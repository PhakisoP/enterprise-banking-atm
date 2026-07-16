package com.phakiso.atm.model;

public class BankAccount {

    private int accountNumber;
    private String accountType;
    private double balance;
    private String pin;

    public BankAccount(int accountNumber,
                       String accountType,
                       double balance,
                       String pin) {

        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.pin = pin;

    }
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }
    }