import java.util.concurrent.Callable;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;

public class Tilt {
    
    public static void main(String[] args) throws InterruptedException {

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput sw   = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, 
                                                  PinPullResistance.PULL_DOWN);

        // setup gpio pins #04, #05, #06 as an output pins and make sure they are all LOW at startup
        GpioPinDigitalOutput TAB[] = { 
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Buzzer", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "led", PinState.LOW), 
        };

        // create a gpio control trigger on the input pin ; when the input goes HIGH, also set gpio pin to HIGH
        sw.addTrigger(new GpioSetStateTrigger(PinState.HIGH, TAB[0], PinState.HIGH));
        sw.addTrigger(new GpioSetStateTrigger(PinState.HIGH, TAB[1], PinState.HIGH));

        // create a gpio control trigger on the input pin ; when the input goes LOW, also set gpio pin to LOW
        sw.addTrigger(new GpioSetStateTrigger(PinState.LOW, TAB[0], PinState.LOW));
        sw.addTrigger(new GpioSetStateTrigger(PinState.LOW, TAB[1], PinState.LOW));
 
        for (;;) {
            Thread.sleep(500);
        }    
    }
}

