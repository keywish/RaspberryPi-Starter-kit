#include <wiringPi.h>
#include <stdio.h>

#define uchar unsigned char
#define uint unsigned int
#define DecodeMode 0x09 //译码模式寄存器
#define Intensity 0x0a //亮度寄存器
#define ScanLimit 0x0b //扫描位数寄存器
#define ShutDown 0x0c //低功耗模式寄存器
#define DisplayTest 0x0f //显示测试寄存器
#define ShutdownMode 0x00 //低功耗方式
#define NormalOperation 0x01 //正常操作方式
#define ScanDigit 0x07 //扫描位数设置, 显示8位数码管
#define DecodeDigit 0x00 //译码设置, 8位均为非译码
#define IntensityGrade 0x0a //亮度级别设置
#define TestMode 0x01 //显示测试模式
#define TextEnd 0x00 //显示测试结束, 恢复正常工作模式
#define DIN 4
#define CS 5
#define CLK 3

uchar buffer[8]=
{
	0x78,0xFC,0xFE,0x7F,0x7F,0xFE,0xFC,0x78,
};

void senduchar(uchar ch){
	uchar i, tmp;
	for(i = 0; i < 8; i++){
		tmp = ch & 0x80;
		if(tmp)
			digitalWrite(DIN, HIGH);
		else
			digitalWrite(DIN, LOW);
		ch = ch << 1;
		digitalWrite(CLK, HIGH);
		digitalWrite(CLK, LOW);
	}
}

void writeWord(uchar addr, uchar num){
	digitalWrite(CS, HIGH);
	digitalWrite(CS, LOW);
	digitalWrite(CLK, LOW);
	senduchar(addr);
	senduchar(num);
	digitalWrite(CS, HIGH);
}

void write(uchar (&buff)[8]){
	uchar i;
	for(i = 0; i < 8; i++){
		printf("%d %d \n", i, buff[i]);
		writeWord(i + 1, buff[i]);
		delay(20);
	}
}

void init(){
	writeWord(DecodeMode, 0x00);
	writeWord(Intensity, 0x08);
	writeWord(ScanLimit, 0x07);
	writeWord(ShutDown, 0x01);
	writeWord(DisplayTest, 0x00);
}

int main(){
	wiringPiSetup();
	pinMode(DIN, OUTPUT);
	pinMode(CS, OUTPUT);
	pinMode(CLK, OUTPUT);
	init();
	while(1){
		write(buffer);
	}
return 0;
}