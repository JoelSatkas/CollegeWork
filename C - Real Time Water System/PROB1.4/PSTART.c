#include <stdio.h>
#include <types.h>
#include <errno.h>
#include <process.h>
#include <dexec.h> 
#include "PIDStruct.h"
#include "WaterStruct.h"
#include "Stable.h"
#include "memory.h"
#define PID_ARRAY_NAME "PID"
#define WATER_STRUCT_NAME "WaterStruct"
#define STABLE_STRUCT_NAME "StableStruct"

#define WATER_EVENT "WaterEvent"
#define STABLE_EVENT "StableEvent"

/*
	Author: Joel Satkauskas
	R00116315

	System:
	
	This system is simulating water treatment were water will be entered
	into a tank, certain values will be changed until they are satisfactory and
	then the water would be removed and the process repeated.
	
	P1 is to simulate a process that will enter water into the system.
	P1 will give the water structure random values and then signal P2 and P3 to turn on.
	
	P2 and P3 will simulate sensors. They will have a cyclic alarm that will wake them
	up and if they receive a message from P1 to turn on, will start reading the water 
	structures values and deciding if they are suitable or not.
	If they are not suitable they will send a message to P4 and P5 to change the water structures.
	It the water structure is suitable they will send a message to P4 and P5 indicating that it is
	and turn themselves off.
	
	P4 and P5 will simulate actuators and will change the values of the water structure 
	based on the messages received from P2 and P3.
	If they receive a message indicating that the structure is suitable, then they will 
	change a boolean value in the stable structure.
	
	P6 in a way simulates a sensor as in, it will always be checking the stable structure to see if
	both boolean values are positive. If they are, it will reset them to 
	negative and send a message to P7.
	
	P7 is to simulate a process that will empty the water tank. It will set all 
	values in the water structure to their base and send a message to P1 to restart the system.

	
*/


