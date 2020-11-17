#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

int  LEDPIN = 24; //led灯接IO0口
int  value = 0 ;
float voltage = 0.0 ;
int main()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
	pinMode(LEDPIN, OUTPUT);
	while(1)
	{	
		value =  wiringPiI2CReadReg8(0x04,0x10);
		if(value >= 100)  
		{ 
			digitalWrite(LEDPIN, HIGH);
			delay(500);
		}else{
			digitalWrite(LEDPIN, LOW);
		}
		printf("%d\n",value); //打印 Value 值
		delay(1000); //延时1秒
	}
}
