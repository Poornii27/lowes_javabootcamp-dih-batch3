package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.util.ArrayList;
import java.util.Collection;

public class AccountServiceLnkLstImpl implements AccountService{
    @Override
    public void createAccount(Account account) throws AccountException {

    }

    @Override
    public Collection<Account> getAccounts() {
        return null;
    }

    @Override
    public Collection<Account>  getAccount(int id) throws AccountException {
        return null;
    }

    @Override
    public Collection<Account>  updateAccount(int id, Account account) throws AccountException {
        return null;

    }

    @Override
    public void deleteAccount(int id) throws AccountException {

    }
}
