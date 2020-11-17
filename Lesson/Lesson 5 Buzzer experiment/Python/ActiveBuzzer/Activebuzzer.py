import RPi.GPIO as GPIO
import time

buzzer = 23

GPIO.setmode(GPIO.BCM)
GPIO.setup(buzzer,GPIO.OUT)

while True:
    GPIO.output(buzzer,GPIO.HIGH)
    time.sleep(1)
    GPIO.output(buzzer,GPIO.LOW)
    time.sleep(1)
GPIO.cleanup()


