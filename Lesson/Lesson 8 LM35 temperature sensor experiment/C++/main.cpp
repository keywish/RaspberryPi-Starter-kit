#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

int  value = 0 ;
int a;
float voltage = 0.0 ;

int main()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
	while(1)
	{
		value =  wiringPiI2CReadReg16(0x04,0x10);
		voltage = ( ( float )value )/1023/10 ;
		value =   voltage * 5 * 100 ;             // 利用公式计算出 Value 值   
		printf("%d\n",value); //打印 Value 值
		delay(1000); //延时1秒
	}
}
