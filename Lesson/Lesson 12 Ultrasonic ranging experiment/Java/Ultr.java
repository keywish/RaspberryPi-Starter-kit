import com.pi4j.wiringpi.Gpio;
import com.pi4j.io.gpio.PinEdge;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;

public class Ultr {
    static  double distance = 0.0, time_1 = 0, time_2 = 0;

    public static Long getmicTime() {
        Long cutime = System.currentTimeMillis() * 1000; // 微秒
        Long nanoTime = System.nanoTime(); // 纳秒
        return cutime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }

    public static void clean_date() {
        Ultr.time_1 = 0;
        Ultr.time_2 = 0;
    }
    
    public static void main(String args[]) throws InterruptedException {
        
        // create and add GPIO listener 
        GpioInterrupt.addListener(new GpioInterruptListener() {
            @Override
            public void pinStateChange(GpioInterruptEvent event) {                
                if(event.getPin() == 29) {
                    if(Gpio.digitalRead(29) == 1) {
                        Ultr.clean_date();
                        Ultr.time_1 = Ultr.getmicTime();
                    } else {
                        Ultr.time_2 = Ultr.getmicTime();
                        Ultr.distance = (Ultr.time_2 - Ultr.time_1) / 1000000 / 2 * 340 * 100;
                        Ultr.clean_date();
                        System.out.println(Math.abs(Ultr.distance));
                   }
                }
            }
        });
        
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }
        
        // set the edge state on the pins we will be listening for
        GpioUtil.setEdgeDetection(29, GpioUtil.EDGE_BOTH);
        
        // configure GPIO 29 as an INPUT pin;GPIO_028设置为输出 enable it for callbacks
        Gpio.pinMode(28, Gpio.OUTPUT);
        Gpio.pinMode(29, Gpio.INPUT);

        //Gpio.pullUpDnControl(29, Gpio.PUD_UP);        
       GpioInterrupt.enablePinStateChangeCallback(29);
        
        // continuously loop to prevent program from exiting
        for (;;) {
            //GPIO_29 设置为高电平。
            Gpio.pinMode(28, Gpio.OUTPUT);
            Gpio.digitalWrite(28, 0);
            Gpio.delayMicroseconds(2);
            Gpio.digitalWrite(28, 1);
            Gpio.delayMicroseconds(10);
            Gpio.digitalWrite(28, 0); 
            Thread.sleep(1000);
        }
    }
}
