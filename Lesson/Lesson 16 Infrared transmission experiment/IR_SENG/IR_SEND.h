#ifndef IR_SEND_H
#define IR_SEND_H

#include <stdio.h>//导入基础库
#include <sys/time.h>
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <softPwm.h>

static int irsys = 0xfe, ircode;
static int PIN = 1;

void IR_Send_Start();
void Send_Byte();
void IR_Send(unsigned char date);

#endif