import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

public class RGB_lamp {
    int LedPinRed = 0, LedPinGreen = 1, LedPinBlue = 2;

    void ledInit() {
        SoftPwm.softPwmCreate(LedPinRed,  0, 100);
        SoftPwm.softPwmCreate(LedPinGreen,0, 100);
        SoftPwm.softPwmCreate(LedPinBlue, 0, 100);
    }
    


    void ledColorSet(int r_val, int g_val, int b_val) {
        SoftPwm.softPwmWrite(LedPinRed,   r_val);
        SoftPwm.softPwmWrite(LedPinGreen, g_val);
        SoftPwm.softPwmWrite(LedPinBlue,  b_val);
    }

    public static void main(String[] args) {
        if(Gpio.wiringPiSetup() == -1){ //when initialize wiring failed, print message to screen
            System.out.println("setup wiringPi failed !");
            return ; 
        }
        RGB_lamp rgb_lamp = new RGB_lamp();
        rgb_lamp.ledInit();
    
        while(true) {
            rgb_lamp.ledColorSet(0xff,0x00,0x00);   //red	
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0x00,0xff,0x00);   //green
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0x00,0x00,0xff);   //blue
            Gpio.delay(500);
    
            rgb_lamp.ledColorSet(0xff,0xff,0x00);   //yellow
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0xff,0x00,0xff);   //pick
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0xc0,0xff,0x3e);
            Gpio.delay(500);
    
            rgb_lamp.ledColorSet(0x94,0x00,0xd3);
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0x76,0xee,0x00);
            Gpio.delay(500);
            rgb_lamp.ledColorSet(0x00,0xc5,0xcd);	
            Gpio.delay(500);
        }
    }
}

