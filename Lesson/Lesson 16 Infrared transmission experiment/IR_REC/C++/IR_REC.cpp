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
            //printf("byte[%d] %x \n",i,byte[i]);
		}
		if ((byte[0] + byte[1] == 0xff) && (byte[2] + byte[3] == 0xff)) {
			//printf("key  %x \n",byte[2]);
			//buf = byte[2];
			return byte[2];
		} else {
			return ERROR;
		}	
	}
}
