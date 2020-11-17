import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.Gpio;

public class Thermistor {
    static int Buzzer = 4, led_pin = 5, value = 0, fd;

    static {
        Gpio.wiringPiSetup();
        fd = I2C.wiringPiI2CSetup(0x04);
        Gpio.pinMode(led_pin, Gpio.OUTPUT);
    }

    public static void main(String[] args){
        for ( ; ;){
            value =  I2C.wiringPiI2CReadReg16(fd, 0x10);
            if(value > 200) {
                Gpio.digitalWrite(led_pin, Gpio.HIGH);
                Gpio.digitalWrite(Buzzer, Gpio.HIGH);
                Gpio.delay(1000);
            } else {
                Gpio.digitalWrite(led_pin, Gpio.LOW);
                Gpio.digitalWrite(Buzzer, Gpio.LOW);
            }
        }

    }
}