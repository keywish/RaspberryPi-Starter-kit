public class Temperature_humidity {
    
    public static void main(String[] args){
        DHT11 dht11 = new DHT11();

        for ( ; ;){
            dht11.updateData();
            double Temperature_val = dht11.getTemperature();
            double Humidity_val = dht11.getHumidity();
            System.out.println("Temperature_val: " + Temperature_val);
            System.out.println("Humidity_val: " + Humidity_val);
        }
    }
}