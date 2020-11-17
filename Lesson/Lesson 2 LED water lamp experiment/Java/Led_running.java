import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Led_running {
    
    public static void main(String[] args) throws InterruptedException {
        
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        
        // provision gpio pin as an output pin and turn on
        final GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.HIGH);
        final GpioPinDigitalOutput pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED", PinState.HIGH);
        final GpioPinDigitalOutput pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "MyLED", PinState.HIGH);
        final GpioPinDigitalOutput pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "MyLED", PinState.HIGH);
        final GpioPinDigitalOutput pin5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "MyLED", PinState.HIGH);
        
        Thread.sleep(5000);
        
        // // turn on gpio pin #01
        // pin.low();
        // System.out.println("--> GPIO state should be: OFF");

        // Thread.sleep(5000);
        for ( ; ; ) {
            pin1.toggle();
            System.out.println("--> GPIO state should be: ON");
            Thread.sleep(1000);
            pin1.toggle();
            System.out.println("--> GPIO state should be: OFF");
            Thread.sleep(1000);

            pin2.toggle();
            System.out.println("--> GPIO state should be: ON");
            Thread.sleep(1000);
            pin2.toggle();
            System.out.println("--> GPIO state should be: OFF");
            Thread.sleep(1000);

            pin3.toggle();
            System.out.println("--> GPIO state should be: ON");
            Thread.sleep(1000);
            pin3.toggle();
            System.out.println("--> GPIO state should be: OFF");
            Thread.sleep(1000);

            pin4.toggle();
            System.out.println("--> GPIO state should be: ON");
            Thread.sleep(1000);
            pin4.toggle();
            System.out.println("--> GPIO state should be: OFF");
            Thread.sleep(1000);

            pin5.toggle();
            System.out.println("--> GPIO state should be: ON");
            Thread.sleep(1000);
            pin5.toggle();
            System.out.println("--> GPIO state should be: OFF");
            Thread.sleep(1000);
        }
    }
}
