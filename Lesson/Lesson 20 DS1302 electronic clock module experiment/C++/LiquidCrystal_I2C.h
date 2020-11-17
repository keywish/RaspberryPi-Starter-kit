#ifndef _LiquidCrystal_I2C_h
#define _LiquidCrystal_I2C_h

#include <stdio.h>
#include <wiringPi.h>
#include <wiringPiI2C.h>
#include <string.h>

extern void write_word(int data);
extern void send_command(int comm);
extern void send_data(int data);
extern void init();
extern void clear();
extern void write(int x, int y, char data[]);
extern void write(int x, int y, char data,int mode);
extern void int2str(int n, char *str);


#endif
