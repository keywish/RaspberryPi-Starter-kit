#!/usr/bin/python
# -*- coding:utf-8 -*-
import RPi.GPIO as GPIO
import time
ERROR = 0xFE
PIN = 19
flag = 0
motor = 6
servopin = 20
     #定义红外接收器引脚
keymap = {
   0x45:"1",                                              
   0x46:"2",                                              
   0x47:"3",                                              
   0x44:"4",                                              
   0x40:"5",                                              
   0x43:"6",                                              
   0x07:"7",                                              
   0x15:"8",                                              
   0x09:"9",                                              
   0x19:"0",                                              
   0x16:"*",                                              
   0x0D:"#",                                              
   0x18:"up",                                             
   0x52:"down",                                           
   0x1C:"ok",                                             
   0x08:"left",                                           
   0x5A:"right",
   0xfe:0xfe
}
 
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(PIN, GPIO.IN, GPIO.PUD_UP)   #设置红外接收引脚为上拉模式
GPIO.setup(motor,GPIO.OUT)
GPIO.setup(servopin,GPIO.OUT)
 
def getKey():                       
    byte = [0, 0, 0, 0]
    if IRStart() == False:      #判断红外引导脉冲
        time.sleep(0.11)        # One message frame lasts 108 ms.
        return ERROR
    else:
        for i in range(0, 4):
                byte[i] = getByte()  #接收32位红外数据（地址、地址反码、数据、数据反码）
        if byte[0] + byte[1] == 0xff and byte[2] + byte[3] == 0xff:  #校验接收数据是否正确
#print("right")
            return byte[2]
        else:
	    #print("error")
            return ERROR
	#return byte[2]
def IRStart():     #判断红外引导脉冲
    timeFallingEdge = [0, 0]
    timeRisingEdge = 0
    timeSpan = [0, 0]
    #通过脉冲上升沿和下降沿读取脉冲时间
    GPIO.wait_for_edge(PIN, GPIO.FALLING)
    timeFallingEdge[0] = time.time()
    GPIO.wait_for_edge(PIN, GPIO.RISING)
    timeRisingEdge = time.time()
    GPIO.wait_for_edge(PIN, GPIO.FALLING)
    timeFallingEdge[1] = time.time()
    
    timeSpan[0] = timeRisingEdge - timeFallingEdge[0]  #第一个脉冲时间
    timeSpan[1] = timeFallingEdge[1] - timeRisingEdge  #第二个脉冲时间
    #print(timeSpan[0],timeSpan[1])
    if timeSpan[0] > 0.0085 and timeSpan[0] < 0.0095 and timeSpan[1] > 0.004 and timeSpan[1] < 0.005:
	#print("1")
        return True
    else:
	#print("0")
        return False
def getByte():   #接收32位红外数据（地址、地址反码、数据、数据反码）
    byte = 0
    timeRisingEdge = 0
    timeFallingEdge = 0
    timeSpan = 0
    for i in range(0, 8):
        #通过脉冲上升沿和下降沿读取脉冲时间
        GPIO.wait_for_edge(PIN, GPIO.RISING)
        timeRisingEdge = time.time()
        GPIO.wait_for_edge(PIN, GPIO.FALLING)
        timeFallingEdge = time.time()
        
        timeSpan = timeFallingEdge - timeRisingEdge   #读取脉冲时间
        if timeSpan > 0.0016 and timeSpan < 0.0018:   #判断脉冲是否为代表1
            byte |= 1 << i
    return byte
    
def change_map(key_val):
    for index in keymap.keys():
        if index == key_val :
            return keymap[index]
            
def pwm_change(temp):
    GPIO.output(servopin ,True)
    time.sleep(0.0005+float(temp)*0.0005/45)
    GPIO.output(servopin , False)
    time.sleep(0.02-(0.0005+float(temp)*0.0005/45)) 
            
print('IRM Test Start ...')
try:
    while True:
        #读取红外脉冲
        key = change_map(getKey())
        if(key != ERROR):  #打印红外脉冲值
            print("Get the key:" + key)
            if(key == "ok"):
				print("IR_KEYCODE_OK key")
				flag = 1 - flag
				GPIO.output(motor,flag)
			
            if(key == "left"):
				print("IR_KEYCODE_left key")
				pwm_change(0)
				time.sleep(0.5)
                
            if(key == "right"):
				print("IR_KEYCODE_right key")
				pwm_change(180)
				time.sleep(0.5)
                
except KeyboardInterrupt:
    GPIO.cleanup()


