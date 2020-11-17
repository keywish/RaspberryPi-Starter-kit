#include "LiquidCrystal_I2C.h"

 int LCDAddr = 0x27;	//LCD设备地址
 int fd;
 int BLEN = 1;

void write_word(int data){
	int temp = data; 
	if ( BLEN == 1 )
		temp |= 0x08;
	else
		temp &= 0xF7;
	wiringPiI2CWrite(fd, temp);
}

void send_command(int comm){
	int buf;
	// Send bit7-4 firstly
	buf = comm & 0xF0;
	buf |= 0x04;			// RS = 0（低电平0时选择指令寄存器）, RW = 0(此时发送指令), EN = 1
	write_word(buf);
	delay(2);
	buf &= 0xFB;			// Make EN = 0，EN从1——>0，下降沿，进行写操作
	write_word(buf);

	// Send bit3-0 secondly
	buf = (comm & 0x0F) << 4;
	buf |= 0x04;			// RS = 0, RW = 0, EN = 1
	write_word(buf);
	delay(2);
	buf &= 0xFB;			// Make EN = 0
	write_word(buf);
}

//发送数据的函数
void send_data(int data){
	int buf;
	// Send bit7-4 firstly
	buf = data & 0xF0;
	buf |= 0x05;			// RS = 1, RW = 0, EN = 1
	write_word(buf);
	delay(2);
	buf &= 0xFB;			// Make EN = 0
	write_word(buf);

	// Send bit3-0 secondly
	buf = (data & 0x0F) << 4;
	buf |= 0x05;			// RS = 1, RW = 0, EN = 1
	write_word(buf);
	delay(2);
	buf &= 0xFB;			// Make EN = 0
	write_word(buf);
}

//初始化函数
void init(){
	fd = wiringPiI2CSetup(LCDAddr);
	send_command(0x33);	// Must initialize to 8-line mode at first
	delay(5);
	send_command(0x32);	// Then initialize to 4-line mode
	delay(5);
	send_command(0x28);	// 2 Lines & 5*7 dots
	delay(5);
	send_command(0x0C);	// Enable display without cursor
	delay(5);
	send_command(0x01);	// Clear Screen
	wiringPiI2CWrite(fd, 0x08);
}
//清屏
void clear(){
	send_command(0x01);	//clear Screen
}

void write(int x, int y, char data[]){
	int addr, i;
	int tmp;
	if (x < 0)  x = 0;
	if (x > 15) x = 15;
	if (y < 0)  y = 0;
	if (y > 1)  y = 1;
	
	addr = 0x80 + 0x40 * y + x;
	send_command(addr);
	
	tmp = strlen(data);
	for (i = 0; i < tmp; i++){
		send_data(data[i]);
	}
}

void int2str(int n, char *str)
{
 char buf[10] = "";
 int i = 0;
 int len = 0;
 int temp = n < 0 ? -n : n;  // temp为n的绝对值
 
 if (str == NULL)
 {
  return;
 }
 while (temp)
 {
  buf[i++] = (temp % 10) + '0';  //把temp的每一位上的数存入buf
  temp = temp / 10;
 }
 
 len = n < 0 ? ++i : i;  //如果n是负数，则多需要一位来存储负号
 str[i] = 0;             //末尾是结束符0
 while (1)
 {
  i--;
  if (buf[len - i - 1] == 0)
  {
   break;
  }
  str[i] = buf[len - i - 1];  //把buf数组里的字符拷到字符串
 }
 if (i == 0)
 {
  str[i] = '-';          //如果是负数，添加一个负号
 }
}

