import time
import smbus as smbus
import RPi.GPIO as GPIO

ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)  
    
while True:
    ADC.write_byte(0x04,0x10)#Write a byte to the slave
    val = ADC.read_word_data(0x04,0x10);
    val = val /6500 * 4
    print(val)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    time.sleep(1)#Delay 1 second


