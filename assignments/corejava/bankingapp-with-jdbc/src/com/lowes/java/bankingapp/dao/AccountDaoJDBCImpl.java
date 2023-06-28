package com.lowes.java.bankingapp.dao;

import com.lowes.java.bankingapp.model.Account;
import org.postgresql.jdbc.PgConnection;
import org.postgresql.util.PSQLException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.sql.DriverManager.getConnection;
public class AccountDaoJDBCImpl implements AccountDao {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/training";

    // Database credentials
    static final String USER = "postgres";
    static final String PASS = "postgres";

    public AccountDaoJDBCImpl() throws SQLException {
        Connection conn = null;
        // Open a connection
        try {
            conn = getConnection(DB_URL,USER,PASS);
            System.out.println("Connection established: " + conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            conn.close();
        }
    }


    @Override
    public boolean createAccount(Account account) throws SQLException {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        // Insertion with Prepared Statement
        try {
            conn = getConnection(DB_URL,USER,PASS);
            String query = "INSERT INTO account (name,type,balance,active,created_date) values (?,?,?,?,?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getType());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setBoolean(4, account.isActive());
            pstmt.setDate(5, Date.valueOf(account.getCreatedDate()));

            status = pstmt.executeUpdate() > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
            pstmt.close();
        }
        return status;
    }

    @Override
    public List<Account> getAccounts() throws SQLException {
        List<Account> accnts = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM account");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                Double balance = rs.getDouble("balance");
                Boolean active = rs.getBoolean("active");
                LocalDate createdDate = rs.getDate("created_date").toLocalDate();
                Date updatedDate = rs.getDate("updated_date");
                LocalDate updateDateNew = null;
                if(updatedDate == null) {
                    updateDateNew = LocalDate.of(1900,01,01);
                }
                else {
                    updateDateNew=rs.getDate("updated_date").toLocalDate();
                }
                accnts.add(new Account(id, name, type, balance, active, createdDate, updateDateNew));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
            stmt.close();
            rs.close();
        }
        return accnts;
    }

    @Override
    public Account getAccount(int accid) throws SQLException{
        Account acc = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs =null;

        try {
            conn = getConnection(DB_URL,USER,PASS);
            String query = "SELECT * FROM account WHERE id = " + accid;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                Double balance = rs.getDouble("balance");
                Boolean active = rs.getBoolean("active");
                Date createdDate = rs.getDate("created_date");
                Date updatedDate = rs.getDate("updated_date");
                acc = (new Account(id,name,type,balance,active,
                        createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        updatedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
            stmt.close();
        }
        return acc;
    }

    @Override
    public boolean updateAccount(int id, Account account) throws SQLException {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        // Insertion with Prepared Statement
        try {
            conn = getConnection(DB_URL,USER,PASS);
            String query = "UPDATE account SET name = ?, type = ?,balance = ?,active = ?,created_date = ?, updated_date = ? WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, account.getName());
            pstmt.setString(2, account.getType());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setBoolean(4, account.isActive());
            pstmt.setDate(5, Date.valueOf(account.getCreatedDate()));
            pstmt.setDate(6, Date.valueOf(account.getUpdatedDate()));
            pstmt.setInt(7,account.getId());


            status = pstmt.executeUpdate() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            pstmt.close();
            conn.close();
        }
        return status;

    }

    @Override
    public boolean deleteAccount(int id) throws SQLException  {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection(DB_URL,USER,PASS);
            String query = "DELETE FROM account WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,id);
            status = pstmt.executeUpdate()> 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            conn.close();
            pstmt.close();
        }
    return status;
    }
}







