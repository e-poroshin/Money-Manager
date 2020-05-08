package com.eugene_poroshin.money_manager.repo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "accounts")
public class AccountEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private int id;
    @ColumnInfo(name = "account_name")
    private String name;
    @ColumnInfo(name = "account_balance")
    private double balance;
    @ColumnInfo(name = "account_currency")
    private String currency;

    public AccountEntity(String name, double balance, String currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
