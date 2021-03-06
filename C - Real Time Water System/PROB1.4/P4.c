#include <stdio.h>
#include <signal.h>
#include <types.h>
#include <modes.h>
#include <errno.h>
#include <cglob.h> 
#include <stdlib.h>
#include "PIDStruct.h"
#include "WaterStruct.h"
#include "Stable.h"

#define PID_ARRAY_NAME "PID"
#define WATER_STRUCT_NAME "WaterStruct"
#define STABLE_STRUCT_NAME "StableStruct"

/*
	CHANGE_BY: the value of which to change the main value of the water structure.
*/
#define CHANGE_BY 5

/*
	CHANGE_BYPRODUCT: the value of which to change the by product value of the water structure,
	simulates the change of anoither aspect when changing one value.
*/
#define CHANGE_BYPRODUCT 2

u_int32 SleepwaterValue;
mh_com mod_head;
char *ptrPIDName;
char *ptrWaterName;
char *ptrStableName;
struct PIDStruct *PID;
struct WaterStruct *waterStruct;
struct Stable *stable;
signal_code DummySignal;
signal_code dummy_sig;

event_id water_ev_id;
event_id stable_ev_id;
u_int32 waterValue;
u_int32 stableValue;

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
			printf("\nP4: Signal to shut down\n");
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
	ptrStableName = STABLE_STRUCT_NAME;

	errno = _os_link(&ptrPIDName, 
					(mh_com**)&mod_head,
					(void**)&PID, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P4: %d: Couldnt Link to data pid module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrWaterName, 
					(mh_com**)&mod_head,
					(void**)&waterStruct, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P4: %d: Couldnt Link to water data module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrStableName, 
					(mh_com**)&mod_head,
					(void**)&stable, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P4: %d: Couldnt Link to Stable data module\n", _procid);
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(WATER_STRUCT_NAME,
							&water_ev_id)) != 0)
	{
		printf("P4: failed to link to water semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(STABLE_STRUCT_NAME,
							&stable_ev_id)) != 0)
	{
		printf("P4: failed to link to water semaphore, shutting down\n");
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
	IncreaseWater():
	A method that will increase values in the water structure by using events.
*/
IncreaseWater()
{
	signal_code signal;
	
	if((errno = _os_ev_wait(water_ev_id, 
							&waterValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P4: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterStruct->fluoridationLevel += CHANGE_BY;
	waterStruct->hardWater += CHANGE_BYPRODUCT;
	
	if((errno = _os_ev_signal(water_ev_id,
							  &waterValue,
							  0)) != 0)
	{
		printf("P4: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
}

/*
	DecreaseWater():
	A method that will decrease values in the water structure by using events.
*/
DecreaseWater()
{
	signal_code signal;
	
	if((errno = _os_ev_wait(water_ev_id, 
							&waterValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P4: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterStruct->fluoridationLevel -= CHANGE_BY;
	waterStruct->hardWater -= CHANGE_BYPRODUCT;
	
	if((errno = _os_ev_signal(water_ev_id,
							  &waterValue,
							  0)) != 0)
	{
		printf("P4: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
}

/*
	waterFinished():
	A method that will change a boolean on the stable structure using events
*/
waterFinished()
{
	signal_code signal;
	
	if((errno = _os_ev_wait(stable_ev_id, 
							&stableValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P4: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	stable->florinated = 'Y';
	
	if((errno = _os_ev_signal(stable_ev_id,
							  &stableValue,
							  0)) != 0)
	{
		printf("P4: Signaling event error, shutting down\n");
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
		printf("P4: Failed to attach signal handler\n");
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
			/*
				Increase fluoridationLevel
			*/
			printf("P4: Increasing fluoridationLevel by: %d \n",CHANGE_BY);
			IncreaseWater();
		}
		else if(ReceivedSignal == 356)
		{
			/*
				decrease fluoridationLevel
			*/
			printf("P4: Decreasing fluoridationLevel by: %d \n",CHANGE_BY);
			DecreaseWater();
		}
		else if(ReceivedSignal == 357)
		{
			/*
				Finish
			*/
			printf("P4: fluoridationLevel Stabilized, Sending to Struct \n\n\n");
			waterFinished();
		}
	}	
}
