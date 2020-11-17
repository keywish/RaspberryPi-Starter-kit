import RPi.GPIO as GPIO
import time

pin_val = [22, 23, 24, 25, 18, 19, 20, 21]
key = [
  ['1','2','3','A'],
  ['4','5','6','B'],
  ['7','8','9','C'],
  ['*','0','#','D']
]
GPIO.cleanup()
GPIO.setmode(GPIO.BCM)

def out_set(n):
    for i in range(0,4):
        GPIO.setup(pin_val[i],GPIO.OUT)
        GPIO.setup(pin_val[i+4], GPIO.IN, pull_up_down=GPIO.PUD_UP)
    if n == 0:
        GPIO.output(pin_val[0],GPIO.HIGH)
        GPIO.output(pin_val[1],GPIO.LOW)
        GPIO.output(pin_val[2],GPIO.LOW)
        GPIO.output(pin_val[3],GPIO.LOW)
    if n == 1:
        GPIO.output(pin_val[0],GPIO.LOW)
        GPIO.output(pin_val[1],GPIO.HIGH)
        GPIO.output(pin_val[2],GPIO.LOW)
        GPIO.output(pin_val[3],GPIO.LOW)            
    if n == 2:
        GPIO.output(pin_val[0],GPIO.LOW)
        GPIO.output(pin_val[1],GPIO.LOW)
        GPIO.output(pin_val[2],GPIO.HIGH)
        GPIO.output(pin_val[3],GPIO.LOW)
    if n == 3:
        GPIO.output(pin_val[0],GPIO.LOW)
        GPIO.output(pin_val[1],GPIO.LOW)
        GPIO.output(pin_val[2],GPIO.LOW)
        GPIO.output(pin_val[3],GPIO.HIGH)
       
while True:
    for n in range(0,4):
        out_set(n)
        for m in range(0,4):
            if GPIO.input(pin_val[m+4]):
                time.sleep(0.1)
                if GPIO.input(pin_val[m+4]):
                    print ('You enter the  key:',key[n][m])