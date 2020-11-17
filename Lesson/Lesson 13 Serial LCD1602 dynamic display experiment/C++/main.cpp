#include <stdio.h>
#include <wiringPi.h>
#include <wiringPiI2C.h>
#include <string.h>
#include <stdlib.h>
#include "LiquidCrystal_I2C.h"

int main()
{
    init();
    delay(100);
    wiringPiSetup();
    while(1) 
    {
	    write(0, 0,"Hello, world!");
	    write(0, 1,"Emakefun");
    }
	
}

