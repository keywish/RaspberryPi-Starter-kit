import ctypes  
import time

so = ctypes.cdll.LoadLibrary   
lib = so("./libpycallclass.so")   

while True:
    lib.IR_Send(0x45)
    time.sleep(1)
