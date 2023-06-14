package com.lowes.bankingapp.service;


import com.lowes.bankingapp.model.Account;

import com.lowes.bankingapp.exception.AccountException;


public interface AccountService {
    public void createAccount(Account account) throws AccountException;
    public Account[] getAccounts();
    public Account getAccount(int id) throws AccountException;
    public void updateAccount(int id, Account account)throws AccountException;
    public void deleteAccount(int id) throws AccountException;

}
