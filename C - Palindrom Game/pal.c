#include "pal.h"

// Author: Joel Satkauskas
// R00116315
// DCOM3 - B


void initialise_array(int* a, int size, int num)
{

}


int is_pal(int* a, int size_num)
{

	int size = (size_num - 1);
	int isPal = 1; //will be higher then 0 if array is plaindrome

	for (int x = 0; x <= size_num; x++)
	{
		if (a[x] != a[size])
		{
			isPal = 0;
		}
		--size;
	}


	return isPal;
}

void print_status(int* a, int size_num, int* p, int num_mov)
{
	printf("%s", "\n------------ Game Status --------------\n");
	printf("%s", "Number = {");
	for (int x = 0; x <= (size_num - 1); x++)
	{
		printf(" %d", a[x]);
	}
	printf("%s", " }\n           ");

	int goUp;
	for (int x = 0; x <= size_num; x++)
	{
		if (p == &(a[x]))
		{
			goUp = x;
		}
	}

	for (int x = 0; x < goUp; x++)
	{
		printf("%s", "  ");
	}

	printf("%s\n", "^");
	printf("Num mov = %d\n", num_mov);
	printf("----------------------------------\n");
}

void print_Movments(char commands[], int size)
{
	printf("\nNew Movment: Enter a command by keyword: \nValid commands: ");
	for (int x = 0; x <= (size - 1); x++)
	{
		printf(" %c", commands[x]);
	}
	printf("\n");
}

char ask_for_command(char c[], int size)
{
	//char commands[] = { 'a','w','d','x' };
	int correct = 0;
	print_Movments(c, size);
	char userInput = my_get_char();

	while (correct == 0)
	{
		for (int x = 0; x <= (size - 1); x++)
		{
			if (userInput == c[x])
			{
				correct++;
			}
		}

		if (correct == 0)
		{
			print_Movments(c, size);
			userInput = my_get_char();
		}
	}

	return userInput;
}

void process_movement(int* a, int size_num, int** p, int* num_mov, char c)
{

}

void user_game_palindrome(int pal_num, int num_size, char commands[], int command_size)
{

	
	
	int *palArray;
	palArray = (int*)malloc(sizeof(int) * num_size);


	int *a;
	a = (int*)malloc(sizeof(int) * num_size);

	int n = (int) log10(pal_num) + 1;
	int i;

	for (i = 0; i < n; ++i, pal_num /= 10) // getting array from the int
	{
		palArray[i] = pal_num % 10;
		//printf("\n%d\n", palArray[i]);
	}

	int down = (num_size - 1);
	for (int x = 0; x <= (num_size - 1); x++) // making the array in the right order
	{
		a[x] = palArray[down];
		--down;
		//printf("\n%d\n", a[x]);
	}

	free(palArray);
	int* p = &a[0];
	int nm = 0;


	char userInput;


	while (is_pal(a, num_size) == 0)
	{
		print_status(a, num_size, p, nm);
		userInput = ask_for_command(commands, command_size);

		if (userInput == 'a')
		{
			if (p != &a[0])
			{
				p--;
				nm++;
			}
		}
		else if (userInput == 'w')
		{
			if (*p != 9)
			{
				++*p;
				nm++;
			}
		}
		else if (userInput == 'd')
		{
			if (p != &a[(num_size - 1)])
			{
				p++;
				nm++;
			}
		}
		else if (userInput == 'x')
		{
			if (*p != 0)
			{
				--*p;
				nm++;
			}
		}
		else
		{
			printf("Invalid command");
		}

	}

	free(a);

	printf("\nFINISHED\n");

	userInput = getchar();
}

//--------------------------------------------------
// get_solving_array
//--------------------------------------------------

char* get_solving_array(int* a, int size_num, int* p, int* total_movs)
{
	char* null = 0;
	return null;
}


//--------------------------------------------------
// machine_game_palindrome
//--------------------------------------------------

