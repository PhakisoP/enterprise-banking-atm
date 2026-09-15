package com.phakiso.atm.app;

import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerDatabaseRepository;

import java.sql.SQLException;

public class DatabaseCustomerReadTest {

    public static void main(String[] args)
            throws SQLException {

        CustomerDatabaseRepository repository =
                new CustomerDatabaseRepository();

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