package com.lowes.java.bankingapp.service;


import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.Collection;


public interface AccountService {
    public void createAccount(Account account) throws AccountException;
    public Collection<Account> getAccounts();
    public Account getAccount(int id) throws AccountException;
    public void updateAccount(int id, Account account)throws AccountException;
    public void deleteAccount(int id) throws AccountException;
    public int balanceAccount() throws AccountException;
    public void getAccountsByType();
    public void getAccountsByTypeSorted();
    public void getAverageBalanceByType();
    public void getAccountIdsContainsName(String name);
}
