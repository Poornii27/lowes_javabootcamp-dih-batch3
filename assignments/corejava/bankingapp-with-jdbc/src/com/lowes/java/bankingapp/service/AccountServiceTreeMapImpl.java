package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.dao.AccountDao;
import com.lowes.java.bankingapp.dao.AccountDaoJDBCImpl;
import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.HashMap;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.Arrays.setAll;
import static java.util.Arrays.stream;


public class AccountServiceTreeMapImpl implements AccountService {
    //Using Comparator to perform Sorting
    Comparator<Integer> SORT_BY_ID_ASC = new Comparator<>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    };
    Comparator<Integer> SORT_BY_ID_DESC = new Comparator<>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    };

    Comparator<Map.Entry<String, Integer>> comp = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getKey().compareTo(o2.getKey());

        }
    };

    Map<Integer, Account> accTreeMap = new HashMap<>();

    AccountDao accountDao;

    public AccountServiceTreeMapImpl() throws SQLException {
        accountDao = new AccountDaoJDBCImpl();
    }

    @Override
    public boolean createAccount(Account account) throws AccountException, SQLException {
        Predicate<Account> create = account1 -> (account1.getName().length() > 2 && account1.getBalance() >= 100.0);
        boolean valStatus = create.test(account);

        if(valStatus){
            System.out.println( "Accounts created successfully");
            return accountDao.createAccount(account);
        }
        else
        {
            int count = account.getId();
            System.out.println("Please check if Name contains atleast three characters and please have a minimum balance of Rs 100 ");
            System.out.println("Account" +"\t"+ count +" " + "Creation Failed, Please Create Another Account");

        }
        return valStatus;
    }

    @Override
    public Collection<Account> getAccounts() throws SQLException {
        return accountDao.getAccounts();
    }

    @Override
    public Account getAccount(int id) throws AccountException, SQLException {
        return accountDao.getAccount(id);
    }

    @Override
    public boolean updateAccount(int id, Account account) throws AccountException, SQLException {
        Predicate<Account> update = account1 -> (account1.getName().length() > 2 && account1.getBalance() > 0.0);
        boolean valStatus = update.test(account);

        if(valStatus)
           {
               return accountDao.updateAccount(account.getId(), account);
           }
        else
            {
                throw new AccountException("Can't Update, no Account found");
            }
        }

    @Override
    public boolean deleteAccount(int id) throws AccountException, SQLException {
        return accountDao.deleteAccount(id);
    }


    public long balanceAccount(double limit) throws SQLException {
        long count = 0;

        count =  accountDao.getAccounts()
                .stream()
                .filter(account -> account.getBalance()>limit)
                .count();
        return count;
    }

    public Map<String, Long> getAccountsByType() throws SQLException {
        Map<String, Long> accountType =
                accountDao.getAccounts()
                        .stream()
                        .collect(Collectors.groupingBy(Account::getType, Collectors.counting()));
        System.out.println(accountType);
        return accountType;
    }

    public List<Map.Entry<String, Long>> getAccountsByTypeSorted() throws SQLException {
        Map<String, Long> accountTypeSort =
                accountDao.getAccounts()
                        .stream()
                        .sorted(Comparator.comparing(Account::getType))
                        .collect(Collectors.groupingBy(Account::getType, TreeMap::new, Collectors.counting()));

        List<Map.Entry<String, Long>> list = new ArrayList<>(accountTypeSort.entrySet());
        return list;
    }

    @Override
    public void getAverageBalanceByType() throws SQLException {
        Map<String,Double> avgbal = accountDao.getAccounts()
                .stream()
                .collect(Collectors.groupingBy(Account::getType,TreeMap::new,Collectors.averagingDouble(Account::getBalance)));
        System.out.println(avgbal);
    }

    @Override
    public void getAccountIdsContainsName(String name) throws SQLException {
        accountDao.getAccounts()
                .stream()
                .filter(acc -> acc.getName().toLowerCase().contains(name.toLowerCase()))
                .map(Account::getId)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public long findDifferenceInDays(LocalDate createDate) {
        return ChronoUnit.DAYS.between(createDate, LocalDate.now());
    }

    @Override
    public Map<String, Double> getAverageAgeOfAccounts() {
        Map<String, Double> averageAges = new HashMap<>();
        Map<String, Long> totalDays = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();

        for (Account account : accTreeMap.values()) {
            String accountType = account.getType();
            long daysSinceCreation = findDifferenceInDays(account.getCreatedDate());

            totalDays.put(accountType, totalDays.getOrDefault(accountType, 0L) + daysSinceCreation);
            counts.put(accountType, counts.getOrDefault(accountType, 0)+1);
        }
        for (String accountType : totalDays.keySet()) {
            long totalDaysForType = totalDays.get(accountType);
            int countForType = counts.get(accountType);
            double averageAge = (double)totalDaysForType/countForType;
            averageAges.put(accountType, averageAge);
        }
        return averageAges;
    }

    @Override
    public synchronized void ImportData() throws SQLException {
        try (Scanner in = new Scanner(new FileReader("./input/account.txt"))) {
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] wordlist = line.split(",");
                int id = Integer.parseInt(wordlist[0].trim());
                String name = wordlist[1].trim();
                String type = wordlist[2].trim();
                double balance = Double.parseDouble(wordlist[3].trim());
                boolean isActive = Boolean.parseBoolean(wordlist[4].trim());
                DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH);
                LocalDate createdDate = LocalDate.parse(wordlist[5].trim(), dtf);
                Account account = new Account(id, name, type, balance, isActive, createdDate);
                accountDao.createAccount(account);
                Thread.sleep(2000);
            }
            System.out.format("%s Accounts data imported successfully.", Thread.currentThread().getName());

        } catch (Exception e) {
            System.out.println("Error occurred while importing. " + e.getMessage());
        }
    }

    @Override
    public void ExportData() throws SQLException {
        try (FileWriter out = new FileWriter("./output/account-out.txt")) {
            accountDao
                    .getAccounts()
                    .stream()
                    .map(account -> account.getId()+"\t"+account.getName()+"\t"+account.getType()+"\t"
                            +account.getBalance()+"\t"+account.isActive()+"\t"+account.getCreatedDate()+"\t"+account.getUpdatedDate())
                    .forEach(accs-> {
                                try {
                                    out.write(accs + "\n");
                                    Thread.sleep(2000);
                                } catch (IOException | InterruptedException e) {
                                    System.out.println("Error occurred while exporting. " + e.getMessage());
                                }
                            });
            out.flush();
            System.out.format("%s Account data exported successfully.", Thread.currentThread().getName());
        } catch (IOException | SQLException e) {
            System.out.println("Error occurred while exporting. " + e.getMessage());
        }
    }
}