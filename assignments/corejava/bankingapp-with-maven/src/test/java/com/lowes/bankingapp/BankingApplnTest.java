package com.lowes.bankingapp;

import com.lowes.bankingapp.model.Account;
import com.lowes.bankingapp.service.AccountServiceTreeMapImpl;
import com.lowes.bankingapp.exception.AccountException;
import com.lowes.bankingapp.service.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


public class BankingApplnTest {

    AccountServiceTreeMapImpl accountService = new AccountServiceTreeMapImpl();


    @BeforeEach
    public void setup() throws AccountException {

        System.out.println("Test data initialization at each test case level..");
        // Initialize Test data
        Account account1 = new Account();

        account1.setId(1);
        account1.setName("Poorni");
        account1.setType("Loan");
        account1.setBalance(200000.0);
        account1.setActive(true);
        account1.setCreatedDate(LocalDate.of(12,02,27));
        account1.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account1);


        Account account2 = new Account();
        account2.setId(2);
        account2.setName("Sami");
        account2.setType("Deposit");
        account2.setBalance(1200.0);
        account2.setActive(true);
        account2.setCreatedDate(LocalDate.of(18,05,21));
        account2.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account2);

    }
    @AfterEach
    public void cleanup() {
        System.out.println("Test data clean up at each test case level..");
    }

    //Positive Testcases
    @Test
    public void shouldCreateAccount_WhenPassingCorrectDetails() throws AccountException {
        Account account = new Account();
        account.setId(3);
        account.setName("Vihaan");
        account.setType("Savings");
        account.setBalance(10000.0);
        account.setActive(true);
        accountService.createAccount(account);
        assertEquals(3, accountService.getAccount(3).getId());
        assertEquals(3, accountService.getAccounts().size());
    }

    //negative Testcases
    @Test
    public void shouldnotCreateAccount_WhenPassingInCorrectName() {
        Account account = new Account();
        account.setId(4);
        account.setName("Jo");
        account.setType("Savings");
        account.setBalance(10000.0);
        account.setActive(true);

        Throwable ex = assertThrows(Exception.class,()-> accountService.createAccount(account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldNotCreateAccount_WhenNoDetailsArePassed() {
        Account account = new Account();
        Throwable ex = assertThrows(Exception.class, () -> accountService.createAccount(account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenBalanceIsLessThanTheRange() {
        Account account = new Account();
        account.setId(4);
        account.setName("Anu");
        account.setType("Savings");
        account.setBalance(10.0);
        account.setActive(true);

        Throwable ex = assertThrows(Exception.class,()-> accountService.createAccount(account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenBalanceIsNegative()  {
        Account account = new Account();
        account.setId(4);
        account.setName("Anu");
        account.setType("Savings");
        account.setBalance(-5.0);
        account.setActive(true);

        Throwable ex = assertThrows(Exception.class,()-> accountService.createAccount(account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldThrowException_WhenEnumTypeIsInvalid() {
        Account account = new Account();
        account.setId(4);
        account.setName("Priya");
        account.setType("Personal");
        account.setBalance(1000.0);
        account.setActive(true);

        Throwable ex = assertThrows(Exception.class,()-> accountService.createAccount(account));
        assertEquals("Invalid Type,Please Choose Deposit,Loan or Savings", ex.getMessage());
    }


    @Test
    public void shouldReturnAllAccounts_WhenAccountIdIsNotMentioned() {
        assertEquals(2, accountService.getAccounts().size());
    }

    @Test
    public void shouldReturnAnAccount_WhoseAccountIdIsMentioned() throws AccountException {
        assertNotNull(accountService.getAccount(2));
        assertEquals(2, accountService.getAccount(2).getId());
    }

    @Test
    public void shouldReturnAnException_WhenInvalidAccountIdIsMentioned()  {
        Throwable ex = assertThrows(Exception.class,()-> accountService.getAccount(3).getId());
        assertEquals("Account not found!", ex.getMessage());
    }


    @Test
    public void shouldUpdateAccount_WhenValidIdIsProvided() throws AccountException {
        Account account = new Account();
        account.setName("Vihaana");
        account.setType("Savings");
        account.setBalance(1000);
        account.setActive(false);

        accountService.updateAccount(2, account);

        assertNotNull(accountService.getAccount(2));
        assertEquals("Vihaana",accountService.getAccount(2).getName());
    }

    @Test
    public void shouldNotUpdateAccount_WhenInvalidIdIsProvided() {
        Account account = new Account();

        account.setId(8);
        account.setName("Vihaana");
        account.setType("Savings");
        account.setBalance(1000);
        account.setActive(false);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());

        Throwable ex = assertThrows(Exception.class,()-> accountService.updateAccount(8, account));
        assertEquals("Can't Update, no Account found", ex.getMessage());
    }

    @Test
    public void shouldNotUpdateAccount_WhenInvalidName_BalanceIsProvided() {
        Account account = new Account();

        account.setId(1);
        account.setName("Vi");
        account.setType("Savings");
        account.setBalance(10);
        account.setActive(false);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());

        Throwable ex = assertThrows(Exception.class,()-> accountService.updateAccount(1, account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldNotUpdateAccount_WhenInvalidTypeIsProvided() {
        Account account = new Account();

        account.setId(1);
        account.setName("Vihaana");
        account.setType("Personal");
        account.setBalance(1000);
        account.setActive(false);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());

        Throwable ex = assertThrows(Exception.class,()-> accountService.updateAccount(1, account));
        assertEquals("Account Creation Failed, Please Create Another Account", ex.getMessage());
    }

    @Test
    public void shouldDeleteAccount_WhenValidIdIsProvided() throws AccountException {
        accountService.deleteAccount(2);
     //   Throwable ex = assertThrows(Exception.class,()-> accountService.getAccount(2));
        assertEquals(1,accountService.getAccounts().size());
    }

    @Test
    public void shouldPrintErrorMessage_WhenInValidIdIsProvided() throws AccountException {

        Throwable ex = assertThrows(Exception.class,()-> accountService.deleteAccount(6));
        assertEquals("Can't Delete, No Account found",ex.getMessage());
    }

    //***************************Print Statistics*************************/

    @Test
    public void shouldDisplayNumberOfAccounts_WhenBalanceGreaterThanLimit() throws AccountException {
        Account account = new Account();
        account.setId(6);
        account.setName("Poorni");
        account.setBalance(200000);
        account.setType("Savings");
        account.setActive(false);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);

        account.setId(7);
        account.setName("Sami");
        account.setBalance(300000);
        account.setType("Loan");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account);

        assertEquals(2,accountService.balanceAccount(100000));

    }


    @Test
    public void shouldDisplayZeroAccounts_WhenBalanceLessThanLimit() throws AccountException {
        Account account = new Account();
        account.setId(6);
        account.setName("Poorni");
        account.setBalance(2000);
        account.setType("Savings");
        account.setActive(false);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);

        account.setId(7);
        account.setName("Sami");
        account.setBalance(300);
        account.setType("Loan");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account);

        assertEquals(0,accountService.balanceAccount(100000));

    }

    @Test
    public void shouldGroupAccountByType_AndDisplayTheCount() throws AccountException {
        Account account = new Account();

        account.setId(6);
        account.setName("Siddhu");
        account.setBalance(300000);
        account.setType("Savings");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);

        Account account1 = new Account();

        account1.setId(7);
        account1.setName("Shravan");
        account1.setBalance(5000);
        account1.setType("Loan");
        account1.setActive(true);
        account1.setCreatedDate(LocalDate.now());
        account1.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account1);

        assertEquals(3, accountService.getAccountsByType().size());
    }

    @Test
    public void shouldSortAccountByType_AndDisplayTheCount() throws AccountException {
        Account account = new Account();

        account.setId(6);
        account.setName("Siddhu");
        account.setBalance(300000);
        account.setType("Savings");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);

        Account account1 = new Account();

        account1.setId(7);
        account1.setName("Shravan");
        account1.setBalance(5000);
        account1.setType("Loan");
        account1.setActive(true);
        account1.setCreatedDate(LocalDate.now());
        account1.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account1);

        assertEquals(3, accountService.getAccountsByTypeSorted().size());
    }

    @Test
    public void shouldDisplayAccountByType_WhenAverageBalanceIsCalculated() throws AccountException {
        Account account = new Account();

        account.setId(6);
        account.setName("Siddhu");
        account.setBalance(300000);
        account.setType("Savings");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);
        Account account1 = new Account();

        account1.setId(7);
        account1.setName("Shravan");
        account1.setBalance(5000);
        account1.setType("Loan");
        account1.setActive(true);
        account1.setCreatedDate(LocalDate.now());
        account1.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account1);
        assertEquals(3,accountService.getAverageBalanceByType().size());
    }
 /*   @Test
    public void shouldDisplayAccount_WhenItContainsTheGivenName() throws AccountException {
        Account account = new Account();

        account.setId(6);
        account.setName("KumaraSiddhu");
        account.setBalance(300000);
        account.setType("Savings");
        account.setActive(true);
        account.setCreatedDate(LocalDate.now());
        account.setUpdatedDate(LocalDate.now());
        accountService.createAccount(account);
        Account account1 = new Account();

        account1.setId(7);
        account1.setName("ShravanKumar");
        account1.setBalance(5000);
        account1.setType("Loan");
        account1.setActive(true);
        account1.setCreatedDate(LocalDate.now());
        account1.setUpdatedDate(LocalDate.now());

        accountService.createAccount(account1);

        assertEquals(2,  accountService.getAccountIdsContainsName("Kumar").size());
    }*/
 /*@Test
 public void shouldGroupAccountByType_ByItsAverageAge() throws AccountException {
     Account account = new Account();

     account.setId(6);
     account.setName("Siddhu");
     account.setBalance(4000.0);
     account.setType("Savings");
     account.setActive(true);
     account.setCreatedDate(LocalDate.of(20,07,03));
     account.setUpdatedDate(LocalDate.now());
     accountService.createAccount(account);

     Account account1 = new Account();

     account1.setId(7);
     account1.setName("Shravan");
     account1.setBalance(500000);
     account1.setType("Loan");
     account1.setActive(true);
     account1.setCreatedDate(LocalDate.of(12,02,27));
     account1.setUpdatedDate(LocalDate.now());

     accountService.createAccount(account1);

     assertEquals(3, accountService.getAverageAgeOfAccounts().values());
 }

*/



}