main(int argc, char *argv[], char **envp)
{
	error_code err;
	process_id child_pid1;
	process_id child_pid2;
	process_id child_pid3;
	process_id child_pid4;
	process_id child_pid5;
	process_id child_pid6;
	process_id child_pid7;

	status_code child_status1;
	status_code child_status2;
	status_code child_status3;
	status_code child_status4;
	status_code child_status5;
	status_code child_status6;
	status_code child_status7;

	mh_com mod_head;
	signal_code DummySignal;
	u_int32 SleepValue;

	signal_code signal;
	event_id water_ev_id;
	event_id stable_ev_id;

	struct PIDStruct *PID;
	struct WaterStruct *waterStruct;
	struct Stable *stable;
	
	u_int16 attr_rev = (MA_REENT << 8);
	u_int16	type_lang = (MT_DATA << 8);
	u_int16 perm = MP_OWNER_READ|MP_OWNER_WRITE;
	u_int16 mem_size_pid = STRUCT_SIZE_PID;
	u_int16 mem_size_water = STRUCT_SIZE_WATER;
	u_int16 mem_size_stable = STRUCT_SIZE_STABLE;

	u_int32 numTicks = 0;

	char *child_argv1[] = {"P1",0};

	char *child_argv2[] = {"P2",0};

	char *child_argv3[] = {"P3",0};

	char *child_argv4[] = {"P4",0};
	
	char *child_argv5[] = {"P5",0};

	char *child_argv6[] = {"P6",0};

	char *child_argv7[] = {"P7",0};
	
	char ChildPIDs[7];

/* Memory Module PID array */	

	if(errno = _os_datmod(PID_ARRAY_NAME,
						mem_size_pid, 
						&attr_rev, 
						&type_lang, 
						perm, 
						(void **)&PID, 
						(mh_data**)&mod_head) != 0)
	{
		printf("\nSTART: Failed to create PID data module, shutting down\n");
		_os_exit(errno);
	}

	printf("START: PID data module created\n");
	
/* Memory Module Water struct */	

	if(errno = _os_datmod(WATER_STRUCT_NAME,
						mem_size_water, 
						&attr_rev, 
						&type_lang, 
						perm, 
						(void **)&waterStruct, 
						(mh_data**)&mod_head) != 0)
	{
		printf("\nSTART: Failed to create WATER data module, shutting down\n");
		_os_exit(errno);
	}

	printf("START: Water data module created\n");

/* Memory Module Stable struct */	

 	if(errno = _os_datmod(STABLE_STRUCT_NAME,
						mem_size_stable, 
						&attr_rev, 
						&type_lang, 
						perm, 
						(void **)&stable, 
						(mh_data**)&mod_head) != 0)
	{
		printf("\nSTART: Failed to create STABLE data module, shutting down\n");
		_os_exit(errno);
	}

	printf("START: Stable data module created\n"); 
	
/* Creating water semaphore */
	
	if((errno = _os_ev_creat(1,
							-1,
							perm,
							&water_ev_id,
							WATER_STRUCT_NAME,
							1,
							MEM_ANY)) != 0)
	{
		printf("START: Failed to create water event, shutting down\n");
		exit(errno);
	}

	printf("START: Created water semaphore\n");
	
/* Creating stable semaphore */
	
	if((errno = _os_ev_creat(1,
							-1,
							perm,
							&stable_ev_id,
							STABLE_STRUCT_NAME,
							1,
							MEM_ANY)) != 0)
	{
		printf("START: Failed to create stable event, shutting down\n");
		exit(errno);
	}

	printf("START: Created stable semaphore\n");

/* Process 1*/

	if(err = (_os_exec(_os_fork, 
						0, 
						3, 
						child_argv1[0], 
						child_argv1, 
						envp, 
						0, 
						&child_pid1, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 1\n");
	}
	else
	{
		printf("START: The child PID for P1 = %d\n", child_pid1); 
	}

/* Process 2*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv2[0], 
						child_argv2, 
						envp, 
						0, 
						&child_pid2, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 2\n");	
	}
	else
	{
		printf("START: The child PID for P2 = %d\n", child_pid2);	
	}

/* Process 3*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv3[0], 
						child_argv3, 
						envp, 
						0, 
						&child_pid3, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 3\n");
	}
	else
	{
		printf("START: The child PID for P3 = %d\n", child_pid3);
	}

/* Process 4*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv4[0], 
						child_argv4, 
						envp, 
						0, 
						&child_pid4, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 4\n");
	}
	else
	{
		printf("START: The child PID for P4 = %d\n", child_pid4);
	}
	
/* Process 5*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv5[0], 
						child_argv5, 
						envp, 
						0, 
						&child_pid5, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 5\n");
	}
	else
	{
		printf("START: The child PID for P5 = %d\n", child_pid5);
	}
	
/* Process 6*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv6[0], 
						child_argv6, 
						envp, 
						0, 
						&child_pid6, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 6\n");
	}
	else
	{
		printf("START: The child PID for P6 = %d\n", child_pid6);
	}
	
/* Process 7*/

	if(err = (_os_exec(_os_fork,
						0, 
						3, 
						child_argv7[0], 
						child_argv7, 
						envp, 
						0, 
						&child_pid7, 
						0, 
						0) !=0))
	{
		printf("START: Error forking child 7\n");
	}
	else
	{
		printf("START: The child PID for P7 = %d\n", child_pid7);
	}
	
	/*
		Store process Id's in struct
	*/

	PID->pidArray[0] = child_pid1;
	PID->pidArray[1] = child_pid2;
	PID->pidArray[2] = child_pid3;
	PID->pidArray[3] = child_pid4;
	PID->pidArray[4] = child_pid5;
	PID->pidArray[5] = child_pid6;
	PID->pidArray[6] = child_pid7;
	
	/*
		Call P1 to begin system and go so infinite sleep 
	*/
	
	printf("START: Sending Start message to P1\n");
	if(err = (_os_send(child_pid1, 356)) != 0)
	{
		printf("START: Failed to send Start to P1\n");
	}
	
	printf("START: Sleeping\n\n\n\n");
	
	while(1)
	{
		_os_sleep(&numTicks, &signal);
	}
	
}