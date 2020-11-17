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

public class Button {
    
    public static void main(String[] args) throws InterruptedException {

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, 
                                                  PinPullResistance.PULL_DOWN);
        
        // setup gpio pins #01 as an output pins and make sure they are all LOW at startup
        GpioPinDigitalOutput myLed[] = { 
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED #1", PinState.LOW),
          };
        
        // create a gpio control trigger on the input pin ; when the input goes LOW, also set gpio pin #01 to LOW
        myButton.addTrigger(new GpioSetStateTrigger(PinState.LOW, myLed[0], PinState.LOW));

        // create a gpio control trigger on the input pin ; when the input goes HIGH, also set gpio pin #01 to HIGH
        myButton.addTrigger(new GpioSetStateTrigger(PinState.HIGH, myLed[0], PinState.HIGH));

        // keep program running until user aborts (CTRL-C)
        for (;;) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
    }
}
