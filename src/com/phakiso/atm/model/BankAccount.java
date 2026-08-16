package com.phakiso.atm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private int accountNumber;
    private String accountType;
    private double balance;
    private String pin;

    private int failedAttempts;
    private boolean locked;

    private List<Transaction> transactions = new ArrayList<>();

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


    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void incrementFailedAttempts() {
        failedAttempts++;
    }

    public void resetFailedAttempts() {
        failedAttempts = 0;
    }

    public void lockAccount() {
        locked = true;
    }

    public void unlockAccount() {
        locked = false;
        failedAttempts = 0;
    }


    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        balance += amount;

        transactions.add(new Transaction("Deposit", amount));

        System.out.println("Deposit Successful!");
        System.out.println("Amount Deposited : R" + amount);
        System.out.println("New Balance      : R" + balance);

    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }

        balance -= amount;

        transactions.add(new Transaction("Withdrawal", amount));

        System.out.println("Withdrawal Successful!");
        System.out.println("Amount Withdrawn : R" + amount);
        System.out.println("New Balance      : R" + balance);
    }

    // ============================================================
    // INTERNAL BALANCE SYNCHRONIZATION
    // Used by transfers after the database transaction commits.
    // These methods do not print messages or create transactions.
    // ============================================================

    public void increaseBalanceSilently(double amount) {

        if (amount <= 0) {
            return;
        }

        balance += amount;
    }


    public void decreaseBalanceSilently(double amount) {

        if (amount <= 0) {
            return;
        }

        balance -= amount;
    }

    public boolean validatePin(String enteredPin) {

        return pin.equals(enteredPin);

    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
}