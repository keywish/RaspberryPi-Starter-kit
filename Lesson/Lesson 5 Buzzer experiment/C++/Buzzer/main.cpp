#include <stdio.h>
#include <wiringPi.h>

int main ()
{
	int buzzer=4;
	wiringPiSetup();
	pinMode(buzzer, OUTPUT);
	while(1)
	{
		for(int i=0;i<800;i++)  // 1k HZ
		{
			//声音频率设置
			digitalWrite(buzzer,HIGH);  
			delay(0.5);
			digitalWrite(buzzer,LOW);
			delay(0.5);
		}
		delay(1000);
		for(int i=0;i<800;i++)   // 250 HZ
		{
			//声音频率设置
			digitalWrite(buzzer,HIGH);  
			delay(2);
			digitalWrite(buzzer,LOW);
			delay(2);
		}
		delay(1000);
	}
}
