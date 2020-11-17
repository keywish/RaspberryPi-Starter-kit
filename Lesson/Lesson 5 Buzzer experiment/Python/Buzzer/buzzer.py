import time
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.OUT)
  
p = GPIO.PWM(23, 100)
p.start(0)
try:
    while 1:
        for dc in range(0, 100):
            p.ChangeDutyCycle(dc)
            time.sleep(1)
        for dc in range(0, 100, 5):
            p.ChangeDutyCycle(dc)
            time.sleep(1)
except KeyboardInterrupt:
    pass
p.stop()
GPIO.cleanup()