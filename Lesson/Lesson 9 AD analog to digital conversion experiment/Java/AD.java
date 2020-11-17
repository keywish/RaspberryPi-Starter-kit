import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;
import com.pi4j.wiringpi.SoftPwm;

public class AD {
    static int LEDPIN  = 21;
    static int value,value_y,value_sw;
     static {
                 // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(LEDPIN, Gpio.OUTPUT);
    }

    static void led_pwm(int val){
        // create soft-pwm pins (min=0 ; max=100)
        SoftPwm.softPwmCreate(LEDPIN, 0, 100);
        SoftPwm.softPwmWrite(LEDPIN, val);
    }

    public static void main(String args[]) throws InterruptedException{
    
        int fd = I2C.wiringPiI2CSetup(0x04);
        for (;;) {
            value =  I2C.wiringPiI2CReadReg16(fd, 0x10) * 100 / 4096;
            AD.led_pwm(value);   
        }

    }
}