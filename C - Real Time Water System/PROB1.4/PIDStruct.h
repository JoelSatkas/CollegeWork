struct PIDStruct
{
	/* 
		An array of process Id's that will be used by
		all the processes to communicate with each other.
	*/ 
	process_id pidArray[7];
};
#define STRUCT_SIZE_PID sizeof(struct PIDStruct)