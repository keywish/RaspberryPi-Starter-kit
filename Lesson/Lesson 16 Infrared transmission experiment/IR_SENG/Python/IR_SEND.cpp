#include <stdio.h>//导入基础库
#include <sys/time.h>
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <softPwm.h>


class IR_SEND_OBJ
{
	public:
		int irsys = 0xfe, ircode;
		int PIN = 1;
	public:
		void IR_Send_Start();
		void Send_Byte();
		void IR_Send(unsigned char date);
};

void IR_SEND_OBJ::IR_Send_Start() 
{
	pwmWrite(PIN,22);
	delayMicroseconds(4800);
	delayMicroseconds(4800);
	pwmWrite(PIN,0);
	delayMicroseconds(4500);	
}


void IR_SEND_OBJ::Send_Byte()
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

void IR_SEND_OBJ::IR_Send(unsigned char date) {
	if(wiringPiSetup() == -1){  
        printf("setup wiringPi failed !");     
    }
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
extern "C" {  
    IR_SEND_OBJ obj;  
	void IR_Send_Start()
		{obj.IR_Send_Start();}
	void Send_Byte()
		{obj.Send_Byte();}
	void IR_Send(unsigned char date)
		{obj.IR_Send(date);}  
}
