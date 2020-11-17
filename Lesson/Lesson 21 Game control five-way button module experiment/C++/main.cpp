#include <stdio.h>//导入基础库
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <wiringPiI2C.h>//导入树莓派WiringPi编码I2C控制库

#define JOYSTICK_SW    1
#define LED_ENTER    3   //enter
#define LED_LEFT     4   //left
#define LED_UP      5  //up
#define LED_RIGHT   6  //right
#define LED_DOWN    25  //down

int value_x,value_y,value_sw;

void setup()
{
	wiringPiSetup();
	wiringPiI2CSetup(0x04);
    pinMode(JOYSTICK_SW, INPUT);
    pinMode(LED_ENTER,OUTPUT); 
    pinMode(LED_LEFT,OUTPUT);
    pinMode(LED_UP,OUTPUT);
    pinMode(LED_RIGHT,OUTPUT);
    pinMode(LED_DOWN,OUTPUT);
    digitalWrite(LED_ENTER,LOW);
    digitalWrite(LED_LEFT,LOW);
    digitalWrite(LED_RIGHT,LOW);
    digitalWrite(LED_UP,LOW);
    digitalWrite(LED_DOWN,LOW);
    
}

int main()
{
	setup();
	while(1)
	{
		value_sw = digitalRead(JOYSTICK_SW);
		value_x =  wiringPiI2CReadReg8(0x04,0x10);
		delay(200);
		value_y =  wiringPiI2CReadReg8(0x04,0x11);
		//printf("value_x: %d  value_y: %d\n",value_x, value_y); 
		//printf("%d\n",value_sw);
		if(value_x==0)
		{
		    digitalWrite(LED_LEFT,HIGH);
		    delay(500);
		    printf("left");
		    digitalWrite(LED_LEFT,LOW);
		}
		if(value_x==255)
		{
		    digitalWrite(LED_RIGHT,HIGH);
		    delay(500);
		    printf("right");
		    digitalWrite(LED_RIGHT,LOW);
		}
		if(value_y==0)
		{
		    digitalWrite(LED_UP,HIGH);
		    delay(500);
		    printf("up");
		    digitalWrite(LED_UP,LOW);
		}
		if(value_y==255)
		{
		    digitalWrite(LED_DOWN,HIGH);
		    delay(500);
		    printf("down");
		    digitalWrite(LED_DOWN,LOW);
		}
		if(value_sw == 0 )
		{
		    delay(200);
		    if(value_sw == 0 )
		    {
			digitalWrite(LED_ENTER,HIGH);
			delay(500);
			//printf("enter");
			digitalWrite(LED_ENTER,LOW);
		    }
		}
	}
}
