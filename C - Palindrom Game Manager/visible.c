
#include "visible.h"

//--------------------------------------------------
// gen_num
//--------------------------------------------------

int gen_num(int lb, int ub) {
	int num = (rand() % (ub - lb)) + lb;

	return num;
}
/* Note: Do not forget to include the following instruction at the beginning of your main() method:
srand(time(NULL));
*/

//--------------------------------------------------
// my_getchar
//--------------------------------------------------

char my_get_char() {
	char my_char;
	int b = 0;
	char dummy_char;

	my_char = getchar();

	while (b == 0) {
		dummy_char = getchar();
		if (dummy_char == '\n')
			b = 1;
	}

	return my_char;
}

//--------------------------------------//
//	split_a_string_by_pattern			//	
//--------------------------------------//
char** split_a_string_by_pattern(char str[], char* pattern, int* num_items) {
	//0. The variable we are going to return
	char** items = NULL;

	//1. At the beginning we have not processed any word, thus:
	//current_word = 0 and current_char = 0 (at the beginning of str).
	unsigned int current_char = 0;
	(*num_items) = 0;

	//2. While we have not processed the entire str
	while (current_char < strlen(str)) {
		//2.1. We make a copy of str to work with it. 
		char* my_aux_str = (char*)malloc((strlen(str) + 1) * sizeof(char));
		strcpy(my_aux_str, &str[current_char]);

		//2.2. We look for the next blank space
		char* p = strstr(my_aux_str, pattern);

		//2.3. We check for the next blank space, to measure the length of the next word of str
		int dist;
		if (p != NULL) {
			//If there is such a blank space, we measure the distance to the start of the string to measure the length of the word.
			dist = p - &my_aux_str[0];
		}
		else {
			//If there is not such a blank space, we take the length of the string as the length of the word.
			dist = strlen(my_aux_str);
		}

		//2.4. We asign the word to our array of words
		char* q = (char*)malloc((dist + 1) * sizeof(char));
		strncpy(q, my_aux_str, dist);
		q[dist] = '\0';

		//EXTRA1. We increase the number of words in our array of words
		char** new_items = (char**)realloc(items, ((*num_items) + 1) * sizeof(char*));
		if (new_items != NULL) {
			items = new_items;
			items[(*num_items)] = q; //We make words[(*num_words)] to point to q.
		}

		//2.5. We update the number of words processed and the next_char we are pointing at in str.
		(*num_items) = (*num_items) + 1;
		current_char = current_char + (dist + 1);

	}

	return items;

}

//--------------------------------------//
//	parse_file_content					//	
//--------------------------------------//
char*** parse_file_content(char str[], int* num_lines, int** num_words_per_line) {
	char*** f_content = NULL;

	//1. Split the string into lines
	(*num_lines) = 0;
	char** lines = split_a_string_by_pattern(str, "\n", num_lines);

	//2. Create the variable 'content' to host the lines of the file a
	f_content = (char***)malloc((*num_lines) * sizeof(char**));
	int* f_words_per_line = (int*)malloc((*num_lines) * sizeof(int*));

	//4. For each line of the file, split it into words and add it to content
	for (int i = 0; i < (*num_lines); i++) {
		f_content[i] = split_a_string_by_pattern(lines[i], "\t", &f_words_per_line[i]);
	}

	//5. Make num_words_per_line to point to f_words_per_line
	(*num_words_per_line) = &f_words_per_line[0];

	return f_content;

}

//---------------------------------------------//
//	read_entire_file_and_store_it_in_a_string  //	
//---------------------------------------------//
char* read_entire_file_and_store_it_in_a_string(char name[]) {
	//1. We create a string of size 1
	int length = 0;
	char* s = (char*)malloc((length + 1) * sizeof(char));

	//2. We open the file for reading and set our loop control variable
	FILE* ifp = fopen(name, "r");
	int finish = 0;

	//3. While we haven't parsed the entire file
	while (finish == 0) {

		//3.1. We read a new character from the file
		char c = getc(ifp);

		//3.2. If the character is not EOF we add the character to s and adjust the size of s with realloc
		if (c != EOF) {
			s[length] = c;
			length = length + 1;
			char* new_s = (char*)realloc(s, (length + 1) * sizeof(char));
			if (new_s != NULL)
				s = new_s;
		}
		//3.3. If the character is EOF we conclude the parsing and set the end character to our string
		else {
			finish = 1;
			s[length] = '\0';
		}
	}

	return s;

}

//--------------------------------------------------
// select_option
//--------------------------------------------------

int select_option() {
	char _option[30];
	int option;

	for (int i = 0; i < 100; i++)
		printf("\n");

	printf("------------------------------------\n");
	printf("			    MENU				\n");
	printf("------------------------------------\n");
	printf("1. Load palindromes from file\n");
	printf("2. Print the numbers of the list\n");
	printf("3. Check if palindrome is in the list\n");
	printf("4. Show info of a number\n");
	printf("5. Add a palindrome from keyboard\n");
	printf("6. Solve a palindrome\n");
	printf("7. Remove a palindrome\n");
	printf("8. Sort the palindromes by their number of movements to be solved\n");
	printf("9. Write the list to a file\n");
	printf("0. Exit\n");

	int repeat = 1;

	while (repeat == 1) {
		printf("Select your option: \n");
		scanf("%s", &_option);
		option = atoi(_option);
		if ((option >= 0) && (option <= 9))
			repeat = 0;
	}

	return option;
}