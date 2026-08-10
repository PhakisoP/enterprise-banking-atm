package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

public class TestDataSeeder {

    private final TestDataService testDataService =
            new TestDataService();

    private final PersistenceService persistenceService =
            new PersistenceService();

    public void seed(
            CustomerRepository repository,
            int numberOfCustomers) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        TEST DATA SEEDER");
        System.out.println("========================================");

        System.out.println(
                "Generating "
                        + numberOfCustomers
                        + " customers..."
        );

        System.out.println();

        int startingCustomerId =
                getNextCustomerId(repository);

        for (int i = 0; i < numberOfCustomers; i++) {

            int customerId =
                    startingCustomerId + i;

            Customer customer =
                    testDataService.generateCustomer(
                            customerId
                    );

            repository.addCustomer(customer);

            System.out.println(
                    "[" + (i + 1) + "] "
                            + customer.getFirstName()
                            + " "
                            + customer.getLastName()
                            + " | Account: "
                            + customer.getAccount()
                            .getAccountNumber()
            );
        }

        persistenceService.save(repository);

        System.out.println();
        System.out.println(
                numberOfCustomers
                        + " customers generated successfully."
        );

        System.out.println("========================================");
    }

    private int getNextCustomerId(
            CustomerRepository repository) {

        int highestId = 0;

        for (Customer customer :
                repository.getCustomers()) {

            if (customer.getCustomerId()
                    > highestId) {

                highestId =
                        customer.getCustomerId();
            }
        }

        return highestId + 1;
    }
}