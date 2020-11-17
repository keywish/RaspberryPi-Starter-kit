#include "IR_REC.h"

int main()
{
	int flag;
	if(wiringPiSetup() == -1){  
        printf("setup wiringPi failed !");  
        return 1;   
    }
	while(1){
		key = Change_Map(GetKey());
		if (key != ERROR) {
			printf("Change_Map %s \n",keymap[key].keyname.c_str());
			switch (key) {// Determine which button is pressed and execute the corresponding program
				case IR_KEYCODE_OK:
						printf("IR_KEYCODE_OK key\n");
						flag = !flag;
						digitalWrite(MotorPin, flag);// Control the motor
						break;
				case IR_KEYCODE_LEFT:
						pwm_fun(0);
						delay(500);// Control the steering gear to turn to 0 degree
						printf("IR_KEYCODE_OK left\n");
						break;
				case IR_KEYCODE_RIGHT:
						pwm_fun(180);
						delay(500);//Control the steering gear to turn 180 degrees
						printf("IR_KEYCODE_OK right\n");
						break;
			}
		}
	}
}