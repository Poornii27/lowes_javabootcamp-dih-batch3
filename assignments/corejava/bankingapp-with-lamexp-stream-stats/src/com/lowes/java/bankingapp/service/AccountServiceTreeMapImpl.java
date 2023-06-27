package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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

    /*Storing the account details as Key-Value pairs using put() method*/
    @Override
    public void createAccount(Account account) throws AccountException {
        Predicate<Account> create = account1 -> (account1.getName().length() > 2 && account1.getBalance() > 0.0);
        boolean valStatus = create.test(account);

        if(valStatus){
            accTreeMap.put(account.getId(),account);
            System.out.println("Accounts created successfully, Select an option to proceed");
        }
        else {
            Consumer<String> print = msg-> System.out.println(msg);
            print.accept("Invalid Name or Balance entered. Please choose again!!!");
        }

    }

    /* Displaying the Key-Value collections*/
    @Override
    public Collection<Account> getAccounts() {
        Supplier<List<Account>> getList = ()-> new ArrayList<Account>(accTreeMap.values());
        return getList.get();
    }

    /* Checking for the matching id using contains() method and displaying the Key-Value pairs */
    @Override
    public Account getAccount(int id) throws AccountException {
        boolean isFound = false;
        Account result = new Account();
        if (accTreeMap.containsKey(id)) {
            result = accTreeMap.get(id);
            isFound = true;
        }
        if (!isFound) {
            throw new AccountException("Account not found!");
        }
        return result;
    }

    /*Updates Account for the id contained in the key-set using put () method*/
    @Override
    public void updateAccount(int id, Account account) throws AccountException {
        Predicate<Account> update = account1 -> (account1.getName().length() > 2 && account1.getBalance() > 0.0);
        boolean valStatus = update.test(account);

        boolean isFound = false;

        if(valStatus) {
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
        }
    }

    /* Delete an account using remove() method*/
    @Override
    public boolean deleteAccount(int id) throws AccountException {
        return accTreeMap.remove(id) != null ? true : false;
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
                          .sorted(Comparator.comparing(Account::getType))//if v  want to sort based on dept
                          .collect(Collectors.groupingBy(Account::getType, TreeMap::new, Collectors.counting()));//grouping based on dept nd finding count

        List<Map.Entry<String, Long>> list = new ArrayList<>(accountTypeSort.entrySet());
        return list;
    }

    @Override
    public void getAverageBalanceByType() {
        Map<String,Double> avgbal = accTreeMap.values()
                .stream()
                .collect(Collectors.groupingBy(Account::getType,TreeMap::new,Collectors.averagingDouble(Account::getBalance)));
                 System.out.println(avgbal);
          }

    @Override
    public void getAccountIdsContainsName(String name) {
        accTreeMap.values()
                .stream()
                .filter(acc -> acc.getName().toLowerCase().contains(name.toLowerCase()))
                .map(Account::getId)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @Override
    public synchronized void ImportData() {
        try (Scanner in = new Scanner(new FileReader("./input/account.txt"))) {
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] wordlist = line.split(",");
                int id = Integer.parseInt(wordlist[0].trim());
                String name = wordlist[1].trim();
                String type = wordlist[2].trim();
                double balance = Double.parseDouble(wordlist[3].trim());
                boolean isActive = Boolean.parseBoolean(wordlist[4].trim());
                Account account = new Account(id, name, type, balance, isActive);
                accTreeMap.put(id, account);
                Thread.sleep(3000);
            }
            System.out.format("%s Accounts data imported successfully.", Thread.currentThread().getName());

        } catch (Exception e) {
            System.out.println("Error occurred while importing. " + e.getMessage());
        }
    }

    @Override
    public void ExportData() {
        try (FileWriter out = new FileWriter("./output/account-out.txt")) {
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