package com.lowes.java.bankingapp.service;


import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public interface AccountService {
    public void createAccount(Account account) throws AccountException;
    public Collection<Account> getAccounts();
    public Account getAccount(int id) throws AccountException;
    public void updateAccount(int id, Account account)throws AccountException;
    public boolean deleteAccount(int id) throws AccountException;
    public long balanceAccount(double limit) throws AccountException;
    public Map<String, Long> getAccountsByType();
    public List<Map.Entry<String, Long>> getAccountsByTypeSorted();
    public void getAverageBalanceByType();
    public void getAccountIdsContainsName(String name);
    public void ImportData() throws FileNotFoundException;
    public void ExportData() throws IOException;

    public Map<String, Double> getAverageAgeOfAccounts();
}
