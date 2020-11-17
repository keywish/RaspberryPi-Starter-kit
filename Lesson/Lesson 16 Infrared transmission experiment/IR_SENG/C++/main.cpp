#include "IR_SEND.h"

int main()
{
	if(wiringPiSetup() == -1){  
        printf("setup wiringPi failed !");  
        return 1;   
    }
	while(1){
		IR_Send(0x45);
		delay(200);
	}
}