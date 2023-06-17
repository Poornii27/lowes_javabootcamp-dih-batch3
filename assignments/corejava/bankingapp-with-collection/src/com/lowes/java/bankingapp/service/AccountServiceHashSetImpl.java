package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AccountServiceHashSetImpl implements AccountService{

     Set<Account> accSet = new HashSet();


     /*Storing the account details using add() method*/
    @Override
    public void createAccount(Account account) throws AccountException {
        accSet.add(account);
    }

    /*Displaying all the accounts based on hashing */
    @Override
    public Collection<Account> getAccounts() {
        return accSet;
    }

    /* Fetching account details of a particular id*/
    @Override
    public Account getAccount(int id) throws AccountException {
        Account result = new Account();
        boolean isFound = false;
        Account[] setString = accSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++)
        {
            if ((setString!=null) && (setString[i].getId() == id))
            {
                result = setString[i];
                isFound = true;
            }
        }
        if(!isFound) {
            throw new AccountException("Account not found!");
        }
        return result;
    }

    /* Updating the account using remove () method to remove the account and add again using add() method*/
    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        boolean removed = false;
        Account[] setString = accSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++)
        {
            if ((setString!=null) && (setString[i].getId() == id))
            {
                accSet.remove(account);
                removed = true;

                if(removed) {
                    accSet.add(account);
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
        Account[] setString = accSet.toArray(new Account[0]);
        for (int i = 0; i < setString.length; i++)
        {
            if ((setString!=null) && (setString[i].getId() == id))
            {
                accSet.remove(setString[i]);
                isDeleted = true;
            }
        }
        if(!isDeleted) {
            throw new AccountException("Can't Delete, no Account found");
        }
    }
}
