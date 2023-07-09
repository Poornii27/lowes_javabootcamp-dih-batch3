package com.lowes.java.bankingapp.dao;

import com.lowes.java.bankingapp.model.Account;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface AccountDao {
    public boolean createAccount(Account account) throws SQLException;

    public List<Account> getAccounts() throws SQLException;

    public Account getAccount(int accid) throws  SQLException;

    public boolean updateAccount(int id, Account account) throws SQLException;

    public boolean deleteAccount(int id) throws SQLException;
}
