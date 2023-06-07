package com.lowes.assignments.java;

public class Sorting {
    public static void main(String[] args) {

        // Outer loop
        if (args.length > 0)
        {
            int arr[] = new int[args.length];
            for (int a=0;a<args.length;a++)
            {
                arr[a] = Integer.parseInt(args[a]);
            }

            for (int i = 0; i < arr.length; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    int temp = 0;
                    if (arr[j] < arr[i]) {
                        temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }
                System.out.print(arr[i] + " ");
            }
    }

    }
}
