import com.pi4j.wiringpi.Gpio;
import com.pi4j.io.gpio.impl.PinImpl;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import java.util.Scanner;


public class Adjustment_servo {
    static int PWMPIN  = 21;
    static int value;
    static {
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(PWMPIN, Gpio.OUTPUT);       
    }

    static void led_pwm(int val){
        Gpio.digitalWrite(PWMPIN, Gpio.HIGH);
        Gpio.delayMicroseconds(500 + val*500 / 45); 
        Gpio.digitalWrite(PWMPIN,Gpio.LOW);
        Gpio.delayMicroseconds((20000 - (500 + val*500 / 45)));    
    }

    public static void main(String args[]) throws InterruptedException{

        for ( ; ; ) {
            System.out.println("输入的数据范围为0~180");
            Scanner scan = new Scanner(System.in);
            // 判断是否还有输入
            if (scan.hasNext()) {
                String str1 = scan.next();
                value = Integer.parseInt(str1);
                System.out.println("输入的数据为：" + value);
            }
            
            for (int i = 0; i < 10; i++) {
                Adjustment_servo.led_pwm(value);  
            }
        }
    }
}