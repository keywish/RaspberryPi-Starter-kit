#!/usr/bin/python
# -*- coding:utf-8 -*-
import RPi.GPIO as GPIO
import threading
import time

IR_PIN = 18
GPIO.setmode(GPIO.BCM)
GPIO.setup(IR_PIN, GPIO.OUT)

def IR_Send_Start(): 
    p.start(50)
    time.sleep(0.009)
    p.start(0)
    time.sleep(0.0045)

def Send_Byte():
    ircode = 0
    for i in range(0, 8):
	if ircode and 0x01:
            p.start(50)
            time.sleep(0.00056)
	    p.start(0)
	    time.sleep(0.000169)				
	else:
	    p.start(50)
            time.sleep(0.00056)
	    p.start(0)
	    time.sleep(0.00056)
	    ircode = ircode >> 1
   
def IR_Send(date): 
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(IR_PIN, GPIO.OUT)
    p = GPIO.PWM(IR_PIN, 38000) 
    ircode = irsys
    IR_Send_Start()
    Send_Byte()
    ircode = ~irsys
    Send_Byte()
    ircode = date
    Send_Byte()
    ircode = ~date;
    Send_Byte()
    p.start(50)
    time.sleep(0.0004)
    p.start(0)

def thread1():
    while True:
        GPIO.output(IR_PIN, GPIO.HIGH)
        time.sleep(0.000013)
        GPIO.output(IR_PIN, GPIO.HIGH)
        time.sleep(0.000013)
def pwm(delay_time):
   # threading.start_new_thread(thread1, ())
    #threading.time(delay_time)
    thread = threading.Thread(target=thread1)

while True:
   # pwm(1)
    GPIO.output(IR_PIN, GPIO.HIGH)
    time.sleep(0.000001)
    GPIO.output(IR_PIN, GPIO.LOW)
    time.sleep(0.000001)
'''
try:
    while True:
        IR_Send_Start()

except KeyboardInterrupt:
    pass
p.stop()
GPIO.cleanup()
'''
