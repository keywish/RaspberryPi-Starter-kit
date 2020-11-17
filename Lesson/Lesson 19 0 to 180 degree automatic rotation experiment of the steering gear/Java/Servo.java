import com.pi4j.wiringpi.Gpio;

public class Servo {
    static int servo_pin  = 21;

    static {
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(servo_pin, Gpio.OUTPUT);       
    }

    static void led_pwm(int val){
        Gpio.digitalWrite(servo_pin, Gpio.HIGH);
        Gpio.delayMicroseconds(500 + val*500 / 45); 
        Gpio.digitalWrite(servo_pin,Gpio.LOW);
        Gpio.delayMicroseconds((20000 - (500 + val*500 / 45)));    
    }

    public static void main(String args[]) {
        for ( ; ;) { 
            for(int i = 0; i < 180; i++) {
                Servo.led_pwm(i);  
            }

            for(int i = 180; i > 0; i--) {
                Servo.led_pwm(i);  
            }
        }
    }
}