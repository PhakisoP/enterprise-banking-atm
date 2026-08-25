package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerDatabaseRepository;
import java.sql.SQLException;

public class DatabaseCustomerReadTest {

    public static void main(String[] args)
            throws SQLException {

        CustomerDatabaseRepository repository =
                new CustomerDatabaseRepository();


        // ========================================================
        // CREATE TEST CUSTOMER
        // ========================================================

        BankAccount account =
                new BankAccount(
                        888888,
                        "Savings",
                        7500.00,
                        "1357"
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


        // ========================================================
        // SAVE CUSTOMER
        // ========================================================

        System.out.println(
                "Saving customer..."
        );

        repository.saveCustomer(customer);


        // ========================================================
        // READ CUSTOMER BY ID
        // ========================================================

        System.out.println();
        System.out.println(
                "Searching for customer..."
        );

        Customer foundCustomer =
                repository.findCustomerById(888888);


        // ========================================================
        // DISPLAY RESULT
        // ========================================================

        if (foundCustomer != null) {

            System.out.println();
            System.out.println(
                    "Customer found successfully!"
            );

            System.out.println(
                    "Customer ID : "
                            + foundCustomer.getCustomerId()
            );

            System.out.println(
                    "Name        : "
                            + foundCustomer.getFirstName()
                            + " "
                            + foundCustomer.getLastName()
            );

            System.out.println(
                    "ID Number   : "
                            + foundCustomer.getIdNumber()
            );

            System.out.println(
                    "Phone       : "
                            + foundCustomer.getPhoneNumber()
            );

            System.out.println(
                    "Email       : "
                            + foundCustomer.getEmail()
            );

            System.out.println(
                    "Account     : "
                            + foundCustomer.getAccount()
                            .getAccountNumber()
            );

            System.out.println(
                    "Account Type: "
                            + foundCustomer.getAccount()
                            .getAccountType()
            );

            System.out.println(
                    "Balance     : R"
                            + foundCustomer.getAccount()
                            .getBalance()
            );

        } else {

            System.out.println(
                    "Customer was not found."
            );
        }


        // ========================================================
        // READ CUSTOMER BY ACCOUNT NUMBER
        // ========================================================

        System.out.println();
        System.out.println(
                "Searching by account number..."
        );

        Customer accountCustomer =
                repository.findCustomerByAccountNumber(888888);


        if (accountCustomer != null) {

            System.out.println(
                    "Account owner found: "
                            + accountCustomer.getFirstName()
                            + " "
                            + accountCustomer.getLastName()
            );

        } else {

            System.out.println(
                    "Account owner was not found."
            );
        }
    }
}