package com.examples.java.accmgr2;

public class AccountManager {

    public static void main(String[] args) {
        AccountService accService = new AccountService();
        // 1. Create set of Accounts (5)
        accService.createAccount();

        // 2. View all Accounts
        accService.viewAccounts();

        // 3. View an Account for given Account ID
        accService.viewAccount(102);

        // 4. Update an Account for given Account ID
        Account accountForUpdate = new Account(102, "Neha", "Deposit", 75000.0, true);
        accService.updateAccount(102, accountForUpdate);
        accService.viewAccounts();


        // 5. Delete an Account for given Account ID
        accService.deleteAccount(102);
        accService.viewAccounts();

    }

}
