package com.lowes.bankingapp.service;

import com.lowes.bankingapp.model.Account;
import com.lowes.bankingapp.exception.AccountException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
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

    @Override
    public void createAccount(Account account) throws AccountException {

        Predicate<Account> create = account1 -> (account1.getName().length() > 2 && account1.getBalance() >= 100.0);
        Predicate<Account> create1 = account1 -> (account1.getType().equalsIgnoreCase("Deposit"))||(account1.getType().equalsIgnoreCase("Loan"))|| (account1.getType().equalsIgnoreCase("Savings"));


        if(account.getId()== 0) {
            System.out.println("No Account details");
            throw new AccountException("Account Creation Failed, Please Create Another Account");
        }

        boolean valStatus = create.test(account);
        boolean valStatus1 = create1.test(account);

        if (!valStatus) {
             System.out.println("Please check if Name contains atleast three characters and please have a minimum balance of Rs 100 ");
             throw new AccountException("Account Creation Failed, Please Create Another Account");
            }

       else if(!valStatus1)
        {
            throw new AccountException("Invalid Type,Please Choose Deposit,Loan or Savings");
        }
        else{
            accTreeMap.put(account.getId(), account);
            System.out.println( "Accounts created successfully");
           }

    }

    @Override
    public Collection<Account> getAccounts() {
        Supplier<List<Account>> getList = ()-> new ArrayList<>(accTreeMap.values());
        return getList.get();
    }

    @Override
    public Account getAccount(int id) throws AccountException {

        Account result = new Account();

        if(id == 0) {
            System.out.println("No Account details");
            throw new AccountException("Account Creation Failed, Please Create Another Account");
        }
        else {
            boolean isFound = false;
            if (accTreeMap.containsKey(id)) {
                result = accTreeMap.get(id);
                isFound = true;
            }
            if (!isFound) {
                throw new AccountException("Account not found!");
            }
        }
        return result;
    }

    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        Predicate<Account> update = account1 -> (account1.getName().length() > 2 && account1.getBalance() > 100.0);
        Predicate<Account> create1 = account1 -> (account1.getType().equalsIgnoreCase("Deposit"))||(account1.getType().equalsIgnoreCase("Loan"))|| (account1.getType().equalsIgnoreCase("Savings"));

        boolean valStatus = update.test(account);
        boolean valStatus1 = create1.test(account);
        boolean isFound = false;

        if((valStatus) && (valStatus1) == true) {
            for (int i : accTreeMap.keySet()) {
                if (accTreeMap.containsKey(id)) {
                    accTreeMap.put(id, account);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                throw new AccountException("Can't Update, no Account found");
            }
        }else{
            System.out.println("Check if Name contains atleast three characters,Type is Deposit,Loan orSavings and minimum balance is atleast Rs 100 ");
            throw new AccountException("Account Creation Failed, Please Create Another Account");
        }
    }

   @Override
   public boolean deleteAccount(int id) throws AccountException {
       boolean isDeleted = false;
       {
           if (accTreeMap.containsKey(id)) {
               accTreeMap.remove(id);
               isDeleted = true;
           }
           if (!isDeleted) {
               throw new AccountException("Can't Delete, No Account found");
           }
       }
       return isDeleted;
   }


    public long balanceAccount(double limit) {
        ArrayList<Account> balList = new ArrayList(accTreeMap.values());
         long count = 0;

         count =  balList
                .stream()
                .filter(account -> account.getBalance()>limit)
                .count();
        return count;
    }

    public Map<String, Long> getAccountsByType() {
        Map<String, Long> accountType =
                accTreeMap.values()
                        .stream()
                        .collect(Collectors.groupingBy(Account::getType, Collectors.counting()));
        System.out.println(accountType);
        return accountType;
    }

    public List<Map.Entry<String, Long>> getAccountsByTypeSorted() {
        Map<String, Long> accountTypeSort =
                accTreeMap.values()
                          .stream()
                          .sorted(Comparator.comparing(Account::getType))
                          .collect(Collectors.groupingBy(Account::getType, TreeMap::new, Collectors.counting()));

        List<Map.Entry<String, Long>> list = new ArrayList<>(accountTypeSort.entrySet());
        System.out.println(list);
        return list;
    }

    @Override
    public List<Map.Entry<String, Double>> getAverageBalanceByType() {
        Map<String,Double> avgbal = accTreeMap.values()
                .stream()
                .collect(Collectors.groupingBy(Account::getType,TreeMap::new,Collectors.averagingDouble(Account::getBalance)));
                 System.out.println(avgbal);
        List<Map.Entry<String, Double>> list = new ArrayList<>(avgbal.entrySet());
                 return list;
          }

    @Override
    public Set<Integer> getAccountIdsContainsName(String name) {
       // List<Integer> accids =
                accTreeMap.values()
                .stream()
                .filter(acc -> acc.getName().toLowerCase().contains(name.toLowerCase()))
                .map(Account::getId)
                .collect(Collectors.toList())
                .forEach(System.out::println);
        return accTreeMap.keySet();

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
            System.out.println(averageAge);
        }
        return averageAges;
    }

    @Override
    public synchronized Map<Integer, Account>  ImportData(String filePath) {
        try (Scanner in = new Scanner(new FileReader(filePath))) {
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] wordlist = line.split(",");
                int id = Integer.parseInt(wordlist[0].trim());
                String name = wordlist[1].trim();
                String type = wordlist[2].trim().toUpperCase();
                double balance = Double.parseDouble(wordlist[3].trim());
                boolean isActive = Boolean.parseBoolean(wordlist[4].trim());
                DateTimeFormatter dtf = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH);
                LocalDate createdDate = LocalDate.parse(wordlist[5].trim(), dtf);
                Account account = new Account(id, name, type, balance, isActive, createdDate);
                accTreeMap.put(id, account);
                Thread.sleep(3000);
            }
            System.out.format("%s Accounts data imported successfully.", Thread.currentThread().getName());

        } catch (Exception e) {
            System.out.println("Error occurred while importing. " + e.getMessage());
        }
        return accTreeMap;
    }

    @Override
    public void ExportData(String filePath, Map<Integer, Account> accMap) {
        try (FileWriter out = new FileWriter(filePath)) {
            if(accMap != null) { accTreeMap = accMap; }
            for (Map.Entry<Integer, Account> entry : accTreeMap.entrySet()) {
                int id = entry.getKey();
                Account account = entry.getValue();

                StringBuilder sb = new StringBuilder();
                sb.append(id).append(",");
                sb.append(account.getName()).append(",");
                sb.append(account.getType()).append(",");
                sb.append(account.getBalance()).append(",");
                sb.append(account.isActive());
                out.write(sb.toString() + "\n");
                Thread.sleep(3000);
            }
            out.flush();
            System.out.format("%s Account data exported successfully.", Thread.currentThread().getName());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error occurred while exporting. " + e.getMessage());
        }
    }
}