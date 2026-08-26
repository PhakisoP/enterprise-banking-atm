package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerDatabaseRepository;
import java.sql.SQLException;

public class DatabaseCustomerMigrationTest {

    public static void main(String[] args) throws SQLException {

        System.out.println();
        System.out.println("========================================");
        System.out.println(" CUSTOMER DATABASE MIGRATION TEST");
        System.out.println("========================================");

        BankAccount account =
                new BankAccount(
                        200010,
                        "Credit",
                        50000.00,
                        "2468"
                );

        Customer customer =
                new Customer(
                        10,
                        "Thatohatsi",
                        "Malaika",
                        "8106113041088",
                        "0609751164",
                        "ThatoM@testbank.co.za",
                        account
                );

        CustomerDatabaseRepository repository =
                new CustomerDatabaseRepository();

        repository.saveCustomer(customer);

        System.out.println();
        System.out.println("Migration test completed.");
    }
}