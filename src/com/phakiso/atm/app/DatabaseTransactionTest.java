package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.repository.TransactionDatabaseRepository;
import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;

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

        account.increaseBalanceSilently(1000.00);

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            repository.saveTransaction(
                    connection,
                    account,
                    "Deposit",
                    1000.00,
                    account.getBalance()
            );

            System.out.println();
            System.out.println("Test completed.");

        } catch (Exception e) {

            System.out.println(
                    "Transaction database test failed."
            );

            e.printStackTrace();
        }
    }
}