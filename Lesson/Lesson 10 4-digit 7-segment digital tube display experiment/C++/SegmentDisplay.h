#ifndef _SEGMENT_DISPLY_H_
#define _SEGMENT_DISPLY_H_
#include <wiringPi.h>
#include <stdio.h>
#include <cstdint>

//#define uint8_t unsigned char
//the common VCC pin dight value
#define DIGHT_0 0xC0
#define DIGHT_1 0xF9
#define DIGHT_2 0xA4
#define DIGHT_3 0xB0
#define DIGHT_4 0x99
#define DIGHT_5 0x92
#define DIGHT_6 0x82
#define DIGHT_7 0xF8
#define DIGHT_8 0x80
#define DIGHT_9 0x90
#define DIGHT_OFF 0x00

#define DIGHT_NUMBLE(n) DIGHT_##n

typedef enum  {
    SEGMENT_DISPLY_1BIT,
    SEGMENT_DISPLY_4BIT,
    SEGMENT_DISPLY_8BIT
}E_DIGHT_DISPLYA_TYPE;
class SegmentDisplay {

private :
    uint8_t dight_pin[8];
    uint8_t dight_select_pin[4];
    E_DIGHT_DISPLYA_TYPE mode;
    void numble2dis(int numble);
public :
    unsigned char dight_display[10];
    unsigned char display_array[4];
    SegmentDisplay(uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e, uint8_t f, uint8_t g, uint8_t h);
    SegmentDisplay(uint8_t a, uint8_t b, uint8_t c, uint8_t d, uint8_t e, uint8_t f, uint8_t g, uint8_t h, uint8_t d1, uint8_t d2, uint8_t d3, uint8_t d4);
    void TurnOffAllLed(void);
    void DisplayChar(unsigned char n);
    void DisplayChar(int n);
    void DisplayChar(unsigned char sel, unsigned char n);
};

#endif
