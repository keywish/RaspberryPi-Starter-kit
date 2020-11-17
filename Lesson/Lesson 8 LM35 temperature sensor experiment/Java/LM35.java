import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;

public class LM35 {
    static int  value;
    static float voltage;

    public static void main(String args[]) throws InterruptedException{
    
        int fd = I2C.wiringPiI2CSetup(0x04);
        for (;;) {
            value =  I2C.wiringPiI2CReadReg16(fd, 0x10); 
            voltage = ( ( float )value ) / 1023 / 10;
            value =   (int)(voltage * 100 * 5); 
            System.out.println("当前温度：" + value +" C");
            Gpio.delay(1000);
        }
    }
}