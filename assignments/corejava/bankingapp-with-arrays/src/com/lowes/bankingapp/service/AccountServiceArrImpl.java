package com.lowes.bankingapp.service;

import com.lowes.bankingapp.exception.AccountException;
import com.lowes.bankingapp.model.Account;

import javax.swing.*;


public class AccountServiceArrImpl implements AccountService {
    private Account[] accounts = new Account[10];
    int index = 0;

    @Override
    public void createAccount(Account account) {
            accounts[index++] = account;
            System.out.println("Account " + account.getId() + " Created.");
            }

    @Override
    public Account[] getAccounts() {
        Account[] accounts1 = this.accounts;
        return accounts1;
    }

    @Override
    public Account getAccount(int id) throws AccountException {
        Account acc = null;
        for (Account account : accounts) {
            if (account != null && account.getId() == id) {
                acc = account;
            }
        }
        if (acc == null) {
            throw new AccountException("Enter a valid Account Id");
        }
        return acc;
    }

    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getId() == id) {
                accounts[i] = account;
                System.out.println("Account " + accounts[i].getId() + " Updated");

            }
        }
    }

    @Override
    public void deleteAccount(int id) throws AccountException {
        for(int i=0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getId() == id) {
                id = accounts[i].getId();
                accounts[i] = null;
                System.out.println("Account " + id + " Deleted");
            }
            else {
                throw new AccountException("No account found for the given id. Enter valid account to delete ");
            }
        }

    }

}
