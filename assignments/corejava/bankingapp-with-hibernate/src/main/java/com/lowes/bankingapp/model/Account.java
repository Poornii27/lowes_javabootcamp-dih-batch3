package com.lowes.bankingapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Optional;


import javax.persistence.*;

@Entity
@Table
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;

    private String name;
    private String type;
    private double balance;
    private boolean active;

    private LocalDate createdDate;

    @Column(nullable = true)
    private LocalDate updateDate;

   /* @Column(nullable = true)
    private Optional<LocalDate> updatedDate;*/

    public Account() {
       // updatedDate = Optional.empty();
    }

    public Account(int id, String name, String type, double balance, boolean active, LocalDate createdDate, LocalDate updateDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.active = active;
        this.createdDate =  createdDate;
        this.updateDate = updateDate;
        // this.updatedDate = Optional.ofNullable(updatedDate);
       // this.updatedDate = this.updateDate;

    }

    public Account (int id, String name, String type, double balance, boolean active, String createdDate, String updateDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.active = active;
        this.createdDate = LocalDate.parse(createdDate);
        this.updateDate = LocalDate.parse(updateDate);
        // this.updatedDate = Optional.ofNullable(updatedDate);
        // this.updatedDate = this.updateDate;

    }

    public Account(int id, String name, String type, double balance, boolean active, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.active = active;
        this.createdDate = createdDate;

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
    /*public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = Optional.ofNullable(updatedDate);
    }*/

    public void setUpdatedDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
   /* public Optional<LocalDate> getUpdatedDate() {
        return updatedDate;
    }*/

    public LocalDate getUpdatedDate() {
        return updateDate;
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
        this.type = String.valueOf(type);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                ", created on=" + createdDate.format(new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH)) +
                ", updated on=" + updateDate +
               /* ", updated on=" + updatedDate.orElse(null) +*/
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
