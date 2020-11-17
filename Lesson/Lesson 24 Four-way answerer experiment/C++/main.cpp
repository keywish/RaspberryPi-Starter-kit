#include <wiringPi.h>
#include <stdio.h>

#define BtnPin_1		3
#define LEDpin_1		1
#define BtnPin_2		4
#define LEDpin_2		24
#define BtnPin_3		5
#define LEDpin_3		28
#define BtnPin_4		6
#define LEDpin_4		29

int main(void)
{
	if(wiringPiSetup() == -1){ //when initialize wiring failed,print messageto screen
		printf("setup wiringPi failed !");
		return 1; 
	}

	pinMode(BtnPin_1, INPUT);
	pinMode(LEDpin_1, OUTPUT);
	pinMode(BtnPin_2, INPUT);
	pinMode(LEDpin_2, OUTPUT);
	pinMode(BtnPin_3, INPUT);
	pinMode(LEDpin_3, OUTPUT);
	pinMode(BtnPin_4, INPUT);
	pinMode(LEDpin_4, OUTPUT);
	
	while(1)
	{
		if(1 == digitalRead(BtnPin_1))
		{
			delay(20);
			while(digitalRead(BtnPin_1))
			{
			    digitalWrite(LEDpin_1,HIGH );
			}
			digitalWrite(LEDpin_1,LOW );
		}

		if(1 == digitalRead(BtnPin_2))
		{
			delay(20);
			while(digitalRead(BtnPin_2))
			{
			    digitalWrite(LEDpin_2,HIGH );
			}
			digitalWrite(LEDpin_2,LOW );
		}
		
		if(1 == digitalRead(BtnPin_3))
		{
			delay(20);
			while(digitalRead(BtnPin_3))
			{
			    digitalWrite(LEDpin_3,HIGH );
			}
			digitalWrite(LEDpin_3,LOW );
		}
		
		if(1 == digitalRead(BtnPin_4))
		{
			delay(20);
			while(digitalRead(BtnPin_4))
			{
			    digitalWrite(LEDpin_4,HIGH );
			}
			digitalWrite(LEDpin_4,LOW );
		}
		digitalWrite(LEDpin_1, LOW);
		digitalWrite(LEDpin_2, LOW);
		digitalWrite(LEDpin_3, LOW);
		digitalWrite(LEDpin_4, LOW);
	}
	return 0;
}
