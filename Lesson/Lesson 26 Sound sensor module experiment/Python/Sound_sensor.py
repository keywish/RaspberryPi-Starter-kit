import time
import smbus as smbus
import RPi.GPIO as GPIO

led = 19
ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)  
GPIO.setup(led, GPIO.OUT)
    
while True:
    ADC.write_byte(0x04,0x10)#Write a byte to the slave
    val = ADC.read_byte_data(0x04,0x10);
    if val >= 100:
        GPIO.output(led, True)
        time.sleep(0.8)
    else:
        GPIO.output(led, False)
    print(val)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    time.sleep(1)#Delay 1 second


