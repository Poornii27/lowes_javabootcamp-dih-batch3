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
        System.out.println(accounts);
    }

    @Override
    public Collection<Account> getAccounts() {

        Iterator it = accounts.iterator();

        if (accounts != null) {
            while (it.hasNext()) {
                System.out.println(it.next());
            }
            if(!it.hasNext()){
                System.out.println("No accounts found!");
            }
        }
        return accounts;
    }

    @Override
    public void getAccount(int id) throws AccountException {
        boolean isFound = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                System.out.println("Fetching Account " + id);
                System.out.println(accounts.get(i));
                isFound = true;
            }
        }
        if(!isFound) {
            throw new AccountException("Account not found!");
        }
    }

    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                accounts.set(i, account);
                isFound = true;
                System.out.println("Account " + accounts.get(i).getId() + " Updated");
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
                System.out.println("Account Deleted...");
            }
        }
        if(!isDeleted) {
            throw new AccountException("Can't Delete, no Account found");
        }

    }
}

