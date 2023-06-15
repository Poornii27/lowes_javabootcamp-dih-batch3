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
        System.out.println(acclist);
    }

    @Override
    public Collection<Account> getAccounts() {
        Iterator it = acclist.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
        if (!it.hasNext()){
            System.out.println("No accounts found!");
        }
        return acclist;

    }

    @Override
    public void  getAccount(int id) throws AccountException {
        boolean isFound = false;
        for (int i = 0; i < acclist.size(); i++) {
            if (acclist.get(i) != null && ((Account)acclist.get(i)).getId() == id) {
                System.out.println("Fetching Account " + id);
                System.out.println(acclist.get(i));
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

        for (int i = 0; i < acclist.size(); i++) {
            if (acclist.get(i) != null && ((Account)acclist.get(i)).getId() == id) {
                acclist.set(i, account);
                isFound = true;
                System.out.println("Account " + ((Account)acclist.get(i)).getId() + " Updated");
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
                System.out.println("Account Deleted...");
            }
        }
        if(!isDeleted) {
            throw new AccountException("Can't Delete, no Account found");
        }

    }
}
