#include <stdio.h>
#include <signal.h>
#include <types.h>
#include <modes.h>
#include <errno.h>
#include <cglob.h> 
#include <stdlib.h>
#include "PIDStruct.h"
#include "Stable.h"
#include "WaterStruct.h"

#define PID_ARRAY_NAME "PID"
#define STABLE_STRUCT_NAME "StableStruct"
#define WATER_STRUCT_NAME "WaterStruct"

/*
	SENSOR_FREQUENCY: the amount of milliseconds to sleep until the alarm wakes up the process again.
*/
#define SENSOR_FREQUENCY 3000

/*
	SLEEP_FOR: the amount of milliseconds to sleep for before sending message to P7.
*/
#define SLEEP_FOR 2000

u_int32 SleepValue;
mh_com mod_head;
char *ptrPIDName;
char *ptrStableName;
char *ptrWaterName;
struct PIDStruct *PID;
struct Stable *stable;
struct WaterStruct *waterStruct;
signal_code DummySignal;
signal_code dummy_sig;

event_id stable_ev_id;
event_id water_ev_id;
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
			printf("\nP6: Signal to shut down\n");
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
	ptrStableName = STABLE_STRUCT_NAME;
	ptrWaterName = WATER_STRUCT_NAME;

	errno = _os_link(&ptrPIDName, 
					(mh_com**)&mod_head,
					(void**)&PID, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P6: %d: Couldnt Link to data pid module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrWaterName, 
					(mh_com**)&mod_head,
					(void**)&waterStruct, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P6: %d: Couldnt Link to water data module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrStableName, 
					(mh_com**)&mod_head,
					(void**)&stable, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P6: %d: Couldnt Link to Stable data module\n", _procid);
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(STABLE_STRUCT_NAME,
							&stable_ev_id)) != 0)
	{
		printf("P5: failed to link to water semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(WATER_STRUCT_NAME,
							&water_ev_id)) != 0)
	{
		printf("P5: failed to link to water semaphore, shutting down\n");
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
	getCholr():
	A method that will get the chlorinated value from the stable structure using events.
*/
char getCholr()
{
	signal_code signal;
	char ans;
	
	if((errno = _os_ev_wait(stable_ev_id, 
							&stableValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P6: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	ans = stable->chlorinated;
	
	if((errno = _os_ev_signal(stable_ev_id,
							  &stableValue,
							  0)) != 0)
	{
		printf("P6: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
	
	return ans;
}

/*
	getFlor():
	A method that will get the florinated value from the stable structure using events.
*/
char getFlor()
{
	signal_code signal;
	char ans;
	
	if((errno = _os_ev_wait(stable_ev_id, 
							&stableValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P6: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	ans = stable->florinated;
	
	if((errno = _os_ev_signal(stable_ev_id,
							  &stableValue,
							  0)) != 0)
	{
		printf("P6: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
	
	return ans;
}

/*
	ready():
	A method that will get both values from the stable structure and see if they
	are set to true. Return a boolean based on that result.
*/
int ready()
{
	char C = getCholr();
	char F = getFlor();
	
	if((C == 'Y') && (F == 'Y'))
	{
		return 1;
	}
	
	return 0;
}

/*
	empty():
	A method that will reset the stable structure using events, change the water
	structure using events and send a message to P7.
*/
empty()
{	
	error_code err;
	signal_code signal;
	
	if((errno = _os_ev_wait(stable_ev_id, 
							&stableValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P6: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	stable->chlorinated = 'N';
	stable->florinated = 'N';
	
	if((errno = _os_ev_signal(stable_ev_id,
							  &stableValue,
							  0)) != 0)
	{
		printf("P6: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
	
	if((errno = _os_ev_wait(water_ev_id, 
							&waterValue,
							&signal,
							1,
							1)) != 0)
	{
		printf("P5: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterStruct->filtered = 'Y';
	waterStruct->sedimented = 'Y';
	waterStruct->flocculated = 'Y';
	
	if((errno = _os_ev_signal(water_ev_id,
							  &waterValue,
							  0)) != 0)
	{
		printf("P5: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
	
	if(err = (_os_send(PID->pidArray[6], 355)) != 0)
	{
		printf("P6: Failed to send to P7\n");
	}
}

main()
{
	error_code err;
	u_int32 milSecs;
	signal_code ReceivedSignal;
	
	alarm_id MyAlarm;
	signal_code WakeupSignal;
	u_int32 TimeToDelay;

	if((err = _os_intercept(sig_handler, 
							_glob_data)) != 0)
	{
		printf("P6: Failed to attach signal handler\n");
		exit(err);
	}
	
	WakeupSignal = 356;
	TimeToDelay = SENSOR_FREQUENCY;
	
	if((errno = _os_alarm_cycle(&MyAlarm, 
								WakeupSignal,
								TimeToDelay)) !=0)
	{
		printf("P6: error creating alarm\n");
		exit(errno);
	}

	milSecs = 0;
	while(1)
	{
		_os_sleep(&milSecs, &ReceivedSignal);
		if(!linked)
			link();
		
		if(ReceivedSignal == 356)
		{
			if(ready())
			{
				printf("P6: Detected that system has treated water.\n");
				sleepFor(SLEEP_FOR);
				printf("P6: Sending finish to P7.\n");
				empty();
			}
		}
	}	
}
