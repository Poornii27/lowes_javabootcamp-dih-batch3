package com.lowes.assignments.java;

import java.util.Arrays;

public class PrimeNumber {
    public static void main(String[] args) {
        if (args.length == 0)
        {
            System.out.println("No number provided, Please enter a number");
            return;
        }

        int num = Integer.parseInt(args[0]);

        System.out.println("Number entered is: \t" + num);
        boolean isPrime = true;

        if (num <= 1)
        {
            isPrime = false;
        }
        else
        {
            for (int i = 2;i <= num/2;i++)
            {
                if(num%i == 0)
                {
                    isPrime = false;
                    break;
                }
            }
        }
        if (isPrime)
        {
            System.out.println("Its a prime number");
        }
        else{
            System.out.println("Its not a prime number");

        }


    }

}
