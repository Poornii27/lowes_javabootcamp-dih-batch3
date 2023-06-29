package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.*;
import java.util.function.DoublePredicate;

public class AccountServiceTreeMapImpl implements AccountService {
    //Using Comparator to perform Sorting
    Comparator<Integer> SORT_BY_ID_ASC = new Comparator<>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };
    Comparator<Integer> SORT_BY_ID_DESC = new Comparator<>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    };
  
    Map<Integer, Account> accTreeMap = new TreeMap<>(SORT_BY_ID_ASC);

    /*Storing the account details as Key-Value pairs using put() method*/
    @Override
    public void createAccount(Account account) throws AccountException {
        accTreeMap.put(account.getId(), account);
        System.out.println(accTreeMap);

    }

    /* Displaying the Key-Value collections*/
    @Override
    public Collection<Account> getAccounts() {
        return accTreeMap.values();
    }

    /* Checking for the matching id using contains() method and displaying the Key-Value pairs */
    @Override
    public Account getAccount(int id) throws AccountException {
        boolean isFound = false;
        Account result = new Account();
        if (accTreeMap.containsKey(id)) {
            result = accTreeMap.get(id);
            isFound = true;
        }
        if (!isFound) {
            throw new AccountException("Account not found!");
        }
        return result;
    }

    /*Updates Account for the id contained in the key-set using put () method*/
    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        boolean isFound = false;
        for (int i : accTreeMap.keySet()) {
            if (accTreeMap.containsKey(id)) {
                accTreeMap.put(id, account);
                System.out.println(accTreeMap.get(id));
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new AccountException("Can't Update, no Account found");
        }
    }

    /* Delete an account using remove() method*/
    @Override
    public void deleteAccount(int id) throws AccountException {
        boolean isDeleted = false;
        {
            if (accTreeMap.containsKey(id)) {
                accTreeMap.remove(id);
                isDeleted = true;
            }
            if (!isDeleted) {
                throw new AccountException("Can't Delete, No Account found");
            }
        }
    }
}