package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.AccountDatabaseRepository;
import com.phakiso.atm.repository.CustomerDatabaseRepository;
import java.sql.SQLException;

import java.util.Scanner;

public class AuthenticationService {

    private final CustomerDatabaseRepository customerDatabaseRepository =
            new CustomerDatabaseRepository();

    private final AccountDatabaseRepository accountDatabaseRepository =
            new AccountDatabaseRepository();

    private final Scanner scanner;

    public AuthenticationService(Scanner scanner) {
        this.scanner = scanner;
    }


    public boolean authenticate(Customer customer) {

        int accountNumber =
                customer.getAccount().getAccountNumber();

        if (accountDatabaseRepository.isAccountLocked(
                accountNumber)) {

            System.out.println();
            System.out.println(
                    "This account is locked."
            );

            System.out.println(
                    "Please contact the bank."
            );

            return false;
        }


        int attempts = 3;

        while (attempts > 0) {

            System.out.print("Enter PIN: ");

            String enteredPin =
                    scanner.next();


            // ======================================
            // CORRECT PIN
            // ======================================

            if (customer.getAccount().validatePin(
                    enteredPin)) {

                accountDatabaseRepository.resetLoginAttempts(
                        accountNumber
                );

                System.out.println();
                System.out.println(
                        "Access Granted."
                );

                return true;
            }


            // ======================================
            // INCORRECT PIN
            // ======================================

            attempts--;

            int failedAttempts =
                    3 - attempts;

            accountDatabaseRepository.updateFailedAttempts(
                    accountNumber,
                    failedAttempts
            );

            System.out.println();
            System.out.println(
                    "Incorrect PIN."
            );


            // ======================================
            // ATTEMPTS REMAINING
            // ======================================

            if (attempts > 0) {

                System.out.println(
                        "Attempts Remaining : "
                                + attempts
                );

                System.out.println();

            }


            // ======================================
            // LOCK ACCOUNT
            // ======================================

            else {

                accountDatabaseRepository.lockAccount(
                        accountNumber
                );

                System.out.println();
                System.out.println(
                        "Card Blocked."
                );

                System.out.println(
                        "Please contact the bank."
                );

                return false;
            }
        }

        return false;
    }


    public Customer login()
            throws SQLException {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Customer customer =
                customerDatabaseRepository
                        .findCustomerByAccountNumber(
                                accountNumber
                        );

        if (customer == null) {

            System.out.println();
            System.out.println(
                    "Account not found."
            );

            return null;
        }

        if (authenticate(customer)) {
            return customer;
        }

        return null;
    }
}