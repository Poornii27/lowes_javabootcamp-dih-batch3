package com.lowes.java.bankingapp;

import com.lowes.java.bankingapp.model.Account;

import com.lowes.java.bankingapp.service.AccountService;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.service.AccountServiceArrLstImpl;
import com.lowes.java.bankingapp.service.AccountServiceLnkLstImpl;

import java.util.*;


public class BankingAppMain {

    boolean isLinkedList = false;
    //static AccountService accService = new AccountServiceArrLstImpl();
    static AccountService accService = new AccountServiceLnkLstImpl();
    public static void main(String[] args) throws AccountException {
        Scanner in = new Scanner(System.in);


        int choice;
        do {
            displaymenu();
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    /*===========Creating Accounts==========*/
                    System.out.println("Enter the number of accounts you would like to create: ");
                    int counter = in.nextInt();
                    System.out.println("Creating Accounts...");
                    for(int i = 0;i <counter;i++) {
                        Account newacc = captureAccountDetails();
                        accService.createAccount(newacc);
                    }
                    System.out.println("Accounts created successfully, Enter an option to proceed");
                    break;

                case 2:
                    /*============List all Accounts============*/

                    System.out.println("Fetching Accounts....");
                    accService.getAccounts();
                    break;

                case 3:
                    /*============View Account=================*/
                    System.out.println("View details of the given Account id...");
                    System.out.println("Enter Account ID: ");
                    int id2 = in.nextInt();
                    try {
                        accService.getAccount(id2);
                    }
                    catch(AccountException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    /*============Update Account===============*/
                        System.out.println("Update an Account for a given Account id...");
                        Account accountforUpdate = captureAccountDetails();
                        try {
                            accService.updateAccount(accountforUpdate.getId(),accountforUpdate);
                        }
                        catch(AccountException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                case 5:
                    /*============Deleting Account==============*/
                    System.out.println("Delete an Account for given Account ID.....");
                    System.out.println("Enter Account ID to be deleted: ");

                    int id5 = in.nextInt();
                    try {
                        accService.deleteAccount(id5);
                    }catch(AccountException e)
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
        String type = "Array List";
        if(accService.getClass().getName().endsWith("AccountServiceLnkLstImpl")) {
            type = "Linked List";
        }
        System.out.println("<----------Banking Application using "+type+ "--------->");
        System.out.println("Menu:");
        System.out.println("1.Create Accounts");
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
        int id = 0;
        try {
            id = in.nextInt();
        } catch (InputMismatchException e) {
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

