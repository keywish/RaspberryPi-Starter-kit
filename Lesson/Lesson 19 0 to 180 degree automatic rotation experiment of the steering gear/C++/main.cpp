#include <stdio.h>
#include <string.h>
#include <wiringPi.h>
#include <wiringSerial.h>
#include <iostream>
using namespace std;

int cyc =20000;
int pwmPin = 28;
int value;
void pwm_fun(int temp)
{
    digitalWrite(pwmPin,HIGH);
    delayMicroseconds(500+temp*500/45); 
    digitalWrite(pwmPin,0);
    delayMicroseconds((cyc-(500+temp*500/45))); 
}

int main()
{
    wiringPiSetup();
    pinMode(pwmPin,OUTPUT);
    for(int i=0; i<=180; i+=10)
    {
	pwm_fun(i);
    }
    while(1);
    
}
