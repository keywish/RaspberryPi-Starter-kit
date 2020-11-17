import com.pi4j.wiringpi.Gpio;

public class IR_SEND_OBJ {
		static int irsys = 0xfe, ircode;
		static int PIN = 1;
		
	public static void IR_Send_Start() {
		Gpio.pwmWrite(IR_SEND_OBJ.PIN,22);
		Gpio.delayMicroseconds(4500);
		Gpio.delayMicroseconds(4500);
		Gpio.pwmWrite(IR_SEND_OBJ.PIN,0);
		Gpio.delayMicroseconds(4500);	
	}
	
	public static void Send_Byte(){
		for (int i = 0; i < 8; i++) {
			if((IR_SEND_OBJ.ircode & 0x01) == 1) {
				Gpio.pwmWrite(IR_SEND_OBJ.PIN,22);
				Gpio.delayMicroseconds(560);
				Gpio.pwmWrite(IR_SEND_OBJ.PIN,0);
				Gpio.delayMicroseconds(1690);				
			} else {
				Gpio.pwmWrite(IR_SEND_OBJ.PIN,22);
				Gpio.delayMicroseconds(560);
				Gpio.pwmWrite(IR_SEND_OBJ.PIN,0);
				Gpio.delayMicroseconds(560);
			}
			IR_SEND_OBJ.ircode = IR_SEND_OBJ.ircode >> 1;
		}
	}
	
	public static void IR_Send(int date) {
		Gpio.pinMode(IR_SEND_OBJ.PIN,Gpio.PWM_OUTPUT);
		Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
		Gpio.pwmSetRange(45);              
		Gpio.pwmSetClock(32); 
		IR_SEND_OBJ.ircode = IR_SEND_OBJ.irsys;	
		IR_SEND_OBJ.IR_Send_Start();
		IR_SEND_OBJ.Send_Byte();
		IR_SEND_OBJ.ircode = ~IR_SEND_OBJ.irsys;
		IR_SEND_OBJ.Send_Byte();
		IR_SEND_OBJ.ircode = date;
		IR_SEND_OBJ.Send_Byte();
		IR_SEND_OBJ.ircode = ~date;
		IR_SEND_OBJ.Send_Byte();
		Gpio.pwmWrite(IR_SEND_OBJ.PIN,22);
		Gpio.delayMicroseconds(400);
		Gpio.pwmWrite(IR_SEND_OBJ.PIN,0);
	}
	
	public static void main(String args[]) {

        // setup wiring pi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
        }
		
		while (true){
			IR_SEND_OBJ.IR_Send(0x45);
			Gpio.delay(1000);
		}
	}
}