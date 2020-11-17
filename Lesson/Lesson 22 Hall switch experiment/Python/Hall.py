import RPi.GPIO as GPIO
import time

Sensor = 22
Buzzer = 23    
led = 24

GPIO.setmode(GPIO.BCM)
GPIO.setup(Buzzer,GPIO.OUT)
GPIO.setup(led,GPIO.OUT)
GPIO.setup(Sensor,GPIO.IN)

while True:
    if  not GPIO.input(Sensor):
        GPIO.output(Buzzer,GPIO.HIGH)
        GPIO.output(led,GPIO.HIGH)
        time.sleep(1)
    else:
        GPIO.output(Buzzer,GPIO.LOW)
        GPIO.output(led,GPIO.LOW)
GPIO.clearnup()