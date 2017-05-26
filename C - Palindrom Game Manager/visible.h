
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <time.h>
#include <string.h>

//--------------------------------------------------
// gen_num
//--------------------------------------------------
/*
Generates a random number in the interval [lb, ub)
Note: Do not forget to include the following instruction at the beginning of your main() method:
srand(time(NULL));
*/

int gen_num(int lb, int ub);

//--------------------------------------------------
// my_getchar
//--------------------------------------------------

char my_get_char();

//--------------------------------------//
//	split_a_string_by_pattern			//	
//--------------------------------------//

char** split_a_string_by_pattern(char str[], char* pattern, int* num_items);

//--------------------------------------//
//	parse_file_content					//	
//--------------------------------------//

char*** parse_file_content(char str[], int* num_lines, int** num_words_per_line);

//---------------------------------------------//
//	read_entire_file_and_store_it_in_a_string  //	
//---------------------------------------------//

char* read_entire_file_and_store_it_in_a_string(char name[]);

//--------------------------------------------------
// select_option
//--------------------------------------------------

int select_option();





