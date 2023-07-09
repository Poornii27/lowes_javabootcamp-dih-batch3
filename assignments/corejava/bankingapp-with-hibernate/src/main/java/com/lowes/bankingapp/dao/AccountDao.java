package com.lowes.bankingapp.dao;

import com.lowes.bankingapp.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    public Integer createAccount(Account account) throws SQLException;
  //  public boolean createAccount(Account account) throws SQLException;


    public List<Account> getAccounts() throws SQLException;

    public Account getAccount(int accid) throws  SQLException;

    public boolean updateAccount(int id, Account account) throws SQLException;

    public boolean deleteAccount(int id) throws SQLException;
}
