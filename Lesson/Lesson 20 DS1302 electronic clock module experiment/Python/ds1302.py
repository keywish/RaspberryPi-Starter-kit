# !/usr/bin/env python
# -*- encoding:utf-8 -*-
# -----------------------------------------------------

#        This is a program for DS1302 RTC Module.
#    It provide precision timmer.

#        This program depend on rpi_time.py. 

#        ds1302 Module              Pi
#            VCC ------------------ 5 V (Must be 5v)
#            GND ------------------ GND
#            SCL ---------------- Pin 20
#            SDA ---------------- Pin 18
#            RST ---------------- Pin 19

# -----------------------------------------------------
import time
import RPi.GPIO as GPIO
import smbus
import logx
import logging
import LCD1602

DSIO   =     18   
RST    =     19
SCLK   =     20
READ_RTC_ADDR = [0x81, 0x83, 0x85, 0x87, 0x89, 0x8b, 0x8d]
WRITE_RTC_ADDR = [0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c]
TIME = [0, 0, 0x12, 0x07, 0x05, 0x06, 0x16]  
#//---DS1302时钟初始化2016年5月7日星期六12点00分00秒。---//
data = [0,0,0,0,0,0,0,0]
GPIO.setmode(GPIO.BCM)
GPIO.setup(DSIO,GPIO.OUT)
GPIO.setup(RST,GPIO.OUT)
GPIO.setup(SCLK,GPIO.OUT)

def Ds1302Write(addr,dat):
    GPIO.output(RST,False)    
    GPIO.output(SCLK,False)     
    GPIO.output(RST,True)     
    for n in range(8):
        GPIO.output(DSIO,(addr & 0x01))
        addr= addr >> 1
        GPIO.output(SCLK,True)       
        GPIO.output(SCLK,False)
    for n in range(8):
        GPIO.output(DSIO,(dat & 0x01))
        addr= addr >> 1
        GPIO.output(SCLK,True)       
        GPIO.output(SCLK,False)
    GPIO.output(RST,False)
    
def Ds1302Read(addr):
    dat = 0
    GPIO.output(RST,False)   
    GPIO.output(SCLK,False)   
    GPIO.output(RST,True)    
    for n in range(8):
        GPIO.output(DSIO,(addr & 0x01))
        addr= addr >> 1
        GPIO.output(SCLK,True)     
        GPIO.output(SCLK,False)
    GPIO.setup(DSIO,GPIO.IN)
    for n in range(0,8):
        dat = dat | (GPIO.input(DSIO)<<n)
        GPIO.output(SCLK,True)   
        GPIO.output(SCLK,False)  
    GPIO.setup(DSIO,GPIO.OUT)
    GPIO.output(RST,False)
    GPIO.output(SCLK,True)
    GPIO.output(DSIO,False)
    GPIO.output(DSIO,True) 
    return dat

def Ds1302ReadTime():
    for i in range(0,6):
        TIME[i] = Ds1302Read(READ_RTC_ADDR[i])
    print(int(TIME[2]/16),(TIME[2]&0x0f),':',int(TIME[1]/16),(TIME[1]&0x0f),':',int(TIME[0]/16),(TIME[0]&0x0f))
    data[0] = int(TIME[2]/16)
    data[1] = TIME[2]&0x0f
    data[2] = 10
    data[3] = int(TIME[1]/16)
    data[4] = TIME[1]&0x0f
    data[5] = 10
    data[6] = int(TIME[0]/16)
    data[7] = TIME[0]&0x0f
    
def Ds1302Init():
    Ds1302Write(0x8E,0X00)
    for i in range(0,7):
        Ds1302Write(WRITE_RTC_ADDR[i],TIME[i])
    Ds1302Write(0x8E,0x80)

Ds1302Init()
LCD1602.init_lcd()
while True:
    Ds1302ReadTime()
    for i in range(8):
       LCD1602.print_lcd_char(i, 0, data[i] + 48)
        #LCD1602.print_lcd(2, 0,"tt")  
    time.sleep(1)
    
        