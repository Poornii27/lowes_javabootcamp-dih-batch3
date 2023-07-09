package com.lowes.bankingapp.service;


import com.lowes.bankingapp.exception.AccountException;
import com.lowes.bankingapp.model.Account;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface AccountService {
    public boolean createAccount(Account account) throws AccountException, SQLException;
    public Collection<Account> getAccounts() throws SQLException;
    public Account getAccount(int id) throws AccountException, SQLException;
    public boolean updateAccount(int id, Account account) throws AccountException, SQLException;
    public boolean deleteAccount(int id) throws AccountException, SQLException;
    public long balanceAccount(double limit) throws AccountException, SQLException;
    public Map<String, Long> getAccountsByType() throws SQLException;
    public List<Map.Entry<String, Long>> getAccountsByTypeSorted() throws SQLException;
    public Map<String, Double> getAverageBalanceByType() throws SQLException;
    public long getAccountIdsContainsName(String name) throws SQLException;

    public Map<String, Double> getAverageAgeOfAccounts();


    Map<Integer, Account>  ImportData(String filePath) throws SQLException;

    //public void ExportData() throws IOException, SQLException;

   public void ExportData(String filePath, Map<Integer, Account> accMap);
}
