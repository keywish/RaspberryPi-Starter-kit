import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;

public class Potentiometer_adjustment {
    static int LEDPIN  = 21;
    static int value;
    static {
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(LEDPIN, Gpio.OUTPUT);       
    }

    static void led_pwm(int val){
        Gpio.digitalWrite(LEDPIN, Gpio.HIGH);
        Gpio.delayMicroseconds(500 + val*500 / 45); 
        Gpio.digitalWrite(LEDPIN,Gpio.LOW);
        Gpio.delayMicroseconds((20000 - (500 + val*500 / 45)));    
    }

    public static void main(String args[]) throws InterruptedException{
        int fd = I2C.wiringPiI2CSetup(0x04); 
        for ( ; ;) { 
            int Potentiometer_val =  I2C.wiringPiI2CReadReg16(fd, 0x10); 
            Potentiometer_val = Potentiometer_val * 180 / 4095;
            System.out.println(Potentiometer_val);
            for(int i = 0; i < 10; i++) {
                Potentiometer_adjustment.led_pwm(Potentiometer_val);  
            }
        }
    }
}