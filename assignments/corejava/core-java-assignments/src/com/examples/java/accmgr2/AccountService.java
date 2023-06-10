package com.examples.java.accmgr2;

public class AccountService {
    private Account[] accounts = new Account[5];

    public void createAccount() {
        //int id, String name, String type, double balance, boolean active
        accounts[0] = new Account(100, "Anu", "Savings", 25000.0, true);
        accounts[1] = new Account(101, "John", "Loan", 250000.0, true);
        accounts[2] = new Account(102, "Neha", "Deposit", 50000.0, true);
        accounts[3] = new Account(103, "Kumar", "Savings", 10000.0, true);
        accounts[4] = new Account(104, "Sandy", "Deposit", 500000.0, true);

        System.out.println("Created " + accounts.length + " Accounts");
    }

    public void viewAccounts() {
        System.out.println("Listing Accounts...");
        for(Account account: accounts) {
            System.out.println(account);
//            System.out.println(account.getId() + " " + account.getName() + " " + account.getType() + " " + account.getBalance() + " " + account.isActive());
        }
    }

    public void viewAccount(int id) {
        System.out.println("Viewing Account " + id);
        for (Account account: accounts) {
            if(account.getId() == id) {
                System.out.println(account);
            }
        }
    }

    public void updateAccount(int id, Account accountForUpdate) {
        System.out.println("Updating Account " + id);

        // Approach #1
//        for (Account account: accounts) {
//            if(account.getId() == id) {
//                account.setBalance(75000);
//                System.out.println("Account " + account.getId() + " Updated");
//            }
//        }

        // Approach #2
        for(int i=0; i < accounts.length; i++) {
            if(accounts[i].getId() == id) {
                accounts[i] = accountForUpdate;
                System.out.println("Account " + accounts[i].getId() + " Updated");
            }
        }
    }

    public void deleteAccount(int id) {
        System.out.println("Deleting Account " + id);
        for(int i=0; i < accounts.length; i++) {
            if(accounts[i].getId() == id) {
                int accId = accounts[i].getId();
                accounts[i] = null;
                System.out.println("Account " + accId + " Deleted");
            }
        }
    }
}
