package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.repository.TransactionDatabaseRepository;

public class DatabaseTransactionTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" TRANSACTION DATABASE TEST");
        System.out.println("=================================");

        BankAccount account =
                new BankAccount(
                        888888,
                        "Savings",
                        7500.00,
                        "2468"
                );

        TransactionDatabaseRepository repository =
                new TransactionDatabaseRepository();

        System.out.println();
        System.out.println("Saving deposit transaction...");

        // Simulate a deposit
        account.deposit(1000.00);

        repository.saveTransaction(
                account,
                "Deposit",
                1000.00
        );

        System.out.println();
        System.out.println("Test completed.");
    }
}