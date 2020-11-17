import com.pi4j.wiringpi.Gpio;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

public class DS1302 {
    int DSIO = 1, RST = 24, SCLK = 28;
    int READ_RTC_ADDR[]= {0x81, 0x83, 0x85, 0x87, 0x89, 0x8b, 0x8d};
    int WRITE_RTC_ADDR[] = {0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c};
    int TIME[] = {0, 0, 0x12, 0x07, 0x05, 0x06, 0x16};
    int data[] = {0, 0, 0, 0, 0, 0, 0, 0};
    String buf;

    void Ds1302Init() {
        Gpio.wiringPiSetup();
	    Gpio.pinMode(DSIO, Gpio.OUTPUT);
        Gpio.pinMode(RST, Gpio.OUTPUT);
        Gpio.pinMode(SCLK, Gpio.OUTPUT);
        Ds1302Write(0x8E, 0x00);
        for (int i = 0; i < 7; i++) {
            Ds1302Write(WRITE_RTC_ADDR[i], TIME[i]);
        }
        Ds1302Write(0x8E, 0x80);
    }

    void Ds1302Write(int addr, int dat){
        Gpio.digitalWrite(RST, Gpio.LOW);
        Gpio.digitalWrite(SCLK, Gpio.LOW);
        Gpio.digitalWrite(RST, Gpio.HIGH);
        for (int i = 0; i < 8; i++) {
            Gpio.digitalWrite(DSIO, (addr & 0x01));
            addr= addr >> 1;
            Gpio.digitalWrite(SCLK, Gpio.HIGH);
            Gpio.digitalWrite(SCLK, Gpio.LOW);
        }
        for (int i = 0; i < 8; i++) {
            Gpio.digitalWrite(DSIO, (dat & 0x01));
            dat= dat >> 1;
            Gpio.digitalWrite(SCLK, Gpio.HIGH);
            Gpio.digitalWrite(SCLK, Gpio.LOW);
        }
        Gpio.digitalWrite(RST, Gpio.LOW);
    }

    int Ds1302Read(int addr){
        int dat = 0;
        Gpio.digitalWrite(RST, Gpio.LOW);
        Gpio.digitalWrite(SCLK, Gpio.LOW);
        Gpio.digitalWrite(RST, Gpio.HIGH);
        for (int i = 0; i < 8; i++) {
            Gpio.digitalWrite(DSIO, (addr & 0x01));
            addr= addr >> 1;
            Gpio.digitalWrite(SCLK, Gpio.HIGH);
            Gpio.digitalWrite(SCLK, Gpio.LOW);
        }
        Gpio.pinMode(DSIO,Gpio.INPUT);
        for (int i = 0; i < 8; i++) {
            int tt = Gpio.digitalRead(DSIO);
            dat = dat | (Gpio.digitalRead(DSIO) << i);
            Gpio.digitalWrite(SCLK, Gpio.HIGH);
            Gpio.digitalWrite(SCLK, Gpio.LOW);
        }
        Gpio.pinMode(DSIO,Gpio.OUTPUT);
        Gpio.digitalWrite(RST, Gpio.LOW);
        Gpio.digitalWrite(SCLK, Gpio.HIGH);
        Gpio.digitalWrite(DSIO, Gpio.LOW);
        Gpio.digitalWrite(DSIO, Gpio.HIGH);

        return dat;
    }

    void Ds1302ReadTime() {
        for (int i = 0; i < 7; i++) {
            TIME[i] = Ds1302Read(READ_RTC_ADDR[i]);
        }
        data[0] = (TIME[0] / 16 * 10) + (TIME[0] & 0x0f);
        data[1] = TIME[1] / 16 * 10 + TIME[1] & 0x0f;
        data[2] = TIME[2] / 16 * 10 + TIME[2] & 0x0f;
    }

    void get_time() {
        String s0 = String.valueOf(data[0]);
        String s1 = String.valueOf(data[1]);
        String s2 = String.valueOf(data[2]);
        StringBuffer buff = new StringBuffer();
        buff.append(s2);
        buff.append(":");
        buff.append(s1);
        buff.append(":");
        buff.append(s0);
        buf = buff.toString();
    }


    public static void main(String[] args) {
        DS1302 ds1302 = new DS1302();
        ds1302.Ds1302Init();
        I2CDevice _device = null;
        I2CLCD _lcd = null;
        try {
            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
            _device = bus.getDevice(0x27);
            _lcd = new I2CLCD(_device);
            _lcd.init();
            _lcd.backlight(true);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        while (true) {
            ds1302.Ds1302ReadTime();
            ds1302.get_time();
            _lcd.display_string_pos(ds1302.buf, 1, 2);
        }
    }
}