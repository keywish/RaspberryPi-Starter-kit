#include <wiringPi.h>
#include <stdio.h>

#define BtnPin		0
#define LEDpin		1

int main(void)
{
	if(wiringPiSetup() == -1){ //when initialize wiring failed,print messageto screen
		printf("setup wiringPi failed !");
		return 1; 
	}

	pinMode(BtnPin, INPUT);
	pinMode(LEDpin, OUTPUT);
	
	while(1)
	{
		if(1 == digitalRead(BtnPin))
		{
			delay(20);
			if(1 == digitalRead(BtnPin))
			{
			    digitalWrite(LEDpin, HIGH);
			}
		}
		else {
		    digitalWrite(LEDpin, LOW);
		}
	}
	return 0;
}
