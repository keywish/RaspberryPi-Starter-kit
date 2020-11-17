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

public class Hall {
    
    public static void main(String[] args) throws InterruptedException {

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput Sensor  = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, 
                                                  PinPullResistance.PULL_UP);

        // setup gpio pins #04, #05, #06 as an output pins and make sure they are all LOW at startup
        GpioPinDigitalOutput TAB[] = { 
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Buzzer", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "led", PinState.LOW), 
        };

        // create a gpio pulse trigger on the input pin; when the input goes LOW, also pulse gpio pin  to the HIGH state for 1 second
        Sensor.addTrigger(new GpioPulseStateTrigger(PinState.LOW, TAB[0], 1000));
        Sensor.addTrigger(new GpioPulseStateTrigger(PinState.LOW, TAB[1], 1000));
 
        for (;;) {
            Thread.sleep(500);
        }    
    }
}
