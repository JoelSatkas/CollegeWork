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
	SENSOR_FREQUENCY: the amount of milliseconds to sleep until the alarm wakes up the process again.
*/
#define SENSOR_FREQUENCY 1500

/*
	SENSOR_Value: the value for which the process will try to either be above or below
*/
#define SENSOR_Value 70

/*
	SENSOR_DIRECTION: A boolean value to indicate wheather it is desired to have the water value
	above or below the SENSOR_Value.
	1 - above SENSOR_Value
	0 - below SENSOR_Value
*/
#define SENSOR_DIRECTION 1

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

/*
	on: a boolean to indicate wheather the sensor is on or off,
	wheather to read the water structure and perform operations or to ignore the alarm.
*/
int on = 0;
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
			printf("\nP2: Signal to shut down\n");
			_os_exit(0);
			break;
		case 357:
			printf("P2: Received message to turn on\n");
			on = 1;
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
		fprintf(stderr, "P2: %d: Couldnt Link to data pid module\n", _procid);
		_os_exit(errno);
	}
	
	errno = _os_link(&ptrWaterName, 
					(mh_com**)&mod_head,
					(void**)&waterStruct, 
					&type_lang, 
					&attr_rev);
	
	
	if(errno != 0)
	{
		fprintf(stderr, "P2: %d: Couldnt Link to water data module\n", _procid);
		_os_exit(errno);
	}
	
	if((errno = _os_ev_link(WATER_STRUCT_NAME,
							&ev_id)) != 0)
	{
		printf("P2: failed to link to water semaphore, shutting down\n");
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
	readWater():
	A method that will retrieve the value fluoridationLevel from the water structure
	by using events.
*/
int readWater()
{
	signal_code signal;
	int waterValue;
	
	if((errno = _os_ev_wait(ev_id, 
							&value,
							&signal,
							1,
							1)) != 0)
	{
		printf("P2: Error waiting for semaphore, shutting down\n");
		_os_exit(errno);
	}
	
	waterValue = waterStruct->fluoridationLevel;
	
	if((errno = _os_ev_signal(ev_id,
							  &value,
							  0)) != 0)
	{
		printf("P2: Signaling event error, shutting down\n");
		_os_exit(errno);
	}
	
	return waterValue;
}

main()
{
	error_code err;
	u_int32 milSecs;
	signal_code ReceivedSignal;
	
	alarm_id MyAlarm;
	signal_code WakeupSignal;
	u_int32 TimeToDelay;
	int waterValue;

	if((err = _os_intercept(sig_handler,
							_glob_data)) != 0)
	{
		printf("P2: Failed to attach signal handler\n");
		exit(err);
	}
	
	/*
		WakeupSignal: the signal for the cyclic alarm to use when calling.
	*/
	WakeupSignal = 356;
	TimeToDelay = SENSOR_FREQUENCY;
	
	if((errno = _os_alarm_cycle(&MyAlarm,
								WakeupSignal,
								TimeToDelay)) !=0)
	{
		printf("P2: error creating alarm\n");
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
			if(on)
			{
				
				waterValue = readWater();
				printf("P2: Fluoridation Level: %d\n",waterValue);
				
				/*
					if we want it to be above the value
				*/
				if(SENSOR_DIRECTION)
				{				
					if(waterValue < SENSOR_Value)
					{
						/*
							send message to increase fluoridationLevel
						*/
						printf("P2: Sending message to Increase\n\n");
						if(err = (_os_send(PID->pidArray[3], 355)) != 0)
						{
							printf("P2: Failed to send Increase to P4\n");
						}
					}
					else
					{
						/*
							level satisfactory, send to finish and turn off.
						*/
						printf("P2: Fluoridation Level satisfactory\n");
						printf("P2: Sending finish to P4\n\n");
						if(err = (_os_send(PID->pidArray[3], 357)) != 0)
						{
							printf("P2: Failed to send Finish to P4\n");
						}
						else
						{
							on = 0;
						}
					}
				}
				else
				{
					/*
						else we want it to be below the value
					*/
					if(waterValue >= SENSOR_Value)
					{
						/*
							send message to decrease fluoridationLevel
						*/
						printf("P2: Sending message to Decrease\n\n");
						if(err = (_os_send(PID->pidArray[3], 356)) != 0)
						{
							printf("P2: Failed to send Decrease to P4\n");
						}
					}
					else
						{
							/*
								level satisfactory, send to finish and turn off.
							*/
							printf("P2: Fluoridation Level satisfactory\n");
							printf("P2: Sending finish to P4\n\n");
							if(err = (_os_send(PID->pidArray[3], 357)) != 0)
							{
								printf("P2: Failed to send Finish to P4\n");
							}
							else
							{
								on = 0;
							}
						}
				}
			}
		}
	}	
}