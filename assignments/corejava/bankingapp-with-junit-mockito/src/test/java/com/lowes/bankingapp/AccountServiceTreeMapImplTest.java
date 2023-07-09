package com.lowes.bankingapp;

import com.lowes.bankingapp.dao.AccountDao;
import com.lowes.bankingapp.exception.AccountException;
import com.lowes.bankingapp.model.Account;
import com.lowes.bankingapp.service.AccountServiceTreeMapImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTreeMapImplTest {


    @InjectMocks
    AccountServiceTreeMapImpl accService = new AccountServiceTreeMapImpl();

    @Mock
    AccountDao accountDao;

    Collection<Account> accounts = new ArrayList<>();

    Map<Integer, Account> retVal = null;

    public AccountServiceTreeMapImplTest() throws SQLException {
    }


    @BeforeEach
    public void setup() throws AccountException, SQLException {

        System.out.println("Test data initialization at each test case level..");
        // Initialize Test data
        Account account1 = new Account(11, "Pankaj Kumar", "Loan", 2000.0, true, LocalDate.of(2022, 02, 27), LocalDate.now());
        accService.createAccount(account1);
        accounts.add(account1);

        Account account2 = new Account(12, "Pradeep Kumar Pandey", "Deposit", 1200.0, true, LocalDate.of(2022, 05, 21), LocalDate.now());
        accService.createAccount(account2);
        accounts.add(account2);
    }

    @AfterEach
    public void cleanup() {
        System.out.println("Test data clean up at each test case level..");
    }

    //Account Creation Testcases
    @Test
    public void shouldCreateAccount_WhenPassingCorrectDetails() throws SQLException {
        Account account = new Account(3, "Vihaan", "Savings", 10000, true, LocalDate.now(), LocalDate.now());
        Mockito.when(accountDao.createAccount(account)).thenReturn(true);

        try {
            assertEquals(true, accService.createAccount(account));
        } catch (com.lowes.bankingapp.exception.AccountException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldnotCreateAccount_WhenPassingInCorrectName() {
        Account account1 = new Account(4, "Jo", "Savings", 10000, true, LocalDate.now(), LocalDate.now());

        Throwable ex = assertThrows(Exception.class, () -> accService.createAccount(account1));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }


    @Test
    public void shouldNotCreateAccount_WhenNoDetailsArePassed() {
        Account account = new Account();
        Throwable ex = assertThrows(Exception.class, () -> accService.createAccount(account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenBalanceIsLessThanTheRange() {
        Account account1 = new Account(4, "Anu", "Savings", 10.0, true, LocalDate.now(), LocalDate.now());

        Throwable ex = assertThrows(Exception.class, () -> accService.createAccount(account1));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenBalanceIsNegative() {
        Account account1 = new Account(4, "Anu", "Savings", -5.0, true, LocalDate.now(), LocalDate.now());

        Throwable ex = assertThrows(Exception.class, () -> accService.createAccount(account1));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenEnumTypeIsInvalid() {
        Account account1 = new Account(4, "Priya", "Personal", 1000.0, true, LocalDate.now(), LocalDate.now());

        Throwable ex = assertThrows(Exception.class, () -> accService.createAccount(account1));
        assertEquals("Invalid Type,Please Choose Deposit,Loan or Savings", ex.getMessage());
    }

    //Get All Accounts Testcase
    @Test
    public void shouldReturnAllAccounts_WhenAccountIdIsNotMentioned() throws SQLException {
        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        Collection<Account> accounts = accService.getAccounts();
        System.out.println(accounts);
        assertNotNull(accounts);
    }

    //View an Account Testcases
    @Test
    public void shouldReturnAnAccount_WhoseAccountIdIsValid() throws SQLException, AccountException {
        Account account = new Account(3, "Vihaan", "Savings", 10000, true, LocalDate.now(), LocalDate.now());
        Mockito.when(accountDao.getAccount(3)).thenReturn(account);
        assertEquals(3, accService.getAccount(3).getId());
    }

    //Update Account Testcases
    @Test
    public void shouldUpdateAccount_WhenValidIdIsProvided() throws AccountException, SQLException {
        Account account = new Account(12, "Vihaana", "Savings", 10000, true, LocalDate.now(), LocalDate.now());
        Mockito.when(accountDao.updateAccount(12, account)).thenReturn(true);
        assertEquals(true, accService.updateAccount(12, account));
    }

    @Test
    public void shouldNotUpdateAccount_WhenInvalidIdIsProvided() {
        Account account = new Account(9, "Vihaan", "Savings", 10000, true, LocalDate.now(), LocalDate.now());
        Throwable ex = assertThrows(Exception.class, () -> accService.updateAccount(9, account));
        assertEquals("Can't Update, no Account found", ex.getMessage());
    }


    @Test
    public void shouldNotUpdateAccount_WhenInvalidName_BalanceIsProvided() {
        Account account = new Account(1, "Vi", "Savings", 10, true, LocalDate.now(), LocalDate.now());
        Throwable ex = assertThrows(Exception.class, () -> accService.updateAccount(1, account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldNotUpdateAccount_WhenInvalidTypeIsProvided() {
        Account account = new Account(1, "Vihaan", "Personal", 10, true, LocalDate.now(), LocalDate.now());

        Throwable ex = assertThrows(Exception.class, () -> accService.updateAccount(1, account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    //Delete Account Testcase
    @Test
    public void shouldDeleteAccount_WhenValidIdIsProvided() throws AccountException, SQLException {
        //  accService.deleteAccount(2);
        Mockito.when(accountDao.deleteAccount(12)).thenReturn(true);
        assertEquals(true, accService.deleteAccount(12));
    }

    @Test
    public void shouldPrintErrorMessage_WhenInValidIdIsProvided() throws AccountException {
        Throwable ex = assertThrows(Exception.class, () -> accService.deleteAccount(6));
        assertEquals("Can't Delete, No Account found", ex.getMessage());
    }

    //Print Statistics Testcases
    @Test
    public void shouldDisplayNumberOfAccounts_WhenBalanceGreaterThanLimit() throws AccountException, SQLException {
        Account account = new Account(6, "Poorni", "Savings", 200000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Sami", "Loan", 300000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);

        assertEquals(2, accService.balanceAccount(100000));
    }

    @Test
    public void shouldDisplayZeroAccounts_WhenBalanceLessThanLimit() throws AccountException, SQLException {
        Account account = new Account(6, "Poorni", "Savings", 2000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Sami", "Loan", 3000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        assertEquals(0, accService.balanceAccount(100000));

    }

    @Test
    public void shouldGroupAccountByType_AndDisplayTheCount() throws AccountException, SQLException {
        Account account = new Account(6, "Siddhu", "Savings", 300000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Shravan", "Loan", 5000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Account account2 = new Account(8, "Shashank", "Savings", 400000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account2);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        assertEquals(3, accService.getAccountsByType().size());
    }

    @Test
    public void shouldSortAccountByType_AndDisplayTheCount() throws AccountException, SQLException {
        Account account = new Account(6, "Siddhu", "Savings", 300000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Shravan", "Loan", 5000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);

        assertEquals(3, accService.getAccountsByTypeSorted().size());
    }

    @Test
    public void shouldDisplayAccountByType_WhenAverageBalanceIsCalculated() throws AccountException, SQLException {
        Account account = new Account(6, "Siddhu", "Savings", 400000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Shravan", "Loan", 5000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Account account2 = new Account(8, "Shashank", "Savings", 400000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account2);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        assertEquals(3, accService.getAverageBalanceByType().size());
    }

    @Test
    public void containsNameTest() throws SQLException {
        Account account = new Account(6, "SiddhuKumar", "Savings", 400000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(7, "Shravan", "Loan", 5000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        Account account2 = new Account(9, "Kumaran", "Loan", 5000, true, LocalDate.now(), LocalDate.now());
        accounts.add(account2);

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        assertEquals(4, accService.getAccountIdsContainsName("Kumar"));
    }


    //Import File Testcase
    @Test
    public void ImportFileTest() throws IOException, SQLException {
        Account account = new Account(16, "SiddhuKumar", "Savings", 400000.00, true, LocalDate.now(), LocalDate.now());
        accounts.add(account);

        Account account1 = new Account(17, "Shravan", "Loan", 5000.00, true, LocalDate.now(), LocalDate.now());
        accounts.add(account1);

        //Import validation - 2 from BeforeEach block and 4 from the Test file
        Mockito.lenient().when(accountDao.createAccount(account)).thenReturn(true);
        retVal = accService.ImportData("./input/account.txt");
        assertEquals(8, retVal.size());
    }

    //Export File Testcase
    @Test
    public void ExportFileTest() throws IOException, SQLException {
        //Before Export- Checking for empty file
        String fileName = "./output/accountTestOut.txt";
        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        file.setLength(0);
        assertEquals(null, br.readLine());
        br.close();

        Mockito.when(accountDao.getAccounts()).thenReturn((List<Account>) accounts);
        //After export - checking for loaded file.  Comparing the number of lines
        accService.ExportData(fileName, retVal);
        BufferedReader br1 = new BufferedReader(new FileReader(fileName));
        int lines = 0;
        while (br1.readLine() != null) lines ++;
        br1.close();
        assertEquals(2, lines);
    }

}



