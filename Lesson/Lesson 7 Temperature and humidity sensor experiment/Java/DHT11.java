import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DHT11 {
    private static final int MAXTIMINGS = 85;
    private final int[] dht11_dat = {0, 0, 0, 0, 0};
    private int pin = 1;

    public double temperature;
    public double humidity;

    public DHT11() {

        if (Gpio.wiringPiSetup() == -1) {
            return;
        }
        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }

    /**
     * Updates the temperature and humidity data from the sensor.
     */
    private boolean readSensor() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;

            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255)
                    break;
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255)
                break;

            //Ignore first 3 transitions.
            if (i >= 4 && i % 2 == 0) {
                dht11_dat[j / 8] <<= 1; //Shove each bit into the storage bytes.

                if (counter > 16)
                    dht11_dat[j / 8] |= 1;

                j++;
            }

        }

        // check we read 40 bits (8bit x 5 ) + verify checksum in the last byte.
        if (j >= 40 && checkParity()) {
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;

            if (h > 100)
                h = dht11_dat[0]; // for DHT11

            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;

            if (c > 125)
                c = dht11_dat[2]; // for DHT11

            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }

            final float f = c * 1.8f + 32;
            this.temperature = f;
            this.humidity = h;
            return true;
        } else
            return false;
    }

    /**
     * Returns the humidity from the sensor.
     *
     * @return Boolean - True of false depending of temperature data array.
     */
    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

    public void updateData() {
        this.temperature = 0.0;
        this.humidity = 0.0;

        boolean result = false;
        int errorCount = 0;

        do {
            try {
                result = this.readSensor();

                if ((this.temperature > 132 || this.temperature < 0) || (this.humidity > 100 || this.humidity < 0)) {
                    System.out.println("Data is incorrect");
                    result = false;
                    errorCount++;
                }

                if (errorCount >= 60) {
                    this.temperature = 0.0;
                    this.humidity = 0.0;
                    return;
                }
            } catch (Exception e) {
            }
        } while (!result);
    }

    /**
     * Returns the temperature from the sensor.
     *
     * @return Double - temperature as a double.
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * Returns the humidity from the sensor.
     *
     * @return Double - humidity as a double.
     */
    public double getHumidity() {
        return this.humidity;
    }
}