# -*- coding:utf-8 -*-

import RPi.GPIO as GPIO
import time

DecodeMode = 0x09 #译码模式寄存器
Intensity = 0x0a #亮度寄存器
ScanLimit = 0x0b #扫描位数寄存器
ShutDown = 0x0c #低功耗模式寄存器
DisplayTest = 0x0f #显示测试寄存器
ShutdownMode = 0x00 #低功耗方式
NormalOperation = 0x01 #正常操作方式
ScanDigit = 0x07 #扫描位数设置, 显示8位数码管
DecodeDigit = 0x00 #译码设置, 8位均为非译码
IntensityGrade = 0x0a #亮度级别设置
TestMode = 0x01 #显示测试模式
TextEnd = 0x00 #显示测试结束, 恢复正常工作模式
buffer =[ 0x78, 0xFC, 0xFE, 0x7F, 0x7F, 0xFE, 0xFC, 0x78 ]

class Max7219:

	def __init__(self, CLK, DIN, CS):
		self.DIN = DIN
		self.CS = CS
		self.CLK = CLK
		GPIO.setmode(GPIO.BCM)
		GPIO.setup(self.DIN, GPIO.OUT)
		GPIO.setup(self.CS, GPIO.OUT)
		GPIO.setup(self.CLK, GPIO.OUT)


	def start(self):
		self.writeWord(DecodeMode, 0x00)
		self.writeWord(Intensity, 0x08)
		self.writeWord(ScanLimit, 0x07)
		self.writeWord(ShutDown, 0x01)
		self.writeWord(DisplayTest, 0x00)


	def senduchar(self, ch):
		for i in range(0, 8):
			tmp = ch & 0x80
			if tmp:
				GPIO.output(self.DIN, GPIO.HIGH)
			else:
				GPIO.output(self.DIN, GPIO.LOW)
			ch = ch << 1
			GPIO.output(self.CLK, GPIO.HIGH)
			GPIO.output(self.CLK, GPIO.LOW)


	def writeWord(self, addr, num):
		GPIO.output(self.CS, GPIO.HIGH)
		GPIO.output(self.CS, GPIO.LOW)
		GPIO.output(self.CLK, GPIO.LOW)
		self.senduchar(addr)
		self.senduchar(num)
		GPIO.output(self.CS, GPIO.HIGH)

	def write(self, buff):
		for i in range(0, 8):
			self.writeWord(i + 1, buff[i])
			time.sleep(0.02)


max7219 = Max7219(22, 23, 24)
max7219.start()
while True:
	max7219.write(buffer)
