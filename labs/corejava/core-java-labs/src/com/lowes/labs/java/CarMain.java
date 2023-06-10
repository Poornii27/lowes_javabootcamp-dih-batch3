package com.lowes.labs.java;

public class CarMain {
    public static void main(String[] args) {
        //Declaration & instantiation;
    /*Car swift = new Car(); //Calls the default constructor
        swift.manufacturer = "Maruti";
        swift.model = "Swift VXI";
        swift.color = "Red";
        swift.engineType = "Diesel";*/
        boolean createSwift = true;
        //Car swift = null;
        if (createSwift) {

            Car swift = new Car("Maruti", "Swift VXI", "Red", 1000.0, "Diesel");

            System.out.println(swift);

            swift.start();
            swift.accelarate();
            swift.accelarate();
            System.out.println(swift.speed);
            swift.applyBrake();
            swift.stop();
            System.out.println();

            Car city = new Car(); //Calls the constructor
            city.manufacturer = "Honda";
            city.model = "VXI";
            city.color = "White";
            city.engineType = "Diesel";
            System.out.println(city);


            city.start();
            city.accelarate();
            city.accelarate();
            ;
            city.applyBrake();
            System.out.println(city.speed);

            city.stop();
        }
    }
}
