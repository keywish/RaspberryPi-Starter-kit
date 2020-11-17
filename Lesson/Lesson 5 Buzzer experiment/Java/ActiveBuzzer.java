import com.pi4j.wiringpi.Gpio;

public class ActiveBuzzer {
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
            Gpio.digitalWrite(buzzer_pin, Gpio.HIGH);
            Gpio.delay(5);
            Gpio.digitalWrite(buzzer_pin,Gpio.LOW);
            Gpio.delay(5);
        }
    }
}