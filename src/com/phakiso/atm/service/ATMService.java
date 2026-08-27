package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import java.sql.SQLException;


import java.util.Scanner;

public class ATMService {

    private final TransactionService transactionService;
    private final AccountService accountService =
            new AccountService();
    private final ValidationService validationService =
            new ValidationService();

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
            Scanner scanner)
            throws SQLException {

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
                recipientAccountNumber,
                amount
        );
    }


    // ==========================================
    // CHANGE PIN
    // ==========================================


    public void changePin(
            Customer customer,
            Scanner scanner)
            throws SQLException {

        BankAccount account =
                customer.getAccount();

        System.out.print(
                "Enter current PIN: "
        );

        String currentPin =
                scanner.next();

        // ==========================================
        // VERIFY CURRENT PIN
        // ==========================================

        if (!account.validatePin(currentPin)) {

            System.out.println(
                    "Incorrect current PIN."
            );

            return;
        }

        // ==========================================
        // ENTER NEW PIN
        // ==========================================

        System.out.print(
                "Enter new PIN: "
        );

        String newPin =
                scanner.next();

        // ==========================================
        // VALIDATE PIN FORMAT
        // ==========================================

        if (!validationService.isValidPin(newPin)) {
            return;
        }

        // ==========================================
        // CONFIRM NEW PIN
        // ==========================================

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

        // ==========================================
        // UPDATE DATABASE
        // ==========================================

        boolean updated =
                accountService.updatePin(
                        account.getAccountNumber(),
                        newPin
                );

        if (!updated) {

            System.out.println();

            System.out.println(
                    "PIN change failed."
            );

            System.out.println(
                    "Database was not updated."
            );

            return;
        }

        // ==========================================
        // UPDATE JAVA OBJECT
        // ==========================================

        account.setPin(newPin);

        System.out.println();

        System.out.println(
                "================================"
        );

        System.out.println(
                "       PIN CHANGE SUCCESSFUL"
        );

        System.out.println(
                "================================"
        );

        System.out.println(
                "PIN updated successfully."
        );

        System.out.println(
                "Your new PIN is now active."
        );

        System.out.println(
                "================================"
        );
    }
    }

