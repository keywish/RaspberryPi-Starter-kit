import RPi.GPIO as GPIO
import time

buzzer = 23
ledpin = 18
sw = 21

GPIO.setmode(GPIO.BCM)
GPIO.setup(ledpin,GPIO.OUT)
GPIO.setup(buzzer,GPIO.OUT)
GPIO.setup(sw,GPIO.IN)


while True:

    if GPIO.input(sw):
        GPIO.output(ledpin,GPIO.HIGH)
        GPIO.output(buzzer,GPIO.HIGH)
        time.sleep(1)
        GPIO.output(ledpin,GPIO.LOW)
        GPIO.output(buzzer,GPIO.LOW)
        time.sleep(0.02)
GPIO.cleanup()

