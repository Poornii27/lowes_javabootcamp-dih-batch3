package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;


public class AccountServiceLnkLstImpl implements AccountService{

    List acclist = new LinkedList();

    @Override
    public void createAccount(Account account) throws AccountException {
        acclist.add(account);
    }

    @Override
    public Collection<Account> getAccounts() {
        return acclist;

    }

    @Override
    public Account getAccount(int id) throws AccountException {
        boolean isFound = false;
        Account result = new Account();
        for (int i = 0; i < acclist.size(); i++) {
            if (acclist.get(i) != null && ((Account)acclist.get(i)).getId() == id) {
                result = (Account)acclist.get(i);
                isFound = true;
            }
        }
        if(!isFound) {
            throw new AccountException("Account not found!");
        }
        return  result;
    }

    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;

        for (int i = 0; i < acclist.size(); i++) {
            if (acclist.get(i) != null && ((Account)acclist.get(i)).getId() == id) {
                acclist.set(i, account);
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
        for (int i = 0; i < acclist.size(); i++) {
            if (acclist.get(i) != null && ((Account)acclist.get(i)).getId() == id) {
                acclist.remove(i);
                isDeleted = true;
            }
        }
        if(!isDeleted) {
            throw new AccountException("Can't Delete, No Account found!");
        }

    }
}