void machine_game_palindrome(int pal_num, int num_size, char commands[], int command_size)
{
	/* Basically i first find in which half the pointer is in.
	Depending on the which half the pointer is in, i set the start and end variables,
	I then find out to which point the pointer is closest and set a boolean to go towards that direction
	It then enters the loop that will continue util the array is a palindrome.

	Depending on the direction that its heading it will first check if its already at
	the start or end of the palindrome, if it is it will change the value each turn untill it is equal
	to its mirror. If it is equal it will set the direction to go the other way.

	else if its not and he start/end point, it will check if its equal to its mirror.
	If not it will change the value every turn.
	if it is, it will move on to the next number until it hit a point or the palindrome is complete.

	*/

	int *palArray;
	palArray = (int*)malloc(sizeof(int) * num_size);


	int *a;
	a = (int*)malloc(sizeof(int) * num_size);

	int n = (int)log10(pal_num) + 1;
	int i;

	for (i = 0; i < n; ++i, pal_num /= 10) // getting array from the int
	{
		palArray[i] = pal_num % 10;
		//printf("\n%d\n", palArray[i]);
	}

	int down = (num_size - 1);
	for (int x = 0; x <= (num_size - 1); x++) // making the array in the right order
	{
		a[x] = palArray[down];
		--down;
		//printf("\n%d\n", a[x]);
	}

	free(palArray);

	int randNum = gen_num(0, (num_size - 1));
	int* p = &a[randNum];
	printf("%d", randNum);
	int nm = 0;

	char userInput = getchar();
	int halfway = num_size / 2;
	short half = 0; // boolean; using short to save space
	int start = 0;
	int end = 0;
	int pPos = 0;

	for (int x = 0; x <= (halfway - 1); x++)
	{
		//goes through first half of palindrome to see if pointer is there
		if (p == &a[x])
		{
			half = 1;
			pPos = x;
		}
	}

	for (int x = (halfway); x <= (num_size - 1); x++)
	{
		// goes through second half of palindrome to see if pointer is there
		if (p == &a[x])
		{
			half = 2;
			pPos = x;
		}
	}

	if (half == 1)// first half
	{
		start = 0;
		end = (halfway - 1);
		printf("\nPointer is in first half\nstart = %d\nend = %d\n",start,end);
	}
	else if (half == 2)// second half
	{
		start = halfway;
		end = (num_size - 1);
		printf("\nPointer is in second half\nstart = %d\nend = %d\n", start, end);
	}
	else
	{
		printf("\nLOGIC ERROR: you shouldnt see this\n");
	}

	int lengthFromStart = 0;
	int lengthFromEnd = 0;

	lengthFromStart = (pPos - start);
	lengthFromEnd = (end - pPos);
	short direction = 0; // boolean; 1 = go forward, 0 = go backwards
	char delay = ' '; // used to get input to delay 

	if (lengthFromStart >= lengthFromEnd)
	{
		direction = 1;
		printf("\nwill be going forwards\ndirection = %d\n", direction);
	}
	else if (lengthFromStart <= lengthFromEnd)
	{
		direction = 0;
		printf("\nwill be going backwards\ndirection = %d\n", direction);
	}
	else
	{
		printf("\nLOGIC ERROR: you shouldnt see this\n");
	}

	while (is_pal(a, num_size) == 0)
	{
		print_status(a, num_size, p, nm);
		delay = getchar();

		if (direction == 0)// what to do if going backwards
		{
			printf("\nwill be going backwards\n");
			if (pPos == start)
			{
				printf("\npointer is at start\n");
				if (a[start] != a[((num_size-1)-pPos)])
				{
					printf("\nstart = %d\nmirror = %d", a[start], a[(end - pPos)]);
					if (a[start] > a[((num_size - 1) - pPos)])
					{
						printf("\nstart is bigger then end, will decrease start\n");
						--a[start];
						++nm;
					}
					else
					{
						printf("\nstart is smaller then end, will increase start\n");
						++a[start];
						++nm;
					}
				}
				else
				{
					++pPos;
					++p;
					++nm;

					direction = 1;// change direcion now
					printf("\nwill change direction now\n");
				}
			}
			else
			{
				printf("\nnot at start\n");
				if (a[pPos] != a[((num_size - 1) -pPos)])
				{
					printf("\npos = %d\nmirror = %d", a[pPos], a[(end - pPos)]);
					if (a[pPos] > a[((num_size - 1) - pPos)])
					{
						
						--a[pPos];
						++nm;
					}
					else
					{
						++a[pPos];
						++nm;
					}
				}
				else
				{
					--pPos;
					--p;
					++nm;
				}
				
			}
		}
		else if (direction == 1)// what to do if going forwards
		{
			printf("\nwill be going forwards\n");
			if (pPos == end)
			{
				if (a[end] != a[((num_size - 1) -pPos)])
				{
					printf("\nend = %d\nmirror = %d", a[start], a[(end - pPos)]);
					if (a[end] > a[((num_size - 1) -pPos)])
					{
						printf("\nend is bigger then start, will decrease start\n");
						--a[end];
						++nm;
					}
					else
					{
						printf("\nend is smaller then start, will increase start\n");
						++a[end];
						++nm;
					}
				}
				else
				{
					--pPos;
					--p;
					++nm;
					direction = 0;// change direcion now
				}
				
			}
			else
			{
				printf("\nnot at end\n");
				printf("\npos = %d\nmirror = %d", a[start], a[(end - pPos)]);
				if (a[pPos] != a[((num_size - 1) - pPos)])
				{
					if (a[pPos] > a[((num_size - 1) - pPos)])
					{
						printf("\nposition is bigger then mirror, will decrease position\n");
						--a[pPos];
						++nm;
					}
					else
					{
						printf("\nposition is smaller then mirror, will increase position\n");
						++a[pPos];
						++nm;
					}
				}
				else
				{
					++pPos;
					++p;
					++nm;
				}
				
			}
		}
		else
		{
			printf("LOGIC ERROR: you shouldnt see this");
		}


	}

	print_status(a, num_size, p, nm);

	printf("FINISHED");

	delay = getchar();
}

