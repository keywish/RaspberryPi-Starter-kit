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

string Change_Map();
extern ST_KEY_MAP keymap[];

#endif