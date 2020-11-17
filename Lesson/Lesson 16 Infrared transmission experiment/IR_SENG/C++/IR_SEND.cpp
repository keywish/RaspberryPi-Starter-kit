#include "IR_SEND.h"

void IR_Send_Start() 
{
	pwmWrite(PIN,22);
	delayMicroseconds(4500);
	delayMicroseconds(4500);
	pwmWrite(PIN,0);
	delayMicroseconds(4500);	
}

void Send_Byte()
{
	for (int i = 0; i < 8; i++) {
		if(ircode&0x01) {
			pwmWrite(PIN,22);
			delayMicroseconds(560);
			pwmWrite(PIN,0);
			delayMicroseconds(1690);				
		} else {
			pwmWrite(PIN,22);
			delayMicroseconds(560);
			pwmWrite(PIN,0);
			delayMicroseconds(560);
		}
		ircode = ircode >> 1;
	}
}

void IR_Send(unsigned char date) {
    pinMode(PIN,PWM_OUTPUT);
	pwmSetMode(PWM_MODE_MS);
    pwmSetRange(45);              
    pwmSetClock(32); 
	ircode = irsys;	
	IR_Send_Start();
	Send_Byte();
	ircode = ~irsys;
	Send_Byte();
	ircode = date;
	Send_Byte();
	ircode = ~date;
	Send_Byte();
	pwmWrite(PIN,22);
	delayMicroseconds(400);
	pwmWrite(PIN,0);
}