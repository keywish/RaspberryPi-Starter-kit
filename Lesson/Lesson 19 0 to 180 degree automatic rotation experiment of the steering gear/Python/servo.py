# -*- coding: utf-8 -*-
#!/usr/bin/env python    

import RPi.GPIO as GPIO  
import time  
import signal  
import atexit  

atexit.register(GPIO.cleanup)    

servopin = 20  
GPIO.setmode(GPIO.BCM)  
GPIO.setup(servopin, GPIO.OUT)    
cyc = 0.2

def pwm_change(temp):
    GPIO.output(servopin ,True)
    time.sleep(0.0005+float(temp)*0.0005/45)
    GPIO.output(servopin , False)
    time.sleep(0.02-(0.0005+float(temp)*0.0005/45))
    print(0.0005+float(temp)*0.0005/45)
    
for i in range(0,180,10):
    pwm_change(i)
    time.sleep(0.2)
GPIO.cleanup()