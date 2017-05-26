#include "linked_list.h"

int numPlaces(int n) // return number of numbers in int
{
	if (n < 100) return 2;
	if (n < 1000) return 3;
	if (n < 10000) return 4;
	if (n < 100000) return 5;
	if (n < 1000000) return 6;
	if (n < 10000000) return 7;
	if (n < 100000000) return 8;
	if (n < 1000000000) return 9;
	if (n < 10000000000) return 10;
	return 11;
}

//--------------------------------------------------
// load_palNode_list_from_file 
//--------------------------------------------------

struct palNode* load_palNode_list_from_file(char file_name[]) {
	struct palNode* head = NULL;

	//1. Get the string with the content of the file
	char* str = read_entire_file_and_store_it_in_a_string(file_name);

	//2. Parse the content of the file
	int num_lines;
	int* num_words_per_line = NULL;
	char*** content = parse_file_content(str, &num_lines, &num_words_per_line);

	//3. For each palindrome of the file. 
	for (int i = 0; i < num_lines; i++) {
		char** l = content[i];

		//3.1. Create the palindrome from the content of the line
		struct palNode* newnode = (struct palNode*) malloc(1 * sizeof(struct palNode));
		//3.1.1. We add the number and pointer index
		(*newnode).number = atoi(l[0]);
		(*newnode).pointer_index = atoi(l[1]);

		//3.1.2. If the palindrome is already solved we just take it from the file
		if (num_words_per_line[i] == 3) {
			char* p = (char*)malloc((strlen(l[2]) + 1) * sizeof(char));
			strcpy(p, l[2]);

			(*newnode).solving_array = p;
			(*newnode).solved = 1;
		}
		//Otherwise it is null for the moment
		else {
			(*newnode).solving_array = NULL;
			(*newnode).solved = 0;
		}

		//3.1.3. We insert the node in the list
		struct palNode* current_node = head;

		//If the list was empty, we make the head to point to this node.
		if (current_node == NULL)
			head = newnode;
		//Otherwise, we place it at the very end of the list
		else 
		{
			while ((*current_node).next != NULL)
				current_node = (*current_node).next;
			(*current_node).next = newnode;
		}
		(*newnode).next = NULL;
	}

	printf("Operation completed\n");

	return head;

}

//--------------------------------------------------
// print_numbers_of_list 
//--------------------------------------------------

void print_numbers_of_list(struct palNode* head)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function\n");
	}
	else
	{
		struct palNode* current_node = head;
		int x = 0;

		while (current_node != NULL)
		{
			x++;
			printf("\n%d) %d",x,(*current_node).number);
			current_node = (*current_node).next;
		}
		printf("\n");
	}
}

//--------------------------------------------------
// find_palindrome_in_list 
//--------------------------------------------------

struct palNode* find_palindrome_in_list(struct palNode* head, int num)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function\n");
		return NULL;
	}
	else
	{
		struct palNode* current_node = head;
		struct palNode* return_node = NULL;
		int counter = 0;
		int position = 0;
		short found = 0; // boolean

		while (current_node != NULL)
		{
			counter++;
			if (num == (*current_node).number)
			{
				found = 1;
				position = counter;
				return_node = current_node;
			}
			current_node = (*current_node).next;
		}

		if (found == 1)
		{
			printf("The number %d has been found in position %d\n",num,position);
			return return_node;
		}
		else
		{
			printf("The number was not found\n");
			return NULL;
		}
	}
}

//--------------------------------------------------
// show_info_of_a_number 
//--------------------------------------------------

void show_info_of_a_number(struct palNode* head, int num)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function\n");
	}
	else
	{
		struct palNode* current_node = head;
		int counter = 0;
		int position = 0;
		short found = 0; // boolean

		while (current_node != NULL)
		{
			counter++;
			if (num == (*current_node).number)
			{
				found = 1;
				printf("Position: %d\nNumber: %d\nPointer at index: %d\n",counter, (*current_node).number, (*current_node).pointer_index);
				if ((*current_node).solved == 1)
				{

					int x = 0;
					char* charArray = (*current_node).solving_array;
					char* charHead = charArray;

					while(charArray[x])
					{
						x++;
					}
					printf("Solved in %d movements: ",x);

					

					charArray = charHead;
					x = 0;
					while (charArray[x])
					{
						printf("%c",charArray[x]);
						x++;
					}
				}
				else
				{
					printf("\nUnsolved\n");
				}
				printf("\n");
			}
			current_node = (*current_node).next;
		}

		if (found == 0)
		{
			printf("\nPalindrome was not found\n");
		}
	}
}

//--------------------------------------------------
// add_palNode_from_keyboard 
//--------------------------------------------------

struct palNode* add_palNode_from_keyboard(struct palNode* head, int num, int pos)
{
	// will presume input is not user error free.

	if (head == NULL)
	{
		printf("header is empty - Can not perform function");
		return NULL;
	}
	else if(num < 10)
	{
		printf("Number is not a valid palindrome");
		return head;
	}
	else
	{
		int numSize = numPlaces(num); // getting number of numbers from int
		// making sure that the index is valid and changing it if not.
		if (pos == -1) pos = gen_num(0,numSize);
		if (pos > numSize) pos = numSize;
		if (pos < 0) pos = 0;

		struct palNode* newnode = (struct palNode*) malloc(1 * sizeof(struct palNode));
		(*newnode).number = num;
		(*newnode).pointer_index = pos;
		(*newnode).solved = 0;
		(*newnode).solving_array = NULL;

		struct palNode* current_node = head;

		while ((*current_node).next != NULL)
		{
			current_node = (*current_node).next;
		}
		(*current_node).next = newnode;
		(*newnode).next = NULL;

		return head;
	}
}


