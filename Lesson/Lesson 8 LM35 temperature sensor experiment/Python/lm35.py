import time
import smbus as smbus

ADC=smbus.SMBus(1)#Declare to use I2C 1
    
while True:
    ADC.write_byte(0x04,0x20)#Write a byte to the slave
    val = ADC.read_word_data(0x04,0x20);
    temp = val / 10 / 1023 * 5 * 100  
    print(temp)#Raspberry Pi reads the data returned by the expansion board and prints it out  
    time.sleep(1)#Delay 1 second