import time
import smbus as smbus
import RPi.GPIO as GPIO

ledpin = 27
ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)  
GPIO.setup(ledpin, GPIO.OUT)
    
while True:
    ADC.write_byte(0x04,0x20)#Write a byte to the slave
    val = ADC.read_word_data(0x04,0x20);
    temp = val / 1023 * 5 * 100  
    if temp>0:
        GPIO.output(ledpin, True)
        time.sleep(1)
    else:
        GPIO.output(ledpin, False)
    print(temp)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    time.sleep(1)#Delay 1 second
