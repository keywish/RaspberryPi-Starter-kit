import RPi.GPIO as GPIO
import time

BtnPin_1 = 22
LEDpin_1 = 18
BtnPin_2 = 23
LEDpin_2 = 19
BtnPin_3 = 24
LEDpin_3 = 20
BtnPin_4 = 25
LEDpin_4 = 21

GPIO.setmode(GPIO.BCM)
GPIO.setup(LEDpin_1,GPIO.OUT)
GPIO.setup(BtnPin_1,GPIO.IN)
GPIO.setup(LEDpin_2,GPIO.OUT)
GPIO.setup(BtnPin_2,GPIO.IN)
GPIO.setup(LEDpin_3,GPIO.OUT)
GPIO.setup(BtnPin_3,GPIO.IN)
GPIO.setup(LEDpin_4,GPIO.OUT)
GPIO.setup(BtnPin_4,GPIO.IN)
GPIO.output(LEDpin_1,GPIO.LOW)
GPIO.output(LEDpin_2,GPIO.LOW)
GPIO.output(LEDpin_3,GPIO.LOW)
GPIO.output(LEDpin_4,GPIO.LOW)

while True:
    if GPIO.input(BtnPin_1):
        time.sleep(0.04)
        while GPIO.input(BtnPin_1):
            GPIO.output(LEDpin_1,GPIO.HIGH)
            GPIO.output(LEDpin_2,GPIO.LOW)
            GPIO.output(LEDpin_3,GPIO.LOW)
            GPIO.output(LEDpin_4,GPIO.LOW)
    if GPIO.input(BtnPin_2):
        time.sleep(0.04)
        while GPIO.input(BtnPin_2):
            GPIO.output(LEDpin_2,GPIO.HIGH)
            GPIO.output(LEDpin_1,GPIO.LOW)
            GPIO.output(LEDpin_3,GPIO.LOW)
            GPIO.output(LEDpin_4,GPIO.LOW)
    if GPIO.input(BtnPin_3):
        time.sleep(0.04)
        while GPIO.input(BtnPin_3):
            GPIO.output(LEDpin_3,GPIO.HIGH)
    if GPIO.input(BtnPin_4):
        time.sleep(0.04)
        while GPIO.input(BtnPin_4):
            GPIO.output(LEDpin_4,GPIO.HIGH)
            GPIO.output(LEDpin_1,GPIO.LOW)
            GPIO.output(LEDpin_2,GPIO.LOW)
            GPIO.output(LEDpin_3,GPIO.LOW)
    GPIO.output(LEDpin_1,GPIO.LOW)
    GPIO.output(LEDpin_2,GPIO.LOW)
    GPIO.output(LEDpin_3,GPIO.LOW)
    GPIO.output(LEDpin_4,GPIO.LOW)
        
GPIO.cleanup()

