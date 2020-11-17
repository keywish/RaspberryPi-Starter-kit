#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

int  LEDPIN = 2 ; //led灯接2口
int  value = 0 ;
float voltage = 0.0 ;

int main()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
	pinMode(LEDPIN, OUTPUT);
	while(1)
	{
		digitalWrite(LEDPIN, HIGH);
		value =  wiringPiI2CReadReg16(0x04,0x10);
		//printf("%d\n",value); 
		voltage = ( ( float )value )/1023 ;
		value =   voltage * 100 ;             // 利用公式计算出 Value 值 
		if(value > 0)  
		{
			digitalWrite(LEDPIN, HIGH);
			delay(1000);
		} else {
			digitalWrite(LEDPIN, LOW);
			}
		printf("%d\n",value); //打印 Value 值
		delay(400);
	}
}
