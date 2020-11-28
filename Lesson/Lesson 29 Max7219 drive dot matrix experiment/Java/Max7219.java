import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioUtil;


public class Max7219 {
	static int DecodeMode = 0x09; //译码模式寄存器
	static int Intensity = 0x0a; //亮度寄存器
	static int ScanLimit = 0x0b; //扫描位数寄存器
	static int ShutDown = 0x0c; //低功耗模式寄存器
	static int DisplayTest = 0x0f; //显示测试寄存器
	static int ShutdownMode = 0x00; //低功耗方式
	static int NormalOperation = 0x01; //正常操作方式
	static int ScanDigit = 0x07; //扫描位数设置, 显示8位数码管
	static int DecodeDigit = 0x00; //译码设置, 8位均为非译码
	static int IntensityGrade = 0x0a; //亮度级别设置
	static int TestMode = 0x01; //显示测试模式
	static int TextEnd = 0x00; //显示测试结束, 恢复正常工作模式
	static int DIN = 4, CS = 5, CLK = 3;
	static int buffer[] =
							{
								0x78,0xFC,0xFE,0x7F,0x7F,0xFE,0xFC,0x78,
							};

	public static void init(){
		Gpio.pinMode(DIN, Gpio.OUTPUT);
		Gpio.pinMode(CS, Gpio.OUTPUT);
		Gpio.pinMode(CLK, Gpio.OUTPUT);
		writeWord(DecodeMode, 0x00);
		writeWord(Intensity, 0x08);
		writeWord(ScanLimit, 0x07);
		writeWord(ShutDown, 0x01);
		writeWord(DisplayTest, 0x00);
	}

	public static void send_data(int ch){
		int i, tmp;
		for(i = 0; i < 8; i++){
			tmp = ch & 0x80;
			if(tmp == 0x80)
				Gpio.digitalWrite(DIN, Gpio.HIGH);
			else
				Gpio.digitalWrite(DIN, Gpio.LOW);
			ch = ch << 1;
			Gpio.digitalWrite(CLK, Gpio.HIGH);
			Gpio.digitalWrite(CLK, Gpio.LOW);
		}
	}

	public static void writeWord(int addr, int num){
		Gpio.digitalWrite(CS, Gpio.HIGH);
		Gpio.digitalWrite(CS, Gpio.LOW);
		Gpio.digitalWrite(CLK, Gpio.LOW);
		send_data(addr);
		send_data(num);
		Gpio.digitalWrite(CS, Gpio.HIGH);
	}

	public static void write(int [] buff){
		int i;
		for(i = 0; i < 8; i++){
			writeWord(i + 1, buff[i]);
			System.out.println(buff[i]);
			Gpio.delay(20);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// setup wiring pi
		if (Gpio.wiringPiSetup() == -1) {
			System.out.println(" ==>> GPIO SETUP FAILED");
			return;
		}
		init();
		for (;;) {
			write(buffer);
		}
	}
}
