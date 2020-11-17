#include <stdio.h>
#include <wiringPi.h>

int main ()
{
	int buzzer = 4;
	int ledpin = 1;
	int sw = 29;
	wiringPiSetup();
	pinMode(buzzer, OUTPUT);
	pinMode(ledpin, OUTPUT);
	pinMode(sw, INPUT);
	while(1)
	{printf("digitalRead(sw): %d\n",digitalRead(sw));
		if(digitalRead(sw))
		{
			digitalWrite(buzzer, HIGH);
			digitalWrite(ledpin, HIGH);
			delay(1000);
			digitalWrite(buzzer, LOW);
			digitalWrite(ledpin, LOW);
			delay(10);
		}

	}
}
