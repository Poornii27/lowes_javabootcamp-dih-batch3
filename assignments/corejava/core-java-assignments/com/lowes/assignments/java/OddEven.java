package com.lowes.assignments.java;

import javax.lang.model.type.NullType;

public class OddEven {
    public static void main(String[] args) {
        int a = 0;
        if(args.length > 0)
        { a = Integer.parseInt(args[0]);}
        else {System.out.println("Please enter a number");
        return;}

       if (args == null) {
            System.out.println("Invalid arguments");
            return;
        }

        if (a % 2 == 0) {
            System.out.println("Its even number");
        } else {
            System.out.println("Its an odd number");

        }
    }
}

