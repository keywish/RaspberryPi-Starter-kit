import com.pi4j.wiringpi.Gpio;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
enum MODE 
{ 
    SEGMENT_DISPLY_1BIT,
    SEGMENT_DISPLY_4BIT,
    SEGMENT_DISPLY_8BIT;
}

    public class Digital_Tube {
        char dight_pin[] = {29, 28, 25, 24, 23, 26, 6, 22};
        char dight_display[] = {0xC0, 0xF9, 0xA4, 0xB0, 0x99, 0x92, 0x82, 0xF8, 0x80, 0x90, 0x00};
        char dight_select_pin[] = {4, 5, 27, 3};
        List list = new ArrayList();
        MODE mode;

    static {
        Gpio.wiringPiSetup();
    }

    void SegmentDisplay() { 
        mode = MODE.SEGMENT_DISPLY_1BIT;
        for (int i = 0; i < 8; i++) {
            Gpio.pinMode(dight_pin[i], Gpio.OUTPUT);  //set all led diplay array pin output mode
            Gpio.digitalWrite(dight_pin[i], Gpio.HIGH);
        }
    }

    void SegmentDisplay(int segment) {
        mode = MODE.SEGMENT_DISPLY_4BIT;
        for (int i = 0; i < 8; i++) {
            Gpio.pinMode(dight_pin[i], Gpio.OUTPUT);  //set all led diplay array pin output mode
            Gpio.digitalWrite(dight_pin[i], Gpio.LOW);
        }
        for (int i = 0; i < 4; i++) {
            Gpio.pinMode(dight_select_pin[i], Gpio.OUTPUT);  //set all led diplay array pin output mode
            Gpio.digitalWrite(dight_select_pin[i], Gpio.LOW);
        }
    }

    void TurnOffAllLed() {
        for (int i = 0; i < 8; i++)
            Gpio.digitalWrite(dight_pin[i], Gpio.LOW);
        if (mode == MODE.SEGMENT_DISPLY_4BIT) {
            for (int i = 0; i < 4; i++)
                Gpio.digitalWrite(dight_select_pin[i], Gpio.LOW);
        }
    }

    void numble2dis(int numble) {
        int numble_bit = 0;
        int bit_base = 1000;
        for (numble_bit = 0; numble_bit < 4; numble_bit++ ) {
            if (numble/bit_base != 0) {
                list.add(numble_bit, numble/bit_base);
                numble = numble%bit_base;
            } else {
                list.add(numble_bit, 0);
            }
            bit_base = bit_base / 10;
        }
    }

    void Display_One_Char(int n) {
        char ch = dight_display[n];
        if (n < 10) {
            for (int i = 0; i < 8; i++) {
                if ((ch & (1 << i)) == 0) {
                    Gpio.digitalWrite(dight_pin[i], Gpio.LOW);
                } else {
                    Gpio.digitalWrite(dight_pin[i], Gpio.HIGH);
                }
            }
        }
    }

    void Display_Four_Char(int n) {
        char ch = dight_display[n];
        if (n < 10) {
            for (int i = 0; i < 8; i++) {
                if ((ch & (1 << i)) == 0) {
                    Gpio.digitalWrite(dight_pin[i], Gpio.HIGH);
                } else {
                    Gpio.digitalWrite(dight_pin[i], Gpio.LOW);
                }
            }
        }
    }

    void DisplayChar(int n) {
        if (mode == MODE.SEGMENT_DISPLY_4BIT) {
            numble2dis(n);
            for(int i = 0; i < 4; i++) {
                Display_Four_Char((int)list.get(i));
                Gpio.digitalWrite(dight_select_pin[i], Gpio.LOW); 
                Gpio.delay(5);
                Gpio.digitalWrite(dight_select_pin[i], Gpio.HIGH);
            }
        }
    } 

    void DisplayChar(char sel, char n) {
        char ch = dight_display[n];
        for (int i = 0; i < 8; i++) {
            if ((ch & (1 << i)) == 0) {
                Gpio.digitalWrite(dight_pin[i], Gpio.HIGH);
            } else {
                Gpio.digitalWrite(dight_pin[i], Gpio.LOW);
            }
        }
        Gpio.digitalWrite(dight_select_pin[sel] , Gpio.HIGH);
    }

    public static void main(String[] args) {
        Digital_Tube digital_tube = new Digital_Tube();
        digital_tube.SegmentDisplay(4);
        int ShowTime = 0, count = 0;
        while (true) {
            for (int i = 60; i >= 0; i--) {
                if (ShowTime > 60 ) {
                ShowTime = 0 ;
                }
                digital_tube.DisplayChar(ShowTime);
                ShowTime++ ;
            }
        }
    }            
}