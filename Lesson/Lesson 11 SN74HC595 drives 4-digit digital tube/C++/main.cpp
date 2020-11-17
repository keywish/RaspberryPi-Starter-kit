#include <wiringPi.h>
#include <stdio.h>

#define BIT_CHOICE_1  3
#define BIT_CHOICE_2  27
#define BIT_CHOICE_3  5
#define BIT_CHOICE_4  4
#define STCP_PIN  28
#define SHCP_PIN  29
#define DATA_PIN  6 //定义stcp shcp ds引脚
#define uint8_t unsigned char
int BIT_CHOICE[4]= {BIT_CHOICE_1 , BIT_CHOICE_2 ,BIT_CHOICE_3,BIT_CHOICE_4};// 4x8bit 
unsigned char DisplayNumble[10]=
{0xC0,0xF9,0xA4,0xB0,0x99,0x92,0x82,0xF8,0x80,0x90};

void shiftOut(uint8_t dataPin, uint8_t clockPin, uint8_t val)
{
    uint8_t i;
    for (i = 0; i < 8; i++) 
    {
        digitalWrite(dataPin, !!(val & (1 << (7-i))));
        printf("%d",!!(val & (1 << i)));
        digitalWrite(clockPin, HIGH);
        digitalWrite(clockPin, LOW); 
    }
    printf("\n");
}

int main()
{ 
    wiringPiSetup();
    pinMode(STCP_PIN, OUTPUT);
    pinMode(SHCP_PIN, OUTPUT);
    pinMode(DATA_PIN, OUTPUT); //设置 stcp shcp ds引脚为输出模式
    for(int i=0;i<4;i++)
    {
         pinMode(BIT_CHOICE[i], OUTPUT);
         digitalWrite(BIT_CHOICE[i], LOW);
    }
    digitalWrite(SHCP_PIN, HIGH);
    while(1)
    { 
         for(int i=0;i<10;i++)
            {
                digitalWrite(STCP_PIN, LOW);
                shiftOut(DATA_PIN,SHCP_PIN,DisplayNumble[i]); //serial shift out put display numble
                digitalWrite(STCP_PIN, HIGH);
                digitalWrite(BIT_CHOICE[3], HIGH);
                delay(1000);
            }
    }
}
