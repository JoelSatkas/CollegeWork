
#include "pal.h"

int main() {
	srand(time(NULL));
	int pal_num = 123456;
	int num_size = 6;
	char commands[] = { 'a','d','w','x' };
	int commands_size = 4;

	machine_game_palindrome(pal_num, num_size, commands, commands_size);

	return 0;
}

