package com.lowes.bankingapp.dao;

import com.lowes.bankingapp.model.Account;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;


public class AccountDaoHibernateImpl implements AccountDao {

    public static SessionFactory factory;

    public static SessionFactory getFactory() {

        if (factory == null) {
            // STEP 1: Create Configuration
            Configuration config = new Configuration();
            // loads hibernate mapping configs from annotated class
            config.addAnnotatedClass(Account.class);

            // STEP 2: Create SessionFactory
            factory = config.buildSessionFactory();
            System.out.println("Database Connection Successful!!! - " + factory);
        }
        return factory;
    }

    @Override
    public Integer createAccount(Account account) {

        Transaction tnx = null;
        Integer id = -1;

        try (Session session = getFactory().openSession()) {
            tnx = session.beginTransaction();

            // Insert data into table by supplying the persistent object
            id = (Integer) session.save(account);

            System.out.println("\nAccounts created successfully with ID - " + id);

            tnx.commit();
        } catch (HibernateException he) {
            tnx.rollback();
            he.printStackTrace();
        }

        return id;
    }

    public List<Account> getAccounts() throws SQLException {
        Session session = getFactory().openSession();
        Transaction txn = null;
        List<Account> accounts = null;

        try {
            txn = session.beginTransaction();
            Criteria criteria = session.createCriteria(Account.class);
            accounts = criteria.list();

            // System.out.println("ID \tName \tAge \tDepartment \tDesignation");
            for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext(); ) {
                Account account = (Account) iterator.next();

                System.out.println(account.getId() + "\t" + account.getName() + "\t" + account.getBalance() + "\t"
                        + "\t" + account.getType() + "\t" + account.isActive() + "\t" + account.getCreatedDate() +
                        "\t" + account.getUpdatedDate());
            }

            txn.commit();
        } catch (HibernateException e) {
            if (txn != null)
                txn.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return accounts;
    }


    public Account getAccount(int accid) throws SQLException {
        Session session = getFactory().openSession();
        Transaction txn = null;

        Account acc = null;
        try {
            txn = session.beginTransaction();
            acc = session.get(Account.class, accid);

            txn.commit();
        } catch (HibernateException e) {
            if (txn != null)
                txn.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return acc;
    }


    public boolean updateAccount(int id, Account account) throws SQLException {
        Session session = getFactory().openSession();
        Transaction txn = null;
        boolean status = false;

        try {
            txn = session.beginTransaction();
            session.get(Account.class, id);

            Account accUpdate = session.get(Account.class, id);
            accUpdate.setName(account.getName());
            accUpdate.setType(account.getType());
            accUpdate.setBalance(account.getBalance());
            accUpdate.setActive(account.isActive());
            accUpdate.setCreatedDate(LocalDate.now());
            accUpdate.setUpdatedDate(LocalDate.now());

            session.update(accUpdate);
            status = true;
            txn.commit();
        } catch (HibernateException e) {
            if (txn != null)
                txn.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return status;
    }


    public boolean deleteAccount(int id) throws SQLException {
        Session session = getFactory().openSession();
        Transaction txn = null;
        boolean status = false;

        try {
            txn = session.beginTransaction();
            Account empDel = session.get(Account.class, id);
            session.delete(empDel);
            status = true;
            txn.commit();
        } catch (HibernateException e) {
            if (txn != null)
                txn.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return status;
    }
}
