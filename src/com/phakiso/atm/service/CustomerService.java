package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;
import java.util.Scanner;
import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Transaction;

public class CustomerService {

    private Scanner scanner = new Scanner(System.in);

    public boolean authenticate(Customer customer) {

        int attempts = 3;

        while (attempts > 0) {

            System.out.print("Enter PIN: ");

            String enteredPin = scanner.next();

            if (customer.getAccount().validatePin(enteredPin)) {

                System.out.println();
                System.out.println("Access Granted.");
                return true;

            }

            attempts--;

            System.out.println("Incorrect PIN.");

            if (attempts > 0) {
                System.out.println("Attempts Remaining : " + attempts);
            }

            System.out.println();

        }

        System.out.println("Card Blocked.");
        return false;
    }

    public void displayCustomer(Customer customer) {

        System.out.println("--------------------------------");
        System.out.println("Customer ID : " + customer.getCustomerId());
        System.out.println("Name : " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("ID Number : " + customer.getIdNumber());

        System.out.println();

        System.out.println("Account Number : " +
                customer.getAccount().getAccountNumber());

        System.out.println("Account Type : " +
                customer.getAccount().getAccountType());

        System.out.println("Balance : R" +
                customer.getAccount().getBalance());

    }

    public void displayMenu(Customer customer) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==============================");
            System.out.println(" Enterprise Banking ATM");
            System.out.println("==============================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit");
            System.out.println("==============================");

            System.out.print("Choose option: ");

            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    System.out.println();
                    System.out.println("Current Balance : R"
                            + customer.getAccount().getBalance());
                    break;

                case 2:
                    System.out.print("Enter deposit amount: R");
                    double depositAmount = scanner.nextDouble();
                    customer.getAccount().deposit(depositAmount);
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: R");
                    double withdrawalAmount = scanner.nextDouble();
                    customer.getAccount().withdraw(withdrawalAmount);
                    break;

                case 4:
                    displayMiniStatement(customer.getAccount());
                    break;

                case 5:
                    changePin(customer);
                    break;

                case 6:
                    System.out.println();
                    System.out.println("Thank you for using Enterprise Banking ATM.");
                    running = false;
                    break;
            }
        }
    }
    public void displayMiniStatement(BankAccount account) {

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

    public void changePin(Customer customer) {

        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();

        if (!customer.getAccount().validatePin(currentPin)) {
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

        customer.getAccount().setPin(newPin);

        System.out.println("PIN changed successfully.");
    }

}