#include <iostream>
#include <wiringPi.h>
#include "SegmentDisplay.h"
using namespace std;

#define  LED_A   29      
#define  LED_B   28       
#define  LED_C   25       
#define  LED_D   24      
#define  LED_E   23      
#define  LED_F   26      
#define  LED_G   6       
#define  LED_H   22    
#define  LED_D1  4
#define  LED_D2  5
#define  LED_D3  27
#define  LED_D4  3

int main ()
{
	wiringPiSetup();
	SegmentDisplay _4Bit_7SegmentDisplay(LED_A, LED_B, LED_C, LED_D, LED_E, LED_F, LED_G, LED_H, LED_D1, LED_D2, LED_D3, LED_D4);

	int ShowTime = 0, count = 0;
	while(1)
	{
	    if (ShowTime > 60 )
	    {
		ShowTime = 0 ;
	    }
	    _4Bit_7SegmentDisplay.DisplayChar((int)ShowTime);
	    ShowTime++ ;
	}
}


