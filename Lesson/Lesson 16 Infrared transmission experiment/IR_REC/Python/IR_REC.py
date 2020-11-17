#!/usr/bin/python
# -*- coding:utf-8 -*-
import RPi.GPIO as GPIO
import time
ERROR = 0xFE
PIN = 19
     #定义红外接收器引脚
 
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(PIN, GPIO.IN, GPIO.PUD_UP)   #设置红外接收引脚为上拉模式
 
def getKey():                       
    byte = [0, 0, 0, 0]
    if IRStart() == False:      #判断红外引导脉冲
        time.sleep(0.11)        # One message frame lasts 108 ms.
        return ERROR
    else:
        for i in range(0, 4):
                byte[i] = getByte()  #接收32位红外数据（地址、地址反码、数据、数据反码）
        if byte[0] + byte[1] == 0xff and byte[2] + byte[3] == 0xff:  #校验接收数据是否正确
	    print("right")
            return byte[2]
        else:
	    print("error")
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
    print(timeSpan[0],timeSpan[1])
    if timeSpan[0] > 0.0085 and timeSpan[0] < 0.0095 and timeSpan[1] > 0.004 and timeSpan[1] < 0.005:
	print("1")
        return True
    else:
	print("0")
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
print('IRM Test Start ...')
try:
    while True:
        key = getKey()     #读取红外脉冲
        if(key != ERROR):  #打印红外脉冲值
            print("Get the key: 0x%02x" %key)
except KeyboardInterrupt:
    GPIO.cleanup()


