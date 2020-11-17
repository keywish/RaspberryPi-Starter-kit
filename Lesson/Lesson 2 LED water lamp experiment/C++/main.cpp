#include <stdio.h>
#include <wiringPi.h>
using namespace std;

int main ()
{
	wiringPiSetup();
	pinMode(3, OUTPUT);
	pinMode(4, OUTPUT);
	pinMode(5, OUTPUT);
	pinMode(6, OUTPUT);
	pinMode(25, OUTPUT);
	pinMode(2, OUTPUT);
	while(1)
	{
		digitalWrite(3, HIGH);
		delay(500);
		digitalWrite(3, LOW);
		digitalWrite(4, HIGH);
		delay(500);
		digitalWrite(4, LOW);
		digitalWrite(5, HIGH);
		delay(500);
		digitalWrite(5, LOW);
		digitalWrite(6, HIGH);
		delay(500);
		digitalWrite(6, LOW);
		digitalWrite(25, HIGH);
		delay(500);
		digitalWrite(25, LOW);	
		digitalWrite(2, HIGH);
		delay(500);
		digitalWrite(2, LOW);	
	}
	
}
