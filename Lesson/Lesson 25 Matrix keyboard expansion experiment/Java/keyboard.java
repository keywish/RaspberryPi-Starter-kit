import com.pi4j.wiringpi.Gpio;

public class keyboard {
    static int[] pin_val = new int[] {3, 4, 5, 6, 1, 24, 28, 29};
	static int[][] key = new int[][] {	{'1','2','3','A'},
										{'4','5','6','B'},
										{'7','8','9','C'},
										{'*','0','#','D'}	};
    static {
        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> Gpio SETUP FAILED");
        }
    }

	public static void out_set(int n) {
		for(int i = 0; i < 4; i++) {
			Gpio.pinMode(pin_val[i],Gpio.OUTPUT);
			Gpio.pinMode(pin_val[i+4], Gpio.INPUT);
		}
		if(n == 0) {
			Gpio.digitalWrite(pin_val[0],Gpio.HIGH);
			Gpio.digitalWrite(pin_val[1],Gpio.LOW);
			Gpio.digitalWrite(pin_val[2],Gpio.LOW);
			Gpio.digitalWrite(pin_val[3],Gpio.LOW);
		}
		if(n == 1) {
			Gpio.digitalWrite(pin_val[0],Gpio.LOW);
			Gpio.digitalWrite(pin_val[1],Gpio.HIGH);
			Gpio.digitalWrite(pin_val[2],Gpio.LOW);
			Gpio.digitalWrite(pin_val[3],Gpio.LOW);
		}			
		if(n == 2) {
			Gpio.digitalWrite(pin_val[0],Gpio.LOW);
			Gpio.digitalWrite(pin_val[1],Gpio.LOW);
			Gpio.digitalWrite(pin_val[2],Gpio.HIGH);
			Gpio.digitalWrite(pin_val[3],Gpio.LOW);
		}
		if(n == 3) {
			Gpio.digitalWrite(pin_val[0],Gpio.LOW);
			Gpio.digitalWrite(pin_val[1],Gpio.LOW);
			Gpio.digitalWrite(pin_val[2],Gpio.LOW);
			Gpio.digitalWrite(pin_val[3],Gpio.HIGH);
		}
	}

    public static void main(String args[]) {

        for ( ; ;) { 
			for(int i = 0; i < 4; i++) {
				out_set(i);
				for(int m = 0; m < 4; m++) {
					if(Gpio.digitalRead(pin_val[m+4]) == 1) {
						Gpio.delay(100);
						if(Gpio.digitalRead(pin_val[m+4]) == 1) {
							System.out.printf("KEY :%c\n",key[i][m]);
						}
					}
				}
			}
		}
	}
}