package com.phakiso.atm.app;

import com.phakiso.atm.repository.AccountDatabaseRepository;

public class AccountBalanceDatabaseTest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" ACCOUNT BALANCE DATABASE TEST");
        System.out.println("========================================");

        AccountDatabaseRepository repository =
                new AccountDatabaseRepository();

        boolean success =
                repository.updateBalance(
                        200010,
                        49500.00
                );

        if (success) {

            System.out.println();
            System.out.println(
                    "Account balance updated successfully."
            );

        } else {

            System.out.println();
            System.out.println(
                    "Account balance update failed."
            );
        }

        System.out.println();
        System.out.println("Test completed.");
    }
}
