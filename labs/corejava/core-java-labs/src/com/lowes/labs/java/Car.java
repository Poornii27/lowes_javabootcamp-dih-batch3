package com.lowes.labs.java;

public class Car {
    //properties / state
    String manufacturer;
    String model;
    String color;
    double weight;
    String transmission;
    String engineType;


    //default constructor

    public Car() {
        System.out.println("Car Default");

    }
    //overloaded constructor
    public Car(String manufacturer,String model,String color,double weight,String transmission) {
        System.out.println("Oveloaded Constructor called");

        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.weight = weight;
        this.transmission =transmission;

    }

    public
    int speed =  0;

    //behaviours / methods
    public void start() {
        System.out.println("Starting the car");
        speed = 0;
    }

    public void stop() {
        speed = 0;
        System.out.println("Stopping the car");

    }
    public void accelarate() {
        System.out.println("Accelarating...");

        speed+= 10;
    }
    public void applyBrake()
    {
        System.out.println("Applying brakes...");
        speed-= 10;
    }
    //
    public String toString()
    {
        return this.manufacturer + " " + this.model + " " + this.color + " " + this.engineType;
    }
}
