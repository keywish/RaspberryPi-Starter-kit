import time
import smbus as smbus
import RPi.GPIO as GPIO  
import signal  
import atexit 
 
atexit.register(GPIO.cleanup)
servopin = 20  
cyc = 0.2
ADC=smbus.SMBus(1)#Declare to use I2C 1
GPIO.setmode(GPIO.BCM)
GPIO.setup(servopin, GPIO.OUT)  

def pwm_change(temp):
    GPIO.output(servopin ,True)
    time.sleep(0.0005+float(temp)*0.0005/45)
    GPIO.output(servopin , False)
    time.sleep(0.02-(0.0005+float(temp)*0.0005/45))  
    
    
while True:
    ADC.write_byte(0x04,0x10)#Write a byte to the slave
    val = ADC.read_word_data(0x04,0x10)
    val = float(val * 180) / 4095
    print(val)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    pwm_change(int(val))
    time.sleep(0.05)#Delay 1 second