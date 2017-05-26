
#include "pal.h"
#include "math.h"

//--------------------------------------------------
// struct palNode definition 
//--------------------------------------------------

struct palNode {
	int number;
	int pointer_index;
	char* solving_array;
	int solved;
	struct palNode* next;
};

//--------------------------------------------------
// load_palNode_list_from_file 
//--------------------------------------------------

struct palNode* load_palNode_list_from_file(char file_name[]);

//--------------------------------------------------
// print_numbers_of_list 
//--------------------------------------------------

void print_numbers_of_list(struct palNode* head);

//--------------------------------------------------
// find_palindrome_in_list 
//--------------------------------------------------

struct palNode* find_palindrome_in_list(struct palNode* head, int num);

//--------------------------------------------------
// show_info_of_a_number 
//--------------------------------------------------

void show_info_of_a_number(struct palNode* head, int num);

//--------------------------------------------------
// add_palNode_from_keyboard 
//--------------------------------------------------

struct palNode* add_palNode_from_keyboard(struct palNode* head, int num, int pos);

//--------------------------------------------------
// solve_palindrome_of_node 
//--------------------------------------------------

char* solve_palindrome_of_node(struct palNode* current_node);

//--------------------------------------------------
// solve_a_palindrome 
//--------------------------------------------------

struct palNode* solve_a_palindrome(struct palNode* head, int num);

//--------------------------------------------------
// find_previous_node 
//--------------------------------------------------

struct palNode* find_previous_node(struct palNode* head, struct palNode* pointAtMe);

//--------------------------------------------------
// remove_a_palindrome 
//--------------------------------------------------

struct palNode* remove_a_palindrome(struct palNode* head, int num);

//--------------------------------------------------
// get_length_of_list 
//--------------------------------------------------

int get_length_of_list(struct palNode* head);

//--------------------------------------------------
// sort_the_list 
//--------------------------------------------------

struct palNode* sort_the_list(struct palNode* head, int length);

//--------------------------------------------------
// write_to_file 
//--------------------------------------------------

void write_to_file(char str[], struct palNode* head);



