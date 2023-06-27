package com.lowes.java.bankingapp.model;

import java.time.LocalDate;

public class Account{
    private int id;
    private String name;
    private String type;
    private double balance;
    private boolean active;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public Account() {

    }

    public Account(int id, String name, String type, double balance, boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public  LocalDate getUpdatedDate() {
        return  updatedDate;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Account) {
            if(this.getId() == ((Account) o).getId()) {
                return true;
            }
        } return false;
    }

    @Override
    public int hashCode() {
        return this.getId()%2;
    }

}
