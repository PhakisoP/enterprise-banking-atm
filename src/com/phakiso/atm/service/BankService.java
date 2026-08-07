package com.phakiso.atm.service;

import com.phakiso.atm.factory.CustomerFactory;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

public class BankService {

    private final ValidationService validationService =
            new ValidationService();

    private final PersistenceService persistenceService =
            new PersistenceService();

    private final CustomerFactory customerFactory =
            new CustomerFactory();

    public Customer createCustomer(
            CustomerRepository repository,
            int customerID,
            String firstName,
            String lastName,
            String idNumber,
            String phoneNumber,
            String email,
            int accountNumber,
            String accountType,
            double openingBalance,
            String pin) {

        Customer customer = customerFactory.createCustomer(
                customerID,
                firstName,
                lastName,
                idNumber,
                phoneNumber,
                email,
                accountNumber,
                accountType,
                openingBalance,
                pin
        );

        repository.addCustomer(customer);

        persistenceService.save(repository);

        return customer;
    }
}