int* makeIntArray(int pal_num, int num_size)
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
		//printf("\n%d\n", a[x]);
		--down;
		//printf("\n%d\n", a[x]);
	}

	free(palArray);
	int* p = &a[0];
	int nm = 0;

	return a;
}


//--------------------------------------------------
// solve_palindrome_of_node 
//--------------------------------------------------

char* solve_palindrome_of_node(struct palNode* current_node)
{

	/*Did not finish. Unsure on what parameters need to be passed into get_solving_array
	I did not have a getTotalMoves or get_solving_array in my project 1 as I showed the moves being played
	on the go. I looked at your code and you seem to be setting the totalMoves to zero so that
	is confusing me even more. I tryed to use the method non the less and it crashes.
	So im not quite sure on how to continue but if this method could pass back a pointer to an array
	of moves i could easily put it inside the palNode and make this function work.*/


	int pal_num = (*current_node).number;
	int arraySize = numPlaces((*current_node).number);
	int* palArray = makeIntArray(pal_num, arraySize);
	int* pointer = &(*current_node).pointer_index;
	int totalmoves = 0;
	int* tm = &totalmoves;
	//char* temp = (char*)malloc((50) * sizeof(char));

	for (int x = 0; x < (arraySize + 1);x++)
	{
		printf("\n%d\n",palArray[x]);
	}

	printf("will try to solve");

	char* temp = get_solving_array(palArray, arraySize, pointer, tm);

	printf("\n%d\n",totalmoves);
	printf("\n%d\n", *tm);

	//char* solvedArray = (char*)malloc((50) * sizeof(char));
	
	int x = 0;
	while(temp[x])
	{
		printf("\n%c\n",temp[x]);
		x++;
	}
	

	return NULL;
}

//--------------------------------------------------
// solve_a_palindrome 
//--------------------------------------------------

struct palNode* solve_a_palindrome(struct palNode* head, int num)
{
	/*Unfinished. see solve_palindrome_of_node^*/

	if (head == NULL)
	{
		printf("header is empty - Can not perform function");
		return NULL;
	}
	else
	{
		struct palNode* toSolve_node = find_palindrome_in_list(head, num);

		if (toSolve_node == NULL) 
		{
			printf("\n Can not find palindrome - can not solve");
		}
		else
		{
			(*toSolve_node).solving_array = solve_palindrome_of_node(toSolve_node);

		}

		return head;
	}
}

//--------------------------------------------------
// find_previous_node 
//--------------------------------------------------

struct palNode* find_previous_node(struct palNode* head, struct palNode* pointAtMe)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function");
		return NULL;
	}
	else
	{
		struct palNode* current_node = head;
		struct palNode* prev_node = NULL;

		while ((*current_node).next != NULL)
		{
			if ((*current_node).next == pointAtMe)
			{
				prev_node = current_node;
			}
			current_node = (*current_node).next;
		}

		return prev_node;
	}
}

//--------------------------------------------------
// remove_a_palindrome 
//--------------------------------------------------

struct palNode* remove_a_palindrome(struct palNode* head, int num)
{
	if (head == NULL)
	{
		printf("\nheader is empty - Can not perform function\n");
		return NULL;
	}
	else
	{
		struct palNode* current_node = head;
		struct palNode* del_node = find_palindrome_in_list(head, num);

		if (del_node == NULL)// if node exists 
		{
			printf("\nNo such palidrome in list\n");
			return head;
		}
		else
		{
			
			struct palNode* next_node = (*del_node).next;
			struct palNode* prev_node = NULL;

			if (del_node == current_node)// if node is first node
			{
				head = next_node;
				free((*del_node).solving_array);
				free(del_node);
				return head;
			}
			else
			{
				prev_node = find_previous_node(head, del_node);	
				(*prev_node).next = next_node;
				free((*del_node).solving_array);
				free(del_node);
				return head;
			}
		}
	}
}

//--------------------------------------------------
// get_length_of_list 
//--------------------------------------------------

int get_length_of_list(struct palNode* head)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function\n");
		return 0;
	}
	else
	{
		struct palNode* current_node = head;
		int counter = 0;

		while (current_node != NULL)
		{
			counter++;
			current_node = (*current_node).next;
		}

		return counter;
	}
}

//--------------------------------------------------
// sort_the_list 
//--------------------------------------------------

struct palNode* sort_the_list(struct palNode* head, int length)
{
	/*Unfinished. Ran out of time.*/
	if (head == NULL)
	{
		printf("\nheader is empty - Can not perform function\n");
		return NULL;
	}
	else
	{
		int length = get_length_of_list(head);
		//struct palNode* sortedArray[] = (struct palNode*) malloc((length) * sizeof(struct palNode*));

		

		struct palNode* current_node = head;
		int x = 0;

		while (current_node != NULL)
		{
			x++;
			printf("\n%d) %d", x, (*current_node).number);
			current_node = (*current_node).next;
		}
		printf("\n");


		return NULL;
	}
}

//--------------------------------------------------
// write_to_file 
//--------------------------------------------------

void write_to_file(char str[], struct palNode* head)
{
	if (head == NULL)
	{
		printf("header is empty - Can not perform function\n");
	}
	else
	{
		struct palNode* current_node = head;
		FILE *fp;
		fp = fopen(str, "w+");

		while (current_node != NULL)
		{
			fprintf(fp, "%d", (*current_node).number);
			fputs("\t", fp);
			fprintf(fp, "%d", (*current_node).pointer_index);
			fputs("\t", fp);

			if ((*current_node).solving_array != NULL)
			{
				fputs((*current_node).solving_array, fp);
			}
			else
			{

			}

			fputs("\n", fp);
			current_node = (*current_node).next;
		}

		int closed = fclose(fp);

		if (closed == 0) printf("Closed");
		else printf("not closed");

	}
}


