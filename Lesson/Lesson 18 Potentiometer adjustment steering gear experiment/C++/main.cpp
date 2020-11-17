#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

int  LEDPIN = 21 ; //led灯接2口
int  value = 0 ;
float val= 0.0 ;
int cyc =20000;
int pwmPin = 28;

void pwm_fun(int temp)
{
    digitalWrite(pwmPin,HIGH);
    delayMicroseconds(500+temp*500/45); 
    digitalWrite(pwmPin,0);
    delayMicroseconds((cyc-(500+temp*500/45))); 
}

int main()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
	pinMode(pwmPin,OUTPUT);
	while(1)
	{
		value =  wiringPiI2CReadReg16(0x04,0x10);  
		val = float(value * 180) / 4095;
		printf("%d\n",int(val));   
		pwm_fun(int(val));
		delay(500); //延时1秒
	}
}
