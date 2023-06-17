package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.*;

public class AccountServiceHashMapImpl implements AccountService {
    private HashMap<Integer,Account> accHashMap = new HashMap<>();

    /*Storing the account details as Key-Value pairs using put() method*/
    @Override
    public void createAccount(Account account) throws AccountException {
        accHashMap.put(account.getId(),account);
    }

    /*Displaying all the collection of accounts*/
    @Override
    public Collection<Account> getAccounts() {
        return accHashMap.values();
    }

    /* Checking for the matching id using contains() method and displaying the Key-Value pairs */
    @Override
    public Account getAccount(int id) throws AccountException {
        Account result = new Account();
        boolean isFound = false;
        if (accHashMap.containsKey(id)) {
           for(Map.Entry<Integer,Account> entry :accHashMap.entrySet()) {
                isFound = true;
                result = entry.getValue();
                break;
            }
        }
        if (!isFound) {
            throw new AccountException("Account not found!");
        }
        return  result;
    }

    /* Updating the account using put() method */
    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        for (int i : accHashMap.keySet()) {
            if (accHashMap.containsKey(id)) {
                accHashMap.put(id, account);
                isFound = true;
            }
        }
        if (!isFound) {
            throw new AccountException("Can't Update, no Account found");
        }
    }

    /* Deleting the account using remove() method */
    @Override
    public void deleteAccount(int id) throws AccountException {
        boolean isDeleted = false;
        for (int i : accHashMap.keySet()) {
            if (accHashMap.containsKey(id)) {
                accHashMap.remove(id);
                isDeleted = true;
            }
        }
        if (!isDeleted) {
            throw new ConcurrentModificationException ("Can't Delete, No Account found");
        }
    }
}
