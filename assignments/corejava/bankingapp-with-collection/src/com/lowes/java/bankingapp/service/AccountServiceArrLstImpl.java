package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class AccountServiceArrLstImpl implements AccountService {
    ArrayList<Account> accounts = new ArrayList<>();

    @Override
    public void createAccount(Account account) throws AccountException {
        accounts.add(account);
    }

    @Override
    public Collection<Account> getAccounts() {
        return accounts;
    }

    @Override
    public Account getAccount(int id) throws AccountException {
        boolean isFound = false;
        Account result = new Account();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                isFound = true;
                result = accounts.get(i);
            }
        }
        if(!isFound) {
            throw new AccountException("Account not found!");
        }
        return result;
    }

    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                accounts.set(i, account);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new AccountException("Can't Update, no Account found");
        }
    }
    @Override
    public void deleteAccount(int id) throws AccountException {
        boolean isDeleted = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                accounts.remove(i);
                isDeleted = true;
            }
        }
        if(!isDeleted) {
            throw new AccountException("Can't Delete, No Account found");
        }

    }
}

