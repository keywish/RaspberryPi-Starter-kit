import RPi.GPIO as GPIO
import time

BIT_CHOICE_1 = 22
BIT_CHOICE_2 = 2
BIT_CHOICE_3 = 24
BIT_CHOICE_4 = 23
STCP_PIN = 20
SHCP_PIN = 21
DATA_PIN = 25 #定义stcp shcp ds引脚

BIT_CHOICE = [22 , 2 ,24,23]
DisplayNumble=[0xC0,0xF9,0xA4,0xB0,0x99,0x92,0x82,0xF8,0x80,0x90]

def setup():
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(STCP_PIN, GPIO.OUT)
    GPIO.setup(SHCP_PIN, GPIO.OUT)
    GPIO.setup(DATA_PIN, GPIO.OUT)
    GPIO.output(SHCP_PIN, GPIO.HIGH)
    for pin in BIT_CHOICE:
        GPIO.setup(pin, GPIO.OUT)    #set all pins' mode is output
        GPIO.output(pin, GPIO.LOW)

def shiftOut(val):
    for dat in range(8):
        print((val & (1<<(7-dat)) ))
        if (val & (1<<(7-dat))):
            GPIO.output(DATA_PIN, GPIO.HIGH)
        else:
            GPIO.output(DATA_PIN, GPIO.LOW)
        GPIO.output(SHCP_PIN, GPIO.HIGH)
        GPIO.output(SHCP_PIN, GPIO.LOW)
    print("\n")

while True:
    setup()
    for i in range(10):
        GPIO.output(STCP_PIN, GPIO.LOW)
        shiftOut(DisplayNumble[i])
        GPIO.output(STCP_PIN, GPIO.HIGH)
        GPIO.output(BIT_CHOICE[3], GPIO.HIGH)
        time.sleep(1)
        
    