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
	SLEEP_FOR: amount of milliseconds to sleep for before empyting the water.
*/
#define SLEEP_FOR 5000

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
			printf("\nP7: Signal to shut down\n");
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
		fprintf(stderr, "P7: %d: Couldnt Link to data pid module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrWaterName, 
					(mh_com**)&mod_head,
					(void**)&waterStruct, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P7: %d: Couldnt Link to water data module\n", _procid);
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(WATER_STRUCT_NAME,
							&ev_id)) != 0)
	{
		printf("P7: failed to link to water semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	linked = 1;
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

/*
	emptyWater():
	A method that will reset the values in the water structure by using events.
*/
emptyWater()
{
	signal_code signal;
	
	printf("P7: Empting.....\n");
	sleepFor(SLEEP_FOR);
	
	if((errno = _os_ev_wait(ev_id, 
							&value,
							&signal,
							1,
							1)) != 0)
	{
		printf("P7: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterStruct->filtered = 'N';
	waterStruct->sedimented = 'N';
	waterStruct->flocculated = 'N';
	waterStruct->pHLevel = 0;
	waterStruct->chlorinationLevel = 0;
	waterStruct->fluoridationLevel = 0;
	waterStruct->hardWater = 0;
	
	if((errno = _os_ev_signal(ev_id,
							  &value,
							  0)) != 0)
	{
		printf("P7: Signaling event error, shutting down\n");
		_os_exit(errno);
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
		printf("P7: Failed to attach signal handler\n");
		exit(err);
	}

	milSecs = 0;
	while(1)
	{
		_os_sleep(&milSecs, &ReceivedSignal);
		if(!linked)
			link();
		
		if(ReceivedSignal == 355)
		{
			printf("P7: Received signal to empty the water tank. \n");
			emptyWater();
			printf("P7: Water has been emptied. System finished.... \n");
			sleepFor(4000);
			
			printf("P7: Sending message to P1 to restart system. \n");
			sleepFor(4000);
			printf("\n\n");
			if(err = (_os_send(PID->pidArray[0], 356)) != 0)
			{
				printf("P7: Failed to send to P1\n");
			}
		}
	}	
}
