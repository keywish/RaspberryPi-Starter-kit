import com.pi4j.wiringpi.Gpio;

public class Button {
	static int BtnPin_1 = 1, LEDpin_1 =	4, BtnPin_2 = 24, LEDpin_2 = 5, BtnPin_3 = 28, LEDpin_3 = 6, BtnPin_4 =	29, LEDpin_4 =	25;
	
	static {
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }
			
		Gpio.pinMode(BtnPin_1, Gpio.INPUT);               
		Gpio.pinMode(LEDpin_1, Gpio.OUTPUT);                  
		Gpio.pinMode(BtnPin_2, Gpio.INPUT);                   
		Gpio.pinMode(LEDpin_2, Gpio.OUTPUT);                  
		Gpio.pinMode(BtnPin_3, Gpio.INPUT);                   
		Gpio.pinMode(LEDpin_3, Gpio.OUTPUT);                  
		Gpio.pinMode(BtnPin_4, Gpio.INPUT);                   
		Gpio.pinMode(LEDpin_4, Gpio.OUTPUT);                  
		Gpio.digitalWrite(LEDpin_1, Gpio.HIGH);               
		Gpio.digitalWrite(LEDpin_2, Gpio.HIGH);               
		Gpio.digitalWrite(LEDpin_3, Gpio.HIGH);               
		Gpio.digitalWrite(LEDpin_4, Gpio.HIGH);		        
    }

	public static void main(String args[]) {
		while (true)
		{
			if(!(Gpio.digitalRead(BtnPin_1) == 1))
			{
				Gpio.delay(20);
				while(!(Gpio.digitalRead(BtnPin_1) == 1))
				{
					Gpio.digitalWrite(LEDpin_1,Gpio.LOW );
				}
				Gpio.digitalWrite(LEDpin_1,Gpio.HIGH );
			}
	
			if(!(Gpio.digitalRead(BtnPin_2) == 1))
			{
				Gpio.delay(20);
				while(!(Gpio.digitalRead(BtnPin_2) == 1))
				{
					Gpio.digitalWrite(LEDpin_2,Gpio.LOW );
				}
				Gpio.digitalWrite(LEDpin_2,Gpio.HIGH );
			}
			
			if(!(Gpio.digitalRead(BtnPin_3) == 1))
			{
				Gpio.delay(20);
				while(!(Gpio.digitalRead(BtnPin_3) == 1))
				{
					Gpio.digitalWrite(LEDpin_3,Gpio.LOW );
				}
				Gpio.digitalWrite(LEDpin_3,Gpio.HIGH );
			}
			
			if(!(Gpio.digitalRead(BtnPin_4) == 1))
			{
				Gpio.delay(20);
				while(!(Gpio.digitalRead(BtnPin_4) == 1))
				{
					Gpio.digitalWrite(LEDpin_4,Gpio.LOW );
				}
				Gpio.digitalWrite(LEDpin_4,Gpio.HIGH );
			}
		}
    }
}
