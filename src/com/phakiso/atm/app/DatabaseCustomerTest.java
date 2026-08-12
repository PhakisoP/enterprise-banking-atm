package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerDatabaseRepository;

public class DatabaseCustomerTest {

    public static void main(String[] args) {

        BankAccount account =
                new BankAccount(
                        999999,
                        "Savings",
                        5000.00,
                        "2468"
                );

        Customer customer =
                new Customer(
                        999999,
                        "Test",
                        "Customer",
                        "9001015009087",
                        "0712345678",
                        "test.customer@testbank.co.za",
                        account
                );

        CustomerDatabaseRepository repository =
                new CustomerDatabaseRepository();

        repository.saveCustomer(customer);

        System.out.println();
        System.out.println(
                "Database customer test completed."
        );
    }
}