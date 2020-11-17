#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

int  LEDPIN = 21 ; //led灯接2口
int  value = 0 ;
float voltage = 0.0 ;
int cyc = 5000;
void LED_pwm(int temp)
{
	digitalWrite(LEDPIN,HIGH);
	delayMicroseconds(temp); 
	digitalWrite(LEDPIN,LOW);
	delayMicroseconds(cyc-temp); 	
}

int main()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
	pinMode(LEDPIN,OUTPUT);
	while(1)
	{
		value =  wiringPiI2CReadReg16(0x04,0x10);
		printf("%d\n",value);   
		LED_pwm(value);
		delay(1000); //延时1秒
	}
}
