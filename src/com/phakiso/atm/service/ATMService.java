package com.phakiso.atm.service;


import com.phakiso.atm.model.Customer;
import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Transaction;
import java.util.Scanner;
import com.phakiso.atm.repository.CustomerRepository;

public class ATMService {


    public void checkBalance(Customer customer) {

        System.out.println();
        System.out.println("Current Balance : R"
                + customer.getAccount().getBalance());
    }
    public void deposit(Customer customer, double amount){

        customer.getAccount().deposit(amount);

    }
    public void withdraw(Customer customer, double amount) {

        customer.getAccount().withdraw(amount);
    }

    public void displayMiniStatement(Customer customer) {

        BankAccount account = customer.getAccount();

        System.out.println();
        System.out.println("==============================");
        System.out.println("      MINI STATEMENT");
        System.out.println("==============================");

        if (account.getTransactions().isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (Transaction transaction : account.getTransactions()) {

                System.out.printf(
                        "%-20s %-15s R%.2f%n",
                        transaction.getTransactionDate(),
                        transaction.getType(),
                        transaction.getAmount()
                );
            }
        }

        System.out.println("------------------------------");
        System.out.println("Current Balance : R" + account.getBalance());
        System.out.println("==============================");
    }

    public void changePin(Customer customer, Scanner scanner) {

        BankAccount account = customer.getAccount();

        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();

        if (!account.validatePin(currentPin)) {
            System.out.println("Incorrect current PIN.");
            return;
        }

        System.out.print("Enter new PIN: ");
        String newPin = scanner.next();

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.next();

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match.");
            return;
        }

        account.setPin(newPin);

        System.out.println("PIN changed successfully.");
    }
    public void transferMoney(Customer sender,
                              CustomerRepository repository,
                              int recipientAccountNumber,
                              double amount) {

        Customer recipient =
                repository.findCustomerByAccountNumberExcludingSender(
                        recipientAccountNumber,
                        sender
                );

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero.");
            return;
        }

        if (amount > sender.getAccount().getBalance()) {
            System.out.println("Insufficient funds.");
            return;
        }

        sender.getAccount().withdraw(amount);

        recipient.getAccount().deposit(amount);

        System.out.println();
        System.out.println("Transfer Successful!");
        System.out.println("------------------------------");
        System.out.println("From : " + sender.getFirstName());
        System.out.println("To   : " + recipient.getFirstName());
        System.out.println("Amount : R" + amount);
    }
}
