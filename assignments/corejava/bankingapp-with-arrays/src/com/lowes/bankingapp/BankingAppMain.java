package com.lowes.bankingapp;

import com.lowes.bankingapp.model.Account;

import com.lowes.bankingapp.service.AccountService;

import com.lowes.bankingapp.service.AccountServiceArrImpl;
import com.lowes.bankingapp.exception.AccountException;

import java.util.InputMismatchException;
import java.util.Scanner;


public class BankingAppMain {


    public static void main(String[] args) throws com.lowes.bankingapp.exception.AccountException, AccountException {

        AccountService accService = new AccountServiceArrImpl();
        Account[] accarr = new Account[2];


        Scanner in = new Scanner(System.in);


        int choice;

        do {
            displaymenu();
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Creating an Account....");

                    for(int i = 0;i <accarr.length;i++) {
                        Account newacc = captureAccountDetails();
                       accarr[i] = newacc;
                        accService.createAccount(newacc);
                    }
                    System.out.println("Accounts created successfully, Enter an option to proceed");
                    break;

                case 2:
                    System.out.println("Listing all Accounts....");

                    accService.getAccounts();

                    for (Account account : accarr) {
                        if ((account != null)) {
                            System.out.println(account);
                        }
                    }
                    break;
                case 3:
                    System.out.println("View details of the given Account id...");
                    System.out.println("Enter Account ID: ");

                    int id2 = in.nextInt();
                    Account arr = accService.getAccount(id2);
                    try {
                        accService.getAccount(id2);
                    } catch (AccountException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Fetching Account " +id2);
                    if ((arr != null)) {
                        System.out.println(arr);
                    }

                    break;

                case 4:
                    System.out.println("Update an Account for a given Account id...");

                    int id3 = in.nextInt();

                    Account accountforUpdate = captureAccountDetails();

                    try {
                        accService.updateAccount(id3,accountforUpdate);
                        System.out.println(accService.getAccount(id3));

                    } catch(AccountException e)
                    {
                        System.out.println(e.getMessage());
                    };
                    break;

                case 5:
                    System.out.println("Delete an Account for given Account ID.....");
                    System.out.println("Enter Account ID to be deleted: ");

                    int id5 = in.nextInt();

                    try{
                        accService.deleteAccount(id5);
                     }catch (AccountException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Exiting....");
                    break;
            }
        } while (choice != 6);
    }

    public static  void displaymenu() {
        System.out.println("<----------Banking Application using Arrays--------->");
        System.out.println("Menu:");
        System.out.println("1.Create an Account");
        System.out.println("2.List all Accounts");
        System.out.println("3.View Account");
        System.out.println("4.Update Account");
        System.out.println("5.Delete Account");
        System.out.println("6.Exit");
    }

    public static Account captureAccountDetails() {
        Account account = new Account();
        Scanner in = new Scanner(System.in);


        System.out.println("Enter Account ID: ");
        int id =0;
        try {
            id = in.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Invalid input,please enter only numbers");
            System.out.println("Enter Account ID: ");
            in = new Scanner(System.in);
            id = in.nextInt();

        }

        account.setId(id);
        System.out.println("Enter Account Name: ");
        account.setName(in.next());
        System.out.println("Enter Account Type: ");
        account.setType(in.next());
        System.out.println("Enter Account Balance: ");
        account.setBalance(in.nextDouble());
        System.out.println("Is Account Active: ");
        account.setActive(in.nextBoolean());

        return account;

    }

}
