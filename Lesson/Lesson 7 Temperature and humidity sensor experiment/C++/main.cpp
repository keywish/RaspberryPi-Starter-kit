#include <stdio.h>
#include <wiringPi.h>
#include <wiringPiI2C.h>
#include <string.h>
#include <stdlib.h>
#include "LiquidCrystal_I2C.h"
#include "dht11.h"

int main()
{
    char val1[3],val2[3],val3[3],val4[3];
    init();
    delay(100);
    wiringPiSetup();
    while(1) 
    {
	pinMode(1,OUTPUT); // set mode to output
	digitalWrite(1, 1); // output a high level 
	delay(100);
	if(readSensorData())
	{
	    printf("RH:%d.%d\n",(int)(databuf>>24)&0xff,(int)(databuf>>16)&0xff); 
	    printf("TMP:%d.%d\n",(int)(databuf>>8)&0xff,(int)databuf&0xff);
	    int2str((int)(databuf>>24)&0xff,val1);
	    int2str((int)(databuf>>16)&0xff,val2);
	    int2str((int)(databuf>>8)&0xff,val3);
	    int2str((int)(databuf)&0xff,val4);
	    write(0, 0,"RH: ");
	    write(5, 0,val1);
	    write(7, 0,".");
	    write(8, 0,val2);
	    write(0, 1,"TMP: ");	
	    write(5, 1,val3);
	    write(7, 1,".");
	    write(8, 1,val4);
	    databuf=0;
	    delay(1000);
	}
    }
	
}

