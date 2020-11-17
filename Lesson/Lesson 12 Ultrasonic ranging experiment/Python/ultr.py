import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
trig=20 #send-pin
echo=21 #receive-pin
GPIO.setup(trig,GPIO.OUT,initial=GPIO.LOW)
GPIO.setup(echo,GPIO.IN)
 
def Measure():
 
    #send
    GPIO.output(trig,True)
    time.sleep(0.00001) #1us
    GPIO.output(trig,False)
 
    #start recording
    while GPIO.input(echo)==0:
        pass
    start=time.time()
 
    #end recording
    while GPIO.input(echo)==1:
        pass
    end=time.time()
 
    #compute distance
    distance=round((end-start)*343/2*100,2)
    print("distance:{0}cm".format(distance))
    
while True:
    Measure()
    time.sleep(1)
    
GPIO.cleanup();
 