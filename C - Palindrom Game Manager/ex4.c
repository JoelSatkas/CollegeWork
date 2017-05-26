
#include "linked_list.h"

//--------------------------------------//
//	main								//	
//--------------------------------------//
int main(int argc, char* argv[]) {

	int finish = 0;
	struct palNode* head = NULL;

	while (finish == 0) {
		//Print the menu
		int repeat = 1; 
		int option = select_option();

		char name[256];
		char _num[256];
		char _pos[256];
		char c;
		int num;
		int pos;

		switch (option) {
		//1. Load palindromes from file
		case 1:
			printf("Enter the name of the file you want to read from\n");
			scanf("%s", &name);

			for (int i = 0; i < 100; i++)
				printf("\n");

			head = load_palNode_list_from_file(name);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//2. Print the numbers of the list
		case 2:
			for (int i = 0; i < 100; i++)
				printf("\n");

			print_numbers_of_list(head);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//3. Check if palindrome is in the list
		case 3:
			printf("Enter the number for your new palindrome\n");
			scanf("%s", &_num);
			num = atoi(_num);

			for (int i = 0; i < 100; i++)
				printf("\n");

			struct palNode* node = find_palindrome_in_list(head, num);
			if (node == NULL)
				printf("The number is not in the list\n");
			else
				printf("The number is in the list\n");

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//4. Show info of a number
		case 4:
			printf("Enter the number of the palindrome you want to see the info\n");
			scanf("%s", &_num);
			num = atoi(_num);

			for (int i = 0; i < 100; i++)
				printf("\n");

			show_info_of_a_number(head, num);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//5. Add a palindrome from keyboard
		case 5:
			printf("Enter the number for your new palindrome\n");
			scanf("%s", &_num);
			printf("Enter the index you want the pointer at start (-1 for generate it randomly)\n");
			scanf("%s", &_pos);
			num = atoi(_num);
			pos = atoi(_pos);

			for (int i = 0; i < 100; i++)
				printf("\n");

			head = add_palNode_from_keyboard(head, num, pos);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//6. Solve a palindrome
		case 6:
			printf("Enter the number of the palindrome you want to solve\n");
			scanf("%s", &_num);
			num = atoi(_num);

			for (int i = 0; i < 100; i++)
				printf("\n");

			head = solve_a_palindrome(head, num);

			printf("Press key to continue\n");
			c = my_get_char();
			break;
			
		//7. Remove a palindrome
		case 7:
			printf("Enter the number of the palindrome you want to remove\n");
			scanf("%s", &_num);
			num = atoi(_num);

			for (int i = 0; i < 100; i++)
				printf("\n");

			head = remove_a_palindrome(head, num);

			printf("Press key to continue\n");
			c = my_get_char();
			break;
			
		//8. Sort the palindromes by their number of movements to be solved
		case 8:
			for (int i = 0; i < 100; i++)
				printf("\n");

			num = get_length_of_list(head);
			head = sort_the_list(head, num);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//9. Save the palindromes in a file
		case 9:
			printf("Enter the name of the file you want to write to\n");
			scanf("%s", &name);

			for (int i = 0; i < 100; i++)
				printf("\n");

			write_to_file(name, head);

			printf("Press key to continue\n");
			c = my_get_char();
			break;

		//0. Exit the program
		case 0:
			finish = 1;
			break;
		}
	}

	return 0;

}


