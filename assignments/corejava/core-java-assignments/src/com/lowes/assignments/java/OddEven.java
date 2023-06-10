package com.lowes.assignments.java;

public class OddEven {
    public static void main(String[] args) {
        int a = 0;
        if(args.length > 0)
        {
            a = Integer.parseInt(args[0]);
        }

        System.out.println("The number entered is :\t" + a);
        if (a % 2 == 0) {
            System.out.println("\nIts even number");
        } else {
            System.out.println("\nIts an odd number");

        }
    }
}





