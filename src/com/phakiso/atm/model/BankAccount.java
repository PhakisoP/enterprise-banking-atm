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

    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        balance += amount;

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

        System.out.println("Withdrawal Successful!");
        System.out.println("Amount Withdrawn : R" + amount);
        System.out.println("New Balance      : R" + balance);
    }
    public boolean validatePin(String enteredPin) {

        return pin.equals(enteredPin);

    }
}