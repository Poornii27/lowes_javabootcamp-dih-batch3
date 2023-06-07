package com.lowes.labs.java;

public class CalcDemo {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments");
            return;
        }


        int operand1 = Integer.parseInt(args[0]);
        int operand2 = Integer.parseInt(args[1]);
        String operator = args[2];

        int result = 0;
        switch (operator) {
            case "add":
                result = operand1 + operand2;
                break;
            case "sub":
                result = operand1 - operand2;
                break;
            case "mul":
                result = operand1 * operand2;
                break;
            case "div":
                result = operand1 / operand2;
                break;
            default:
                System.out.println("Invalid operators");
                return;
        }
        System.out.println(result);
    }
}

