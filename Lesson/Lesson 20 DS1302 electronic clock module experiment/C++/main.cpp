#include "ds1302.h"
#include "LiquidCrystal_I2C.h"
#include<stdio.h>
#include<wiringPi.h>

typedef unsigned int u16;     //对数据类型进行声明定义
typedef unsigned char u8;

char num=0;




/*******************************************************************************
* 函 数 名       : main
* 函数功能       : 主函数
* 输    入       : 无
* 输    出         : 无
*******************************************************************************/
int main()
{   init();
    Ds1302Init();
    while(1)
    {
		char DisplayData[8];
		Ds1302ReadTime();
		DisplayData[0] = TIME[2]/16;               //时
		DisplayData[1] = TIME[2]&0x0f;   
		DisplayData[2] = 10;           
		DisplayData[3] = TIME[1]/16;               //分
		DisplayData[4] = TIME[1]&0x0f;
		DisplayData[5] = 10;  
		DisplayData[6] = TIME[0]/16;               //秒
		DisplayData[7] = TIME[0]&0x0f;
		for (int i = 0; i<8; i++)
		{
			printf("%d",DisplayData[i]);
		}
		printf("\n");
		for (int i = 0; i<8; i++)
		{
		    write(i+8,0,DisplayData[i]+48,0);
		}
	delay(1000);
	clear();
    }    
}
