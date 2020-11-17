#ifndef IR_REC_H
#define IR_REC_H

#include <stdio.h>//导入基础库
#include <sys/time.h>
#include <wiringPi.h>//导入树莓派WiringPi编码IO控制库
#include <string>
#include <string.h>
#include<bits/stdc++.h>
#include <cstddef>

using namespace std;

static int buf;
typedef struct
{
    string keyname;
    int keycode;
}ST_KEY_MAP;

static int cyc =20000, pwmPin = 28, MotorPin = 22, value;
static int ERROR = 0xfe, key;
static int PIN = 24;
static struct timeval timeRisingEdge;
static struct timeval timeFallingEdge;
static struct timeval timeRising;
static struct timeval timeFalling_0;
static struct timeval timeFalling_1;
static long timeSpan_val = 0, time_span[2] = {0, 0};

bool IRStart();
int GetByte();
int GetKey();
void pwm_fun(int temp);
int Change_Map(int data);
extern ST_KEY_MAP keymap[];

typedef enum {
    IR_KEYCODE_1 = 0,
    IR_KEYCODE_2,
    IR_KEYCODE_3,
    IR_KEYCODE_4,
    IR_KEYCODE_5,
    IR_KEYCODE_6,
    IR_KEYCODE_7,
    IR_KEYCODE_8,
    IR_KEYCODE_9,
    IR_KEYCODE_0,
    IR_KEYCODE_STAR,      // *
    IR_KEYCODE_POUND,     // # 
    IR_KEYCODE_UP,
    IR_KEYCODE_DOWN,
    IR_KEYCODE_OK,
    IR_KEYCODE_LEFT,
    IR_KEYCODE_RIGHT
};

#endif