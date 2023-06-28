package com.lowes.java.bankingapp.service;


import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public interface AccountService {
    public boolean createAccount(Account account) throws AccountException, SQLException;
    public Collection<Account> getAccounts() throws SQLException;
    public Account getAccount(int id) throws AccountException, SQLException;
    public boolean updateAccount(int id, Account account) throws AccountException, SQLException;
    public boolean deleteAccount(int id) throws AccountException, SQLException;
    public long balanceAccount(double limit) throws AccountException, SQLException;
    public Map<String, Long> getAccountsByType() throws SQLException;
    public List<Map.Entry<String, Long>> getAccountsByTypeSorted() throws SQLException;
    public void getAverageBalanceByType() throws SQLException;
    public void getAccountIdsContainsName(String name) throws SQLException;

    public Map<String, Double> getAverageAgeOfAccounts();

    public void ImportData() throws FileNotFoundException, SQLException;
    public void ExportData() throws IOException, SQLException;

}
