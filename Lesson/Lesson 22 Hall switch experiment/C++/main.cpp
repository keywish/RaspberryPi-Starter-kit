#include <stdio.h>
#include <wiringPi.h>
using namespace std;
#define Sensor 3
#define Buzzer 4   
#define led 5


int main ()
{
	int val;
	wiringPiSetup();
	pinMode(Buzzer, OUTPUT);
	pinMode(led, OUTPUT);
	pinMode(Sensor, INPUT);
	while(1)
	{
		val=digitalRead(Sensor);//将数字接口的值读取赋给val 
		if(val==0) 
		{  
			digitalWrite(Buzzer, HIGH);
			digitalWrite(led, HIGH);
			delay(1000);
		} 
		else 
		{   
			digitalWrite(Buzzer, LOW);
			digitalWrite(led, LOW);
		} 
	}
}
