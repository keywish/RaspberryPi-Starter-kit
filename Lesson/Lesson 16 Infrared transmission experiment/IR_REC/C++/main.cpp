#include "IR_REC.h"

int main()
{
	int key;
	if(wiringPiSetup() == -1){  
        printf("setup wiringPi failed !");  
        return 1;   
    }
	while(1){
	key = GetKey();
		if (key != ERROR) {
			printf("key: %x \n",key);
		}
	}
}