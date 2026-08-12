package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

import java.util.Scanner;

public class ATMService {

    private final TransactionService transactionService;

    public ATMService() {

        transactionService =
                new TransactionService();
    }


    // ==========================================
    // CHECK BALANCE
    // ==========================================

    public void checkBalance(Customer customer) {

        System.out.println();

        System.out.println(
                "Current Balance : R"
                        + customer.getAccount().getBalance()
        );
    }


    // ==========================================
    // DEPOSIT
    // ==========================================

    public void deposit(
            Customer customer,
            Scanner scanner) {

        System.out.print(
                "Enter deposit amount: R"
        );

        double amount =
                scanner.nextDouble();

        transactionService.deposit(
                customer,
                amount
        );
    }


    // ==========================================
    // WITHDRAW
    // ==========================================

    public void withdraw(
            Customer customer,
            Scanner scanner) {

        System.out.print(
                "Enter withdrawal amount: R"
        );

        double amount =
                scanner.nextDouble();

        transactionService.withdraw(
                customer,
                amount
        );
    }


    // ==========================================
    // MINI STATEMENT
    // ==========================================

    public void miniStatement(
            Customer customer) {

        transactionService.displayMiniStatement(
                customer
        );
    }


    // ==========================================
    // TRANSFER
    // ==========================================

    public void transferMoney(
            Customer sender,
            CustomerRepository repository,
            Scanner scanner) {

        System.out.print(
                "Recipient Account Number: "
        );

        int recipientAccountNumber =
                scanner.nextInt();

        System.out.print(
                "Amount: R"
        );

        double amount =
                scanner.nextDouble();

        transactionService.transferMoney(
                sender,
                repository,
                recipientAccountNumber,
                amount
        );
    }


    // ==========================================
    // CHANGE PIN
    // ==========================================

    public void changePin(
            Customer customer,
            Scanner scanner) {

        BankAccount account =
                customer.getAccount();

        System.out.print(
                "Enter current PIN: "
        );

        String currentPin =
                scanner.next();

        if (!account.validatePin(currentPin)) {

            System.out.println(
                    "Incorrect current PIN."
            );

            return;
        }

        System.out.print(
                "Enter new PIN: "
        );

        String newPin =
                scanner.next();

        System.out.print(
                "Confirm new PIN: "
        );

        String confirmPin =
                scanner.next();

        if (!newPin.equals(confirmPin)) {

            System.out.println(
                    "PINs do not match."
            );

            return;
        }

        if (!newPin.matches("\\d{4}")) {

            System.out.println(
                    "PIN must contain exactly 4 digits."
            );

            return;
        }

        account.setPin(newPin);

        System.out.println(
                "PIN changed successfully."
        );
    }
}