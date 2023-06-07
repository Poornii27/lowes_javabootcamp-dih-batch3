package com.lowes.labs.java;

public class Calculator {

    // global variable
//    static int a = 20;
//    static int b = 10;
    // global constant
//    static final int a = 20;
//    static final int b = 10;
    public static void main(String[] args) {
        // local variable
//        int a = 20;
//        int b = 10;
        // local constant
//        final int a = 20;
//        final int b = 10;
        if (args.length < 2) {
            System.out.println("Invalid Arguments");
            return;
        }

        // command line args
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        // Addition
        int out1 = a + b;

        // Subtraction
        int out2 = a - b;

        // Multiplication
        int out3 = a * b;

        // Division
        int out4 = a / b;

        System.out.println(out1);
        System.out.println(out2);
        System.out.println(out3);
        System.out.println(out4);
    }
}