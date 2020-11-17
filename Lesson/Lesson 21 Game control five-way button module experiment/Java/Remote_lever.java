import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;

public class Remote_lever {
    static int JOYSTICK_SW = 1, LED_ENTER = 3, LED_LEFT = 4, LED_UP = 5, LED_RIGHT = 6, LED_DOWN = 25;
    static int value_x,value_y,value_sw;
     static {
                 // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }
        //Gpio.pinMode(JOYSTICK_SW, Gpio.INPUT);
        Gpio.pullUpDnControl(JOYSTICK_SW, Gpio.PUD_UP);
        Gpio.pinMode(LED_ENTER, Gpio.OUTPUT);
        Gpio.pinMode(LED_LEFT, Gpio.OUTPUT);
        Gpio.pinMode(LED_UP, Gpio.OUTPUT);
        Gpio.pinMode(LED_RIGHT, Gpio.OUTPUT);
        Gpio.pinMode(LED_DOWN, Gpio.OUTPUT);
        Gpio.digitalWrite(LED_ENTER,Gpio.LOW);
        Gpio.digitalWrite(LED_LEFT,Gpio.LOW);
        Gpio.digitalWrite(LED_UP,Gpio.LOW);
        Gpio.digitalWrite(LED_RIGHT,Gpio.LOW);
        Gpio.digitalWrite(LED_DOWN,Gpio.LOW);
    }
    public static void main(String args[]) throws InterruptedException{
    
        int fd = I2C.wiringPiI2CSetup(0x04);
        for (;;) {
            value_sw = Gpio.digitalRead(JOYSTICK_SW);
            value_x =  I2C.wiringPiI2CReadReg16(fd, 0x10);
            value_y =  I2C.wiringPiI2CReadReg16(fd, 0x11);

            if(value_x >= 0 && value_x <= 1500) {
                System.out.println(" left");
                Gpio.digitalWrite(LED_LEFT, Gpio.HIGH);
                Gpio.delay(200);
                Gpio.digitalWrite(LED_LEFT, Gpio.LOW);
            }

            if(value_x >= 3500 && value_x <= 4095) {
                System.out.println(" right"); //printf("right");
                Gpio.digitalWrite(LED_RIGHT, Gpio.HIGH);
                Gpio.delay(200);
                Gpio.digitalWrite(LED_RIGHT, Gpio.LOW);
            }

            if(value_y >= 0 && value_y <= 1500) {
                System.out.println(" up");//printf("up");
                Gpio.digitalWrite(LED_UP, Gpio.HIGH);
                Gpio.delay(200);
                Gpio.digitalWrite(LED_UP, Gpio.LOW);
            }

            if(value_y >= 3500 && value_y <= 4095) {
                System.out.println(" down");//printf("down");
                Gpio.digitalWrite(LED_DOWN, Gpio.HIGH);
                Gpio.delay(200);
                Gpio.digitalWrite(LED_DOWN, Gpio.LOW);
            }

            if(value_sw == 0 ) {
                Gpio.delay(200);
                if(value_sw == 0 ) {
                    System.out.println("enter");//printf("enter");
                    Gpio.digitalWrite(LED_ENTER, Gpio.HIGH);
                    Gpio.delay(200);
                    Gpio.digitalWrite(LED_ENTER, Gpio.LOW);
                }
            }
        }

    }
}