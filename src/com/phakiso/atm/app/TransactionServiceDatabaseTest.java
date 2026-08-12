package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.service.TransactionService;

public class TransactionServiceDatabaseTest {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("========================================");
        System.out.println(" TRANSACTION SERVICE DATABASE TEST");
        System.out.println("========================================");

        BankAccount account =
                new BankAccount(
                        888888,
                        "Savings",
                        7500.00,
                        "2468"
                );

        Customer customer =
                new Customer(
                        888888,
                        "Phakiso",
                        "Test",
                        "9001015009088",
                        "0712345679",
                        "phakiso.test@testbank.co.za",
                        account
                );

        TransactionService transactionService =
                new TransactionService();


        // ==========================================
        // TEST DEPOSIT
        // ==========================================

        System.out.println();
        System.out.println("Testing deposit...");

        transactionService.deposit(
                customer,
                1000.00
        );


        // ==========================================
        // TEST WITHDRAWAL
        // ==========================================

        System.out.println();
        System.out.println("Testing withdrawal...");

        transactionService.withdraw(
                customer,
                500.00
        );


        // ==========================================
        // DISPLAY FINAL BALANCE
        // ==========================================

        System.out.println();
        System.out.println("========================================");
        System.out.println(" FINAL ACCOUNT STATE");
        System.out.println("========================================");

        System.out.println(
                "Account Number : " +
                        account.getAccountNumber()
        );

        System.out.println(
                "Final Balance  : R" +
                        account.getBalance()
        );

        System.out.println();
        System.out.println("Test completed.");
    }
}