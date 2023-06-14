package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.lang.reflect.Array;
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
    public ArrayList<Account> getAccounts() {

        Iterator it = accounts.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
        return accounts;
    }

    @Override
    public ArrayList<Account> getAccount(int id) throws AccountException {

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                System.out.println("Fetching Account " + id);
                System.out.println(accounts.get(i));
                break;
            }
        else{
            throw new AccountException("Account not found!");
            }
        }
        return accounts;

    }

    @Override
    public Collection<Account> updateAccount(int id, Account account) throws AccountException {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && (accounts.get(i).getId() == id || accounts.get(i+1)!=null)) {
                accounts.set(i, account);
                System.out.println("Account " + accounts.get(i).getId() + " Updated");
                break;
            } else {
                throw new AccountException("Can't Update, no Account found");
            }
        }

        return accounts;
    }


    @Override
    public void deleteAccount(int id) throws AccountException {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null && accounts.get(i).getId() == id) {
                accounts.remove(i);
                System.out.println("Account Deleted...");
                break;
            }

        }
    }
}

