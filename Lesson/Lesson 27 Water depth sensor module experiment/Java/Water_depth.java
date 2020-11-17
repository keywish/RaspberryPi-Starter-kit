import com.pi4j.wiringpi.I2C;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.lang.Math;

public class Water_depth {
    static int value = 0, fd;
    static float voltage;
    static {
        Gpio.wiringPiSetup();
        fd = I2C.wiringPiI2CSetup(0x04);
    }

    public static void main(String[] args){
        I2CDevice _device = null;
        I2CLCD _lcd = null;

        for ( ; ;) {
            value =  I2C.wiringPiI2CReadReg16(fd, 0x10);
            voltage = (float)(Math.round((1 - (float)value / 4095) * 100 * 100)) / 100;
            String s = String.valueOf(voltage);
            try {
                I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
                _device = bus.getDevice(0x27);
                _lcd = new I2CLCD(_device);
                _lcd.init();
                _lcd.backlight(true);
                _lcd.display_string_pos("deep: " + s +"cm", 1, 2);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }
}