#include "key4x4.h"

uint8_t pin_val[8] = {3, 4, 5, 6, 1, 24, 28, 29};
char key[4][4] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

void getval()
{
		for (int i=0; i<4; i++)
		{	
			out_set(i);	
			for (int j=0; j<4; j++)
			{
				if (digitalRead(pin_val[j+4]))
				{
					delay(100);
					if (digitalRead(pin_val[j+4]))
					{
						printf("key: %c\n",key[i][j]);
					}
				}
			}
		}
}
	
void out_set(int n)
{
	pinMode(pin_val[0],OUTPUT);
	pinMode(pin_val[1],OUTPUT);
	pinMode(pin_val[2],OUTPUT);
	pinMode(pin_val[3],OUTPUT);
	pinMode(pin_val[4],INPUT);
	pinMode(pin_val[5],INPUT);
	pinMode(pin_val[6],INPUT);
	pinMode(pin_val[7],INPUT);
	switch(n){
			case 0:
				digitalWrite(pin_val[0],HIGH);
				digitalWrite(pin_val[1],LOW);
				digitalWrite(pin_val[2],LOW);
				digitalWrite(pin_val[3],LOW);
				break;
			case 1:
				digitalWrite(pin_val[0],LOW);
				digitalWrite(pin_val[1],HIGH);
				digitalWrite(pin_val[2],LOW);
				digitalWrite(pin_val[3],LOW);
				break;
			case 2:
				digitalWrite(pin_val[0],LOW);
				digitalWrite(pin_val[1],LOW);
				digitalWrite(pin_val[2],HIGH);
				digitalWrite(pin_val[3],LOW);
				break;
			case 3:
				digitalWrite(pin_val[0],LOW);
				digitalWrite(pin_val[1],LOW);
				digitalWrite(pin_val[2],LOW);
				digitalWrite(pin_val[3],HIGH);
				break;
			default:
				break;
		}
}


