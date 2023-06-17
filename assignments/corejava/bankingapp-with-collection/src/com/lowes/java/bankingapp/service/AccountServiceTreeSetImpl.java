package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.*;

public class AccountServiceTreeSetImpl implements AccountService {

    Set<Account> accountTreeSet = new TreeSet<>(SORT_BY_ID_ASC);

    /* Sorting based on key index*/
    private static Comparator<Account> SORT_BY_ID_ASC = new Comparator<Account>() {

        @Override
        public int compare(Account o1, Account o2) {
            return o1.getId() - o2.getId();
        }

    };

    /* Creating accounts using add() method.*/
    @Override
    public void createAccount(Account account) throws AccountException {
        accountTreeSet.add(account);
    }

    /* Displaying all the accounts*/
    @Override
    public Collection<Account> getAccounts() {
        return accountTreeSet;
    }

    @Override
    public Account getAccount(int id) throws AccountException {
        boolean isFound = false;
        Account result = new Account();
        Account[] setString = accountTreeSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++) {
            if ((setString != null) && (setString[i].getId() == id)) {
                result = setString[i];
                isFound = true;
            }
        }
        if (!isFound) {
            throw new AccountException("Account not found!");
        }
        return result;
    }

    /* Updating the account using remove () method to remove the account and add again using add() method*/
        @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        boolean removed = false;
        Account[] setString = accountTreeSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++) {
            if ((setString != null) && (setString[i].getId() == id)) {
                accountTreeSet.remove(account);
                removed = true;

                if (removed) {
                    accountTreeSet.add(account);
                    removed = false;
                }
                isFound = true;
            }
        }
        if (!isFound) {
            throw new AccountException("Can't Update, no Account found");
        }
    }

    /* Deleting an account using remove() method*/
    @Override
    public void deleteAccount(int id) throws AccountException {
        boolean isDeleted = false;

        Account[] setString = accountTreeSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++) {
            if ((setString != null) && (setString[i].getId() == id)) {
                accountTreeSet.remove(setString[i]);
                isDeleted = true;
            }
        }
        if (!isDeleted) {
            throw new AccountException("Can't Delete, no Account found");
        }
    }
}

