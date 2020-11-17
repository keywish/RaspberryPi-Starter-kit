#ifndef _DHT11_H_
#define _DHT11_H_

#include <stdio.h>
#include <wiringPi.h>
#include <string.h>
#include <stdlib.h>

#define HIGH_TIME 32

typedef unsigned char uint8;
typedef unsigned char uchar;
typedef unsigned int  uint16;
typedef unsigned long uint32;

extern uint32 databuf;


extern uint8 readSensorData(void);
extern uint32 getvalue(void);



#endif
