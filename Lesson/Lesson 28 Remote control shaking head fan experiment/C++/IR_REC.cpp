#include "IR_REC.h"

bool IRStart() 
{
	while(!(digitalRead(PIN) == 0)); 
	gettimeofday(&timeFalling_0, NULL);
	while(!(digitalRead(PIN) == 1));
	gettimeofday(&timeRising, NULL);
	while(!(digitalRead(PIN) == 0)); 
	gettimeofday(&timeFalling_1, NULL);	
	time_span[0] = (timeRising.tv_sec-timeFalling_0.tv_sec) * 1000000 + timeRising.tv_usec-timeFalling_0.tv_usec;
	time_span[1] = (timeFalling_1.tv_sec-timeRising.tv_sec) * 1000000 + timeFalling_1.tv_usec-timeRising.tv_usec;
	if (time_span[0] > 8500 && time_span[0] < 9500 && time_span[1] > 4000 && time_span[1] < 5000)
		return true;
	else	
		return false;
}

int GetByte()
{
	int byte = 0; 
	pinMode(PIN, INPUT);
	pinMode(pwmPin,OUTPUT);
	pinMode(MotorPin,OUTPUT);
	for (int i = 0; i < 8; i++) {
		while(!(digitalRead(PIN) == 1));
		gettimeofday(&timeRisingEdge, NULL);
		while(!(digitalRead(PIN) == 0));
		gettimeofday(&timeFallingEdge, NULL);
		timeSpan_val = (timeFallingEdge.tv_sec-timeRisingEdge.tv_sec) * 1000000 + timeFallingEdge.tv_usec-timeRisingEdge.tv_usec;
		if (timeSpan_val > 1600 && timeSpan_val < 1800)
			 byte |= 1 << i;
	}
	return byte;
}

int GetKey()
{
	int byte[] = {0, 0, 0, 0};
	if (IRStart() == false) {
		delay(108);
		return ERROR;
	} else {
		for (int i = 0; i < 4; i++) {
			byte[i] = GetByte();
		}
		if ((byte[0] + byte[1] == 0xff) && (byte[2] + byte[3] == 0xff)) {
			//printf("key  %x \n",byte[2]);
			buf = byte[2];
			return byte[2];
		} else {
			return ERROR;
		}	
	}
}

int Change_Map(int data) {
	for (int i = 0; i < 17; i++) {
		if (keymap[i].keycode == data) {
			return i;
		}
	}
	return ERROR;
}

void pwm_fun(int temp)
{
    digitalWrite(pwmPin,HIGH);
    delayMicroseconds(500+temp*500/45); 
    digitalWrite(pwmPin,0);
    delayMicroseconds((cyc-(500+temp*500/45))); 
}

ST_KEY_MAP keymap[17] = {
    {"1", 0x45},
    {"2", 0x46},
    {"3", 0x47},
    {"4", 0x44},
    {"5", 0x40},
    {"6", 0x43},
    {"7", 0x07},
    {"8", 0x15},
    {"9", 0x09},
    {"0", 0x19},
    {"*", 0x16},
    {"#", 0x0D},
    {"up", 0x18},
    {"down", 0x52},
    {"ok", 0x1C},
    {"left", 0x08},
    {"right", 0x5A}
};