#include <stdio.h>
#include <wiringPi.h>

int main ()
{
	int buzzer = 4;
	wiringPiSetup();
	pinMode(buzzer, OUTPUT);
	while(1)
	{
		digitalWrite(buzzer, HIGH);
		delay(500);
		digitalWrite(buzzer, LOW);
		delay(500);
	}
}
