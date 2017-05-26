
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




