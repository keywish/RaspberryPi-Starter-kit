import time
import smbus as smbus
import RPi.GPIO as GPIO

JOYSTICK_SW =   18
LED_ENTER   = 22#enter
LED_LEFT    = 23#left
LED_UP      = 24#up
LED_RIGHT   = 25#right
LED_DOWN    = 26#down

ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)
GPIO.setup(JOYSTICK_SW,GPIO.IN)
GPIO.setup(LED_ENTER,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(LED_LEFT,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(LED_UP,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(LED_RIGHT,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(LED_DOWN,GPIO.OUT,initial=GPIO.LOW)
    
while True: 
    ADC.write_byte(0x04,0x10)#Write a byte to the slave
    value_x = ADC.read_byte_data(0x04,0x10)
    value_y = ADC.read_byte_data(0x04,0x11)
    value_sw = GPIO.input(JOYSTICK_SW)
      
    time.sleep
    if value_x==0:
        GPIO.output(LED_LEFT,True)
        time.sleep(0.5)
        print("left")
        GPIO.output(LED_LEFT,False)
    if value_x==255:
        GPIO.output(LED_RIGHT,True)
        time.sleep(0.5)
        print("right")
        GPIO.output(LED_RIGHT,False)
    if value_y==0:
        GPIO.output(LED_UP,True)
        time.sleep(0.5)
        print("up")
        GPIO.output(LED_UP,False)
    if value_y==255:
        GPIO.output(LED_DOWN,True)
        time.sleep(0.5)
        print("down")
        GPIO.output(LED_DOWN,False)
    if value_sw == 0:
        GPIO.output(LED_ENTER,True)
        time.sleep(0.5)
        print("enter")
        GPIO.output(LED_ENTER,False)


