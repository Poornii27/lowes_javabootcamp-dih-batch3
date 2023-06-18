package com.lowes.java.bankingapp.service;

import com.lowes.java.bankingapp.exception.AccountException;
import com.lowes.java.bankingapp.model.Account;

import java.io.*;
import java.util.*;

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

    Map<Integer, Account> accTreeMap = new TreeMap<>(SORT_BY_ID_ASC);

    /*Storing the account details as Key-Value pairs using put() method*/
    @Override
    public void createAccount(Account account) throws AccountException {
        accTreeMap.put(account.getId(), account);
    }

    /* Displaying the Key-Value collections*/
    @Override
    public Collection<Account> getAccounts() {
        return accTreeMap.values();
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
        boolean isFound = false;
        for (int i : accTreeMap.keySet()) {
            if (accTreeMap.containsKey(id)) {
                accTreeMap.put(id, account);
                System.out.println(accTreeMap.get(id));
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new AccountException("Can't Update, no Account found");
        }
    }

    /* Delete an account using remove() method*/
    @Override
    public void deleteAccount(int id) throws AccountException {
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
    }

    public int balanceAccount(double limit) {
        ArrayList<Account> balList = new ArrayList<Account>(accTreeMap.values());
        int count = 0;
        for (Account acc : accTreeMap.values()) {
            if (acc.getBalance() > limit) {
                count++;
            }
        }
        return count;
    }

    public TreeMap<String, Integer> getAccountsByType() {
        TreeMap<String, Integer> accountType = new TreeMap<>();

        for (Map.Entry<Integer, Account> entry : accTreeMap.entrySet()) {
            Account accType = entry.getValue();

            if (accountType.containsKey(accType.getType())) {
                accountType.put(accType.getType(), accountType.get(accType.getType()) + 1);
            } else {
                accountType.put(accType.getType(), 1);
            }
        }
        return accountType;
    }

    public List<Map.Entry<String, Integer>> getAccountsByTypeSorted() {
        TreeMap<String, Integer> accountType = new TreeMap<>();

        for (Map.Entry<Integer, Account> entry : accTreeMap.entrySet()) {
            Account accType = entry.getValue();

            if (accountType.containsKey(accType.getType())) {
                accountType.put(accType.getType(), accountType.get(accType.getType()) + 1);
            } else {
                accountType.put(accType.getType(), 1);
            }
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(accountType.entrySet());
        Collections.sort(list, comp);

        TreeMap<String, Integer> sortedAccounts = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedAccounts.put(entry.getKey(), entry.getValue());
        }
        return list;
    }

    @Override
    public void getAverageBalanceByType() {
        TreeMap<String, List<Double>> accBalanceByType = new TreeMap<>();
        TreeMap<String, Double> averageAccountBalances = new TreeMap<>();

        double avgBal = 0.0;

        for (Map.Entry<Integer, Account> entry : accTreeMap.entrySet()) {
            Account acc = entry.getValue();
            String accType = acc.getType();
            double accBalance = acc.getBalance();

            List<Double> accBalances = accBalanceByType.getOrDefault(accType, new ArrayList<>());
            accBalances.add(accBalance);
            accBalanceByType.put(accType, accBalances);
        }

        for (Map.Entry<String, List<Double>> entry : accBalanceByType.entrySet()) {
            String accType = entry.getKey();
            double sum = 0.0;
            List<Double> accBalances = entry.getValue();
            for (double bal : accBalances) {
                sum += bal;
            }
            avgBal = sum / accBalances.size();
            averageAccountBalances.put(accType, avgBal);
        }
        for (Map.Entry<String, Double> entry : averageAccountBalances.entrySet()) {
            String at = entry.getKey();
            double ab = entry.getValue();
            System.out.println("Average Balance for " + at + " is: " + ab);
        }
    }

    @Override
    public void getAccountIdsContainsName(String name) {
        TreeMap<Integer, String> accIdByName = new TreeMap<>();
        for (Map.Entry<Integer, Account> entry : accTreeMap.entrySet()) {
            Account acc = entry.getValue();
            int accId = acc.getId();
            String accName = acc.getName();

            if (accName.contains(name)) {
                accIdByName.put(accId, accName);
            }
        }
        if (accIdByName == null || accIdByName.size() == 0) {
            System.out.println("No accounts found containing the name " + name);
        } else {
            for (Map.Entry<Integer, String> entry : accIdByName.entrySet()) {
                int ai = entry.getKey();
                String an = entry.getValue();
                System.out.println("The Account ID is " + ai + " for the name: " + an);
            }
        }
    }

    @Override
    public void ImportData() throws FileNotFoundException {
        Scanner in = new Scanner(new File("./input/account.txt"));
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
        }
        System.out.println("Account data imported Successfully");
    }

    @Override
    public void ExportData() throws IOException {
        FileWriter out = new FileWriter("./output/account-out.txt");
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
        }
        out.flush();
        System.out.println("The data has been exported!");
    }
}