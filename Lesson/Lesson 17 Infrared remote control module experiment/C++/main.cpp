#include "IR_REC.h"

int main()
{
	int key;
	if(wiringPiSetup() == -1){  
        printf("setup wiringPi failed !");  
        return 1;   
    }
	while(1){
		if (GetKey() != ERROR) {
			printf("Change_Map %s \n",Change_Map().c_str());
			//cout << VAL.keyname << endl;
		}
	}
}