package com.lowes.java.bankingapp;

import com.lowes.java.bankingapp.model.Account;

import com.lowes.java.bankingapp.service.*;

import com.lowes.java.bankingapp.exception.AccountException;

import java.util.*;

/*======================Banking Application=============*/

public class BankingAppMain {
    static String nameOfTheBank = "Bank Of Bengaluru";

    /*===========Different Implementations of the Banking application=========*/

   // static AccountService accService = new AccountServiceArrLstImpl();
    //static AccountService accService = new AccountServiceLnkLstImpl();
    //static AccountService accService = new AccountServiceHashSetImpl();
    //static AccountService accService = new AccountServiceHashMapImpl();
    static AccountService accService = new AccountServiceTreeSetImpl();
    //static AccountService accService = new AccountServiceTreeMapImpl();


    public static void main(String[] args) throws AccountException {
        Scanner in = new Scanner(System.in);

        int choice;
        do {
            displayMenu();
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    /*===========Creating Accounts==========*/
                    System.out.println("Enter the number of accounts you would like to create: ");
                    int counter = in.nextInt();
                    System.out.println("Creating Accounts...");
                    for(int i = 0;i <counter;i++) {
                        Account newAcc = captureAccountDetails();
                        accService.createAccount(newAcc);
                    }
                    System.out.println("Accounts created successfully, Enter an option to proceed");
                    break;

                case 2:
                    /*============List all Accounts============*/
                    System.out.println("Fetching Accounts...");
                    Collection<Account> result = accService.getAccounts();
                    Iterator it = result.iterator();
                    boolean isFound = false;
                    while (it.hasNext()) {
                        System.out.println(it.next());
                        isFound = true;
                    }
                    if (!it.hasNext() && !isFound){
                        System.out.println("No accounts found!");
                    }
                    break;

                case 3:
                    /*============View Account=================*/
                    System.out.println("View details of the given Account id...");
                    System.out.println("Enter the Account ID: ");
                    int id2 = in.nextInt();
                    try {
                        Account res = accService.getAccount(id2);
                        System.out.println(res);
                    }
                    catch(AccountException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    /*============Update Account===============*/
                        System.out.println("Update an Account for a given Account id...");
                        Account accountUpdate = captureAccountDetails();
                        try {
                            accService.updateAccount(accountUpdate.getId(),accountUpdate);
                            System.out.println("Account Updated");
                        }
                        catch(AccountException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                case 5:
                    /*============Deleting Account==============*/
                    System.out.println("Delete an Account for given Account ID...");
                    System.out.println("Enter the Account ID to be deleted: ");

                    int id5 = in.nextInt();
                    try {
                        accService.deleteAccount(id5);
                        System.out.println("Account Deleted");
                    }
                    catch(ConcurrentModificationException e) {
                        System.out.println(e.getMessage());
                    }
                    catch(AccountException ex) {
                        System.out.println(ex.getMessage());}
                    break;

                case 6:
                    System.out.println("Thank you for visiting "+ nameOfTheBank +". Have a nice day!");
                    break;

            }
        } while (choice != 6);
    }

    /*=================Menu displaying different options ===================*/
    public static  void displayMenu() {
        String type = ": Banking Application ";
        if(accService.getClass().getName().endsWith("AccountServiceArrLstImpl")) {type += "using Array List";}
        else if(accService.getClass().getName().endsWith("AccountServiceLnkLstImpl")) {type += "using Linked List";}
        else if (accService.getClass().getName().endsWith("AccountServiceHashMapImpl")) {type += "using Hash Map";}
        else if (accService.getClass().getName().endsWith("AccountServiceHashSetImpl")) {type += "using Hash Set";}
        else if (accService.getClass().getName().endsWith("AccountServiceTreeMapImpl")) {type += "using Tree Map";}
        else if (accService.getClass().getName().endsWith("AccountServiceTreeSetImpl")) {type += "using Tree Set";}
        System.out.println("<----------"+nameOfTheBank+""+type+ "--------->");
        System.out.println("Actions:");
        System.out.println("1.Create Accounts");
        System.out.println("2.List all Accounts");
        System.out.println("3.View an Account");
        System.out.println("4.Update an Account");
        System.out.println("5.Delete an Account");
        System.out.println("6.Exit");
    }

    /*================Stores the details given by the user===============*/

    public static Account captureAccountDetails() {
        Account account = new Account();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Account ID: ");
        int id = 0;
        try { id = in.nextInt(); }
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter only numbers!");
            System.out.println("Enter the Account ID: ");
            in = new Scanner(System.in);
            id = in.nextInt();
        }
        account.setId(id);

        System.out.println("Enter the Account Name: ");
        account.setName(in.next());

        System.out.println("Enter the Account Type: ");
        account.setType(in.next());

        System.out.println("Enter the Account Balance: ");
        double balance = 0.0;
        try { balance = in.nextDouble();}
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter only numbers; This field is decimal friendly.!");
            System.out.println("Enter the Account Balance ");
            in = new Scanner(System.in);
            balance = in.nextDouble();
        }
        account.setBalance(balance);

        System.out.println("Is the  Account Active? ");
        boolean isActive = true;
        try {isActive = in.nextBoolean();}
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter true/false");
            System.out.println("Is the Account Active? ");
            in = new Scanner(System.in);
            isActive = in.nextBoolean();
        }
        account.setActive(isActive);

        return account;
    }
}

