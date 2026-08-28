package com.phakiso.atm.model;

import java.io.Serializable;

/**
 * Represents a customer's bank account.
 *
 * The BankAccount model stores account information and provides
 * basic account-related behaviour such as PIN validation and
 * in-memory balance synchronization.
 *
 * Database persistence is handled by the repository and service
 * layers rather than by this model.
 */
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public void setPin(String pin) {
        this.pin = pin;
    }

    // ============================================================
    // INTERNAL BALANCE SYNCHRONIZATION
    //
    // These methods update the in-memory account balance only.
    // Database persistence is handled by the service/repository
    // layers.
    // ============================================================

    /**
     * Increases the in-memory balance without creating a
     * transaction or displaying a message.
     *
     * This is used after a successful database transaction.
     *
     * @param amount amount to add to the balance
     */
    public void increaseBalanceSilently(double amount) {

        if (amount <= 0) {
            return;
        }

        balance += amount;
    }

    /**
     * Decreases the in-memory balance without creating a
     * transaction or displaying a message.
     *
     * This is used after a successful database transaction.
     *
     * @param amount amount to subtract from the balance
     */
    public void decreaseBalanceSilently(double amount) {

        if (amount <= 0) {
            return;
        }

        balance -= amount;
    }

    /**
     * Validates a PIN against the account's current PIN.
     *
     * @param enteredPin PIN entered by the customer
     * @return true when the supplied PIN matches the account PIN
     */
    public boolean validatePin(String enteredPin) {

        return pin.equals(enteredPin);
    }
}