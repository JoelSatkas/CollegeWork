
#include "pal.h"

//--------------------------------------------------
// initialise_array
//--------------------------------------------------

void initialise_array(int* a, int size, int num) {
	int i = size - 1;
	while (i >= 0) {
		*(a + i) = num % 10;
		num = num / 10;
		i = i - 1;
	}
}

//--------------------------------------------------
// is_pal
//--------------------------------------------------

int is_pal(int* a, int size_num) {
	int res = 1;
	int i = 0;

	while ((res == 1) && (i < (size_num / 2))) {
		if (a[i] != a[(size_num - 1) - i])
			res = 0;
		else
			i = i + 1;
	}

	return res;
}

//--------------------------------------------------
// print_valid_commands
//--------------------------------------------------

void print_valid_commands(char c[], int size) {
	printf("Valid commands: ");
	for (int j = 0; j < size; j++)
		printf("%c\t", c[j]);
	printf("\n");
}

//--------------------------------------------------
// print_valid_commands
//--------------------------------------------------

int is_char_in_array(char com, char c[], int size) {
	int val = 0;
	int i = 0;

	while ((val == 0) && (i < size)) {
		if (com == c[i])
			val = 1;
		else
			i = i + 1;
	}

	return val;
}

//--------------------------------------------------
// ask_for_command
//--------------------------------------------------

char ask_for_command(char c[], int size) {
	int b = 0;
	char com;

	while (b == 0) {
		printf("NEW MOVEMENT: Enter a valid command by keyword:\n");
		print_valid_commands(c, size);

		com = my_get_char();


		b = is_char_in_array(com, c, size);
	}

	return com;

}

//--------------------------------------------------
// process_movement
//--------------------------------------------------

void process_movement(int* a, int size_num, int** p, int* num_mov, char com) {
	switch (com) {
	case 'a':
		if ((*p) > (a + 0)) {
			(*p) = (*p) - 1;
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'd':
		if ((*p) < (a + (size_num - 1))) {
			(*p) = (*p) + 1;
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'w':
		if ((*(*p)) < 9) {
			(*(*p)) = (*(*p)) + 1;
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'x':
		if ((*(*p)) > 0) {
			(*(*p)) = (*(*p)) - 1;
			(*num_mov) = (*num_mov) + 1;
		}
		break;
	case 'h':
		if ((*p) > (a + 0)) {
			(*p) = (a + 0);
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'k':
		if ((*p) < (a + (size_num - 1))) {
			(*p) = (a + (size_num - 1));
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'u':
		if ((*(*p)) < 9) {
			(*(*p)) = 9;
			(*num_mov) = (*num_mov) + 1;
		}
		break;

	case 'm':
		if ((*(*p)) > 0) {
			(*(*p)) = 0;
			(*num_mov) = (*num_mov) + 1;
		}
		break;
	}

}

//--------------------------------------------------
// print_status
//--------------------------------------------------

void print_status(int* a, int size_num, int* p, int num_mov) {

	for (int i = 0; i < 100; i++)
		printf("\n");

	printf("\n------ Game Status -------\n");

	//1. Print number
	printf(" Number = { ");

	for (int i = 0; i < size_num; i++) {
		printf("%d ", *(a + i));
	}
	printf("}\n");

	//2. Print pointer
	printf("            ");

	int more_blanks = (int)(p - (a + 0));

	for (int i = 0; i < (2 * more_blanks); i++)
		printf(" ");
	printf("^");
	printf("\n");

	//3. Print number_of_movements
	printf(" Num mov = %d\n", num_mov);

	printf("--------------------------\n");
}

//--------------------------------------------------
// user_game_palindrome
//--------------------------------------------------

void user_game_palindrome(int pal_num, int num_size, char commands[], int command_size) {
	//1. For random purposes
	srand((unsigned int)time(NULL));

	//2. Initialise the variables of the program
	int* a = (int*)malloc(num_size * sizeof(int));
	initialise_array(a, num_size, pal_num);
	int* p = a + gen_num(0, 6);
	int num_mov = 0;

	//3. Main loop: While the number is not a palindrome:
	//	 Print the current status
	//	 Ask for a new command
	//	 Process the command
	print_status(a, num_size, p, num_mov);
	while (!is_pal(a, num_size)) {
		char com = ask_for_command(commands, command_size);
		process_movement(a, num_size, &p, &num_mov, com);
		print_status(a, num_size, p, num_mov);
	}

	printf("\n-------------------------\n");
	printf("------- SOLVED! --------\n");
	printf("\n-------------------------\n");

	free(a);

}

//--------------------------------------------------
// get_solving_array
//--------------------------------------------------
char* get_solving_array(int* a, int size_num, int* p, int* total_movs) {
	//0. Create the solving array that will be returned
	char* res = NULL;

	//1. Extra variables
	//1.1. Compute the lower bound and upper bound indexes of each half of the 'a' array
	int fh_lb = 0;
	int fh_ub = (size_num / 2) - 1;
	int odd = size_num % 2;
	int sh_lb = fh_ub + 1 + odd;
	int sh_ub = size_num - 1;
	//1.2. Position of the pointer and half it belongs to
	int p_index = p - &a[0];
	int half = p_index > fh_ub; //Small comment: If odd size, we consider the middle position to belong to the second half.

								//2. Compute the array of positions to fix.
								//2.1. We create the array with as many position as 'a' has
	int* fix = (int*)malloc(size_num * sizeof(int));
	//2.2. We set total_movs to 0  
	(*total_movs) = 0;
	//2.3. We place two index at the beginning and end of the array 'a', so as to compute 'fix' using them
	int i = fh_lb;
	int j = sh_ub;
	//2.4 While we have not covered the entire half...
	while (i <= fh_ub) {
		//2.4.1. We set fix[i] and fix[j]
		fix[i] = a[j] - a[i];
		fix[j] = a[i] - a[j];
		//2.4.2. We update the total number of movements 'w' or 'x' to be added to the solving array.  
		if (fix[i] >= 0)
			(*total_movs) = (*total_movs) + fix[i];
		else
			(*total_movs) = (*total_movs) + fix[j];
		//2.4.3. We update 'i' and 'j'
		i = i + 1;
		j = j - 1;
	}
	//2.4.4. Finally, if the array is of odd size, the central element will always be 0
	if (odd = 1)
		fix[i] = 0;

	//3. We compute how much to move the pointer ('a' and 'd' commands)
	//3.1. We create two variables 'h_l' and 'h_r' representing how far the pointer has to move (--from its current position--) to the left and to the right, respectively.
	int h_l = 0;
	int h_r = 0;

	//3.2. We compute h_l
	//3.2.1. We set 'i' to fh_lb or sh_lb, depending on half
	i = ((size_num / 2) + odd) * half; //--> Alternative: if (half == 0) then i = fh_lb else i = sh_lb
									   //3.2.2. While there are positions to the left of the pointer and we still have not found any that should be fixed...
	while ((i < p_index) && (h_l == 0)) {
		//3.2.2.1. We check the position. If it has to be fixed we set h_l to its distance to p_index. And we can conclude the loop, there will be no other position more far away to the left the pointer has to move to.
		if (fix[i] != 0)
			h_l = (p_index - i);
		//3.2.2.2. We increase the position to be checked
		i = i + 1;
	}

	//3.3. We compute h_r
	//3.2.1. We set 'i' to fh_ub or sh_ub, depending on half
	i = (size_num / (2 - half)) - 1; //--> Alternative: if (half == 0) then i = fh_ub else i = sh_ub
									 //3.2.2. While there are positions to the right of the pointer and we still have not found any that should be fixed...
	while ((i > p_index) && (h_r == 0)) {
		//3.2.2.1. We check the position. If it has to be fixed we set h_r to its distance to p_index. And we can conclude the loop, there will be no other position more far away to the left the pointer has to move to.
		if (fix[i] != 0)
			h_r = (i - p_index);
		//3.2.2.2. We increase the position to be checked
		i = i - 1;
	}

	//3.4. We add the 'a' and 'd' movements to total_movs. The idea is that we kept the minimum of them. The total number of movemets will be: (i) To go there. (ii) To come back from there. (iii) To go to the remaining side.
	if (h_l <= h_r)
		(*total_movs) = (*total_movs) + ((h_l * 2) + h_r);
	else
		(*total_movs) = (*total_movs) + ((h_r * 2) + h_l);

	//4. We initialise the solving array. We make the + 1 for the final '\0' character.
	res = (char*)malloc(((*total_movs) + 1) * sizeof(char));
	int index = 0;

	//4.1. Implement the initial movements
	//4.1.1. If the shortest way is to go first left
	if (h_l <= h_r) {
		//4.1.1.1. We add the proper 'a' characters (updating index) and set the pointer to be there.
		for (int j = 0; j < h_l; j++)
			res[index] = 'a';
		p_index = p_index - h_l;
		index = index + h_l;
	}
	//4.1.2. If the shortest way is to go first right
	else {
		//4.1.2.1. We add the proper 'd' characters (updating index) and set the pointer to be there.
		for (int j = 0; j < h_r; j++)
			res[index] = 'd';
		p_index = p_index + h_r;
		index = index + h_r;
	}

	//4.2. Implement the fix and move
	//We traverse all elements to be fixed, fixing them.
	while (index < (*total_movs)) {
		//4.2.1. If the current position has to be fixed 
		if (fix[p_index] >= 0)
			//If the fixing involves 'w' commnands. Pay attention: We include the 0 case here, in which we do nothing.
			for (int z = 0; z < fix[p_index]; z++) {
				res[index] = 'w';
				index = index + 1;
			}
		else
			//If the fixing involves 'x' commnands. 
			for (int z = 0; z > fix[p_index]; z--) {
				res[index] = 'x';
				index = index + 1;
			}
		//4.2.2. If there are more positions to fix, move the pointer towards that direction
		if (index < (*total_movs)) {
			//4.2.2.1. If we first went left, now we go right. 
			if (h_l <= h_r) {
				res[index] = 'd';
				p_index = p_index + 1;
			}
			//4.2.2.2. If we first went right, now we go left. 
			else {
				res[index] = 'a';
				p_index = p_index - 1;
			}
			index = index + 1;
		}
	}

	//4.3. We add the final '\0' to turn it into a string
	res[index] = '\0';

	//5. We free the memory of fix, as we will not use it anymore
	free(fix);

	//6. We return the solved array
	return res;
}

//--------------------------------------------------
// machine_game_palindrome
//--------------------------------------------------

void machine_game_palindrome(int pal_num, int num_size, char commands[], int commands_size, int p_index) {
	//1. For random purposes
	srand((unsigned int)time(NULL));

	//2. Initialise the variables of the program
	//2.1. We reserve memory for 'a' and initialise it accordingly to the 'pal_num' entered by the user 
	int* a = (int*)malloc(num_size * sizeof(int));
	initialise_array(a, num_size, pal_num);

	//2.2. We initialise 'p'. If the user entered a -1 we generate a random position for it. Otherwise we just respect the position entered by the user.
	int* p;
	if (p_index == -1)
		p = &a[0] + gen_num(0, num_size);
	else
		p = &a[0] + p_index;

	//2.3. We initialise num_mov and total_movs
	int num_movs = 0;
	int total_movs = 0;

	//3. We compute the optimal number of movements solving array
	char* solving_array = get_solving_array(a, num_size, p, &total_movs);

	//4. We show the result of the array
	print_status(a, num_size, p, num_movs);
	printf("\n-----------------------------------------------\n");
	printf("----- Solved optimally in %d movements --------\n", total_movs);
	printf("\t{ %s }\n", solving_array);
	printf("\n-----------------------------------------------\n");

	//5. We show the evolution of the movements (one by one) so as to turn the original number into a palindrome
	char com;
	int index = 0;

	printf("Press a key to see the solved movements one by one:\n");
	com = my_get_char();

	while (index < total_movs) {
		process_movement(a, num_size, &p, &num_movs, solving_array[index]);
		index++;
		print_status(a, num_size, p, num_movs);
		if (index < total_movs) {
			char com = my_get_char();
		}
	}

	printf("\n-------------------------\n");
	printf("------- SOLVED! --------\n");
	printf("\n-------------------------\n");

	//6. We free the array 'a' and the solving array 'solving_array', as we will not need them anymore.
	free(a);
	free(solving_array);

}



