import time
import smbus as smbus
import RPi.GPIO as GPIO  
import time
 
LEDPIN = 19
cyc =0.5
ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)
GPIO.setup(LEDPIN, GPIO.OUT)

while True:
    ADC.write_byte(0x04,0x10)#Write a byte to the slave
    val = ADC.read_word_data(0x04,0x10)
    print(val)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    if val>=800:
        GPIO.output(LEDPIN, True)
        time.sleep(0.5)
    else:
        GPIO.output(LEDPIN, False)
    time.sleep(1)#Delay 1 second