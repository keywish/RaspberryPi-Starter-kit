import RPi.GPIO as GPIO

button = 17
led = 18

GPIO.setmode(GPIO.BCM)
GPIO.setup(led,GPIO.OUT)
GPIO.setup(button,GPIO.IN)


while True:

    if GPIO.input(button):

        GPIO.output(led,GPIO.HIGH)
    else:
        GPIO.output(led,GPIO.LOW)
GPIO.cleanup()

