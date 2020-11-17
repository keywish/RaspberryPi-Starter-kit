import com.pi4j.wiringpi.Gpio;

public class Buzzer {
    static int buzzer_pin  = 4;

    static {
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(buzzer_pin, Gpio.OUTPUT);       
    }

    public static void main(String args[]) {

        for ( ; ;) { 
            //  500HZ
            for (int i = 0; i < 800; i++) {
                Gpio.digitalWrite(buzzer_pin, Gpio.HIGH);
                Gpio.delay(1);
                Gpio.digitalWrite(buzzer_pin,Gpio.LOW);
                Gpio.delay(1);
            }
            Gpio.delay(1000);

            //  250HZ
            for (int i = 0; i < 800; i++) {
                Gpio.digitalWrite(buzzer_pin, Gpio.HIGH);
                Gpio.delay(2);
                Gpio.digitalWrite(buzzer_pin,Gpio.LOW);
                Gpio.delay(2);
            }
            Gpio.delay(1000);
        }
    }
}