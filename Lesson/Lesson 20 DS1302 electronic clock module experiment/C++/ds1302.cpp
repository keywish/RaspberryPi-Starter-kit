#include"ds1302.h"

//---DS1302写入和读取时分秒的地址命令---//
//---秒分时日月周年 最低位读写位;-------//
uchar  READ_RTC_ADDR[7] = {0x81, 0x83, 0x85, 0x87, 0x89, 0x8b, 0x8d}; 
uchar  WRITE_RTC_ADDR[7] = {0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c};

//---DS1302时钟初始化2016年5月7日星期六12点00分00秒。---//
//---存储顺序是秒分时日月周年,存储格式是用BCD码---//
uchar TIME[7] = {0, 0, 0x12, 0x07, 0x05, 0x06, 0x16};

/*******************************************************************************
* 函 数 名         : Ds1302Write
* 函数功能         : 向DS1302命令（地址+数据）
* 输    入         : addr,dat
* 输    出         : 无
*******************************************************************************/

void Ds1302Write(uchar addr, uchar dat)
{
    uchar n;
    digitalWrite(RST,LOW);
    delayMicroseconds(1);

    digitalWrite(SCLK,LOW);
    delayMicroseconds(1);
    digitalWrite(RST,HIGH);
    delayMicroseconds(1);

    for (n=0; n<8; n++)//开始传送八位地址命令
    {
        digitalWrite(DSIO,(addr & 0x01));
        addr >>= 1;
        digitalWrite(SCLK,HIGH);
        delayMicroseconds(1);
        digitalWrite(SCLK,LOW);
        delayMicroseconds(1);
    }
    for (n=0; n<8; n++)//写入8位数据
    {
        digitalWrite(DSIO,(dat & 0x01));
        dat >>= 1;
        digitalWrite(SCLK,HIGH);//数据在上升沿时，DS1302读取数据
        delayMicroseconds(2);
        digitalWrite(SCLK,LOW);
        delayMicroseconds(2);   
    }   

    digitalWrite(RST,LOW);         //传送数据结束
    delayMicroseconds(1);
}

/*******************************************************************************
* 函 数 名         : Ds1302Read
* 函数功能         : 读取一个地址的数据
* 输    入         : addr
* 输    出         : dat
*******************************************************************************/

uchar Ds1302Read(uchar addr)
{
    uchar n,dat=0,dat1;
    digitalWrite(RST,LOW);
    delayMicroseconds(1);
    digitalWrite(SCLK,LOW);//先将SCLK置低电平。
    delayMicroseconds(1);
    digitalWrite(RST,HIGH);//然后将RST(CE)置高电平。
    delayMicroseconds(1);

    for(n=0; n<8; n++)//开始传送八位地址命令
    {//数据从低位开始传送
        digitalWrite(DSIO,(addr & 0x01));
        addr >>= 1;
     //数据在上升沿时，DS1302读取数据
        digitalWrite(SCLK,HIGH);
        delayMicroseconds(1);
       //DS1302下降沿时，放置数据
        digitalWrite(SCLK,LOW);
        delayMicroseconds(1);
    }
    delayMicroseconds(1);
    pinMode(DSIO,INPUT);
    for(n=0; n<8; n++)//读取8位数据
    {
        dat1 = digitalRead(DSIO);//从最低位开始接收
        dat = (dat) | (dat1<<(n));
        digitalWrite(SCLK,HIGH);
        delayMicroseconds(1);
        //DS1302下降沿时，放置数据
        digitalWrite(SCLK,LOW);
        delayMicroseconds(1);
    }
    pinMode(DSIO,OUTPUT);
    digitalWrite(RST,LOW);
    delayMicroseconds(1);   //以下为DS1302复位的稳定时间,必须的。
    digitalWrite(SCLK,HIGH);
    delayMicroseconds(1);
    digitalWrite(DSIO,LOW);
    delayMicroseconds(1);
    digitalWrite(DSIO,HIGH);
    delayMicroseconds(1);
    return dat; 
}

/*******************************************************************************
* 函 数 名         : Ds1302Init
* 函数功能         : 初始化DS1302.
* 输    入         : 无
* 输    出         : 无
*******************************************************************************/

void Ds1302Init()
{
    uchar n;
    wiringPiSetup();
    pinMode(DSIO,OUTPUT); 
    pinMode(RST,OUTPUT);
    pinMode(SCLK,OUTPUT);
    Ds1302Write(0x8E,0X00);      //禁止写保护，就是关闭写保护功能
    for (n=0; n<7; n++)//写入7个字节的时钟信号：分秒时日月周年
    {
        Ds1302Write(WRITE_RTC_ADDR[n],TIME[n]); 
    }
    Ds1302Write(0x8E,0x80);      //打开写保护功能
}

/*******************************************************************************
* 函 数 名         : Ds1302ReadTime
* 函数功能         : 读取时钟信息
* 输    入         : 无
* 输    出         : 无
*******************************************************************************/

void Ds1302ReadTime()
{
    uchar n;

    for (n=0; n<7; n++)//读取7个字节的时钟信号：分秒时日月周年
    {
        TIME[n] = Ds1302Read(READ_RTC_ADDR[n]);
    }

}

/*******************************************************************************
* 函 数 名         : datapros()
* 函数功能         : 时间读取处理转换函数
* 输    入         : 无
* 输    出         : 无
*******************************************************************************/
