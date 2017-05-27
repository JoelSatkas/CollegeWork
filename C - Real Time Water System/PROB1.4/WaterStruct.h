struct WaterStruct
{
	/*
		1) filtered: a boolean 'N/Y' that will indicate if the system is filtered.
		2) sedimented: a boolean 'N/Y' that will indicate if the system is sedimented.
		3) flocculated: a boolean 'N/Y' that will indicate if the system is flocculated.
		4) pHLevel: a numeric value indicating the level of the ph in the water.
		5) chlorinationLevel: a numeric value indicating the level of chlorine in the water.
		6) fluoridationLevel: a numeric value indicating the level of fluorine in the water.
		7) hardWater: a numeric value indicating how hard the water is.
	*/
	char filtered; 
	char sedimented;
	char flocculated;
	int pHLevel;
	int chlorinationLevel;
	int fluoridationLevel;
	int hardWater;
};
#define STRUCT_SIZE_WATER sizeof(struct WaterStruct)