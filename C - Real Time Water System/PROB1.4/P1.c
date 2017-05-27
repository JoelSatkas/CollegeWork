#include <stdio.h>
#include <signal.h>
#include <types.h>
#include <modes.h>
#include <errno.h>
#include <cglob.h> 
#include <stdlib.h>
#include "PIDStruct.h"
#include "WaterStruct.h"


#define PID_ARRAY_NAME "PID"
#define WATER_STRUCT_NAME "WaterStruct"

/*
	MIN_N is the minimum number for a random number to be generated
*/
#define MIN_N 1

/*
	MAX_N is the maximum number for a random number to be generated
*/
#define MAX_N 100

/*
	WATER_WAIT_TIME is the amount of milliseconds to wait for the water to enter the system.
*/
#define WATER_WAIT_TIME 5000

u_int32 SleepValue;
mh_com mod_head;
char *ptrPIDName;
char *ptrWaterName;
struct PIDStruct *PID;
struct WaterStruct *waterStruct;
signal_code DummySignal;
signal_code dummy_sig;

event_id ev_id;
u_int32 value;

int linked = 0;
u_int32 milSecs = 3000;
u_int16 attr_rev = (MA_REENT << 8);
u_int16	type_lang = (MT_DATA << 8);
u_int16 mem_size_pid = STRUCT_SIZE_PID;
u_int16 mem_size_water = STRUCT_SIZE_WATER;

sig_handler(signal_code sig)
{
	switch(sig)
	{
		case 3: 
			printf("\nP1: Signal to shut down\n");
			_os_exit(0);
			break;
	}
	_os_rte();
}

/*
	link():
	A method to link to all the recources needed by the process.
	Once linked, it will set 'linked' to 1 to indicate that everything is already linked
	so it wont try to link again.
*/
link()
{
	ptrPIDName = PID_ARRAY_NAME;
	ptrWaterName = WATER_STRUCT_NAME;

	errno = _os_link(&ptrPIDName, 
					(mh_com**)&mod_head,
					(void**)&PID, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P1: %d: Couldnt Link to data pid module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrWaterName, 
					(mh_com**)&mod_head,
					(void**)&waterStruct, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P1: %d: Couldnt Link to water data module\n", _procid);
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(WATER_STRUCT_NAME,
							&ev_id)) != 0)
	{
		printf("P1: failed to link to water semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	linked = 1;
	
}

/*
	nextInput():
	A method that will access the water structure by using events and assign the 
	water structure random values to simulate a new entry of water.
*/
nextInput()
{	
	signal_code signal;
	int newPhLevel = getRandNum();
	int newChLevel = getRandNum();
	int newFlLevel = getRandNum();
	int newHW = getRandNum();
	
	printf("P1: Pumping next 1000L of water into system\n");
	
	sleepFor(WATER_WAIT_TIME); 
	
	printf("----------------------------\n");
	printf("PHLevel is %d \n",newPhLevel);
	printf("Chlorination Level is %d \n",newChLevel);
	printf("Fluorination Level is %d \n",newFlLevel);
	printf("HardWater level is %d \n",newHW);
	printf("----------------------------\n");
	
	if((errno = _os_ev_wait(ev_id, 
							&value,
							&signal,
							1,
							1)) != 0)
	{
		printf("P1: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterStruct->pHLevel = newPhLevel;
	waterStruct->chlorinationLevel = newChLevel;
	waterStruct->fluoridationLevel = newFlLevel;
	waterStruct->hardWater = newHW;
	waterStruct->filtered = 'N';
	waterStruct->sedimented = 'N';
	waterStruct->flocculated = 'N';
	
	if((errno = _os_ev_signal(ev_id,
							  &value,
							  0)) != 0)
	{
		printf("P1: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
}

/*
	getRandNum():
	A method that will get a random number between the global variables of MIN_N and MAX_N
	Note: this method does not return truly random values but for this simulation will suffice 
*/
int getRandNum()
{
	return (rand() % (MAX_N - MIN_N + 1) + MIN_N);
}

/*
	sleepFor(int milSecs):
	A method that will take in an int value to sleep for. No signal will interupt its sleep.
	milSecs: amount of milliseconds to sleep for.
*/
sleepFor(int milSecs)
{
	signal_code dummySig;
	int sleepFor = milSecs;
	
	while(sleepFor)
	{
		_os_sleep(&sleepFor, &dummySig);
	}
}

main()
{
	error_code err;
	u_int32 milSecs;
	signal_code ReceivedSignal;

	if((err = _os_intercept(sig_handler, 
							_glob_data)) != 0)
	{
		printf("P1: Failed to attach signal handler\n");
		exit(err);
	}
		

	milSecs = 0;
	while(1)
	{
		_os_sleep(&milSecs, &ReceivedSignal);
		if(!linked)
			link();
		
		if(ReceivedSignal == 356)
		{
			/*
			call next input to make new struct of random values.
			call next processes to start using it
			*/
			nextInput();
			
			printf("P1: Water ready. Calling sensors.\n\n\n");
			if(err = (_os_send(PID->pidArray[1], 357)) != 0)
			{
				printf("P1: Failed to send Start to P2\n");
			}
			
 			if(err = (_os_send(PID->pidArray[2], 357)) != 0)
			{
				printf("P1: Failed to send Start to P3\n");
			} 
		}
	}	
}