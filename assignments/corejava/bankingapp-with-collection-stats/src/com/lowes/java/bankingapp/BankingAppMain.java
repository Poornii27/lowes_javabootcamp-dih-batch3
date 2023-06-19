package com.lowes.java.bankingapp;

import com.lowes.java.bankingapp.model.Account;

import com.lowes.java.bankingapp.service.*;

import com.lowes.java.bankingapp.exception.AccountException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*======================Banking Application=============*/

public class BankingAppMain {
    static String nameOfTheBank = "Bank Of Bengaluru";
    static final AtomicInteger count = new AtomicInteger(0);

    /*===========Different Implementations of the Banking application=========*/

    // static AccountService accService = new AccountServiceArrLstImpl();
    //static AccountService accService = new AccountServiceLnkLstImpl();
    //static AccountService accService = new AccountServiceHashSetImpl();
    //static AccountService accService = new AccountServiceHashMapImpl();
    //static AccountService accService = new AccountServiceTreeSetImpl();
    static AccountService accService = new AccountServiceTreeMapImpl();


    public static void main(String[] args) throws AccountException {
        Scanner in = new Scanner(System.in);

        int choice;
        do {
            displayMenu();
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    /*===========Creating Accounts==========*/
                    System.out.println("How many accounts you would like to create? ");
                    int counter = in.nextInt();
                    System.out.println("Creating Accounts...");
                    for(int i = 0;i <counter;i++) {
                        Account newAcc = captureAccountDetails(false);
                        accService.createAccount(newAcc);
                    }
                    System.out.println("Accounts created successfully, Select an option to proceed");
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
                    Account accountUpdate = captureAccountDetails(true);
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
                    /*==========Printing the Statistics*/
                    System.out.println("***** Printing the Statistics *****");
                    int res1 = accService.balanceAccount();
                    System.out.println("No of accounts with Balance over 1 lac is: "+ res1);
                    System.out.println("***Number of accounts by the Account Type***");
                    accService.getAccountsByType();
                    System.out.println("***Number of accounts by the Account Type: Sorted***");
                    accService.getAccountsByTypeSorted();
                    System.out.println("***Average Balance by the Account Type: ");
                    accService.getAverageBalanceByType();
                    System.out.println("***Account Ids containing the name: Kumar");
                    accService.getAccountIdsContainsName("Kumar");

                case 7:
                    System.out.println("Thank you for visiting "+ nameOfTheBank +". Have a nice day!");
                    break;

            }
        } while (choice != 7);
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
        System.out.println("1. Create Accounts");
        System.out.println("2. List all Accounts");
        System.out.println("3. View an Account");
        System.out.println("4. Update an Account");
        System.out.println("5. Delete an Account");
        System.out.println("6. Print Statistics");
        System.out.println("7. Exit");
    }

    /*================Stores the details given by the user===============*/

    public static Account captureAccountDetails(boolean isUpdate) {
        Account account = new Account();
        Scanner in = new Scanner(System.in);
        int id =0;
        try {
            if(!isUpdate) {
                //id = in.nextInt(); in.nextLine();
                id = count.incrementAndGet();
                System.out.println("The Account ID for the new account is: " + id);
            }
            else {
                System.out.println("Enter the Account ID: ");
                in = new Scanner(System.in);
                id = in.nextInt();
                in.nextLine();
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter only numbers!");
            System.out.println("Enter the Account ID: ");
            in = new Scanner(System.in);
            id = in.nextInt();
            in.nextLine();
        }
        account.setId(id);

        System.out.println("What is the name of the Account Holder? ");
        account.setName(in.nextLine());

        System.out.println("What is the Account Type? ");
        account.setType(in.nextLine());

        System.out.println("What is the Account Balance? ");
        double balance = 0.0;
        try { balance = in.nextDouble();}
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter only numbers; This field is decimal friendly.!");
            System.out.println("What is the Account Balance? ");
            in = new Scanner(System.in);
            balance = in.nextDouble();
        }
        account.setBalance(balance);

        System.out.println("Is the Account Active? ");
        boolean isActive = true;
        String val= in.next();

        boolean b = val.equalsIgnoreCase("n") || val.equalsIgnoreCase("no") || val.equalsIgnoreCase("false");
        boolean b1 = val.equalsIgnoreCase("y") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true");

        try {
            if (b1) {
                isActive = true;
            } else if (b) {
                isActive = false;
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter yes/no or true/false");
            System.out.println("Is the Account Active? ");
            in = new Scanner(System.in);
            if(b1) {
                isActive = true;
            }
            else if (b) {
                isActive = false;
            }
        }
        account.setActive(isActive);

        return account;
    }
}

