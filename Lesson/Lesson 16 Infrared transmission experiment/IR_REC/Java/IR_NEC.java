
import com.pi4j.wiringpi.Gpio;

public class IR_NEC {
        static int PIN = 24;
	    static int ERROR = 0xfe, key;
		static long timeRisingEdge, timeFallingEdge, timeRising, timeFalling_0, timeFalling_1;
		static long timeSpan_val = 0;
		static long[] time_span = new long[2];
	
	static {
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }

        Gpio.pinMode(PIN, Gpio.INPUT);       
    }
	
	public static boolean  IRStart() {
		while(!(Gpio.digitalRead(PIN) == 0)); 
		timeFalling_0 = gettimeofday();
		while(!(Gpio.digitalRead(PIN) == 1));
		timeRising = gettimeofday();
		while(!(Gpio.digitalRead(PIN) == 0)); 
		timeFalling_1 = gettimeofday();	
		time_span[0] = timeRising - timeFalling_0;
		time_span[1] = timeFalling_1 - timeRising;

//		System.out.println("start_time " + time_span[0] + "," +time_span[1]);

		if (time_span[0] > 8500 && time_span[0] < 9500 && time_span[1] >= 4000 && time_span[1] <= 5000)
		{	
//			System.out.println("start singe*************");
            return true;
        }
		else	{
			return false;
        }
	}
	
	public static long gettimeofday() {
//		return System.currentTimeMillis() ;// +System.nanoTime() / 1000;
		return System.nanoTime() / 1000;

	}

	public static int GetByte() {
		int byte_val = 0; 
		for (int i = 0; i < 8; i++) {
			while(!(Gpio.digitalRead(PIN) == 1));
			timeRisingEdge = gettimeofday();
			while(!(Gpio.digitalRead(PIN) == 0));
			timeFallingEdge = gettimeofday();
			timeSpan_val = timeFallingEdge - timeRisingEdge;
//			System.out.print("start byte  ");
//          System.out.println(timeSpan_val);

			if (timeSpan_val > 1500 && timeSpan_val < 1800)
				byte_val |= 1 << i;

		}
//	System.out.printf("byte_val: %x  \n", byte_val);

		return byte_val;
	}
	
	public static int GetKey() {
		int[] byte_val = new int[4];
		if (IRStart() == false) {
			Gpio.delay(108);
			return ERROR;
		} else {
			for (int i = 0; i < 4; i++) {
				byte_val[i] = GetByte();
//				System.out.printf("byte_val[%d]: %x \n",i, byte_val[i]);

			}
			if ((byte_val[0] + byte_val[1] == 0xff) && (byte_val[2] + byte_val[3] == 0xff)) {
				return byte_val[2];
			} else {
				    return ERROR;
			  }	
		  }
	 }
	
	public static void main(String args[]) {
        int rec_val;
		IR_NEC ir_nec = new IR_NEC();
        for ( ; ;) {
            rec_val = ir_nec.GetKey();
			if (rec_val != ERROR) {
				System.out.printf("key: %x \n",rec_val);
			}
        }
    }
}
