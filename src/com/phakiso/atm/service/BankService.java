package com.phakiso.atm.service;

import com.phakiso.atm.factory.CustomerFactory;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;
import java.util.List;

public class BankService {

    private final PersistenceService persistenceService =
            new PersistenceService();
    private final CustomerFactory customerFactory =
            new CustomerFactory();


    public List<Customer> getAllCustomers(
            CustomerRepository repository) {

        return repository.getCustomers();
    }

    public boolean validateCustomerId(
            int customerID,
            CustomerRepository repository) {

        return !repository.customerIDExists(customerID);
    }


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

    public Customer findCustomer(
            CustomerRepository repository,
            int accountNumber) {

        return repository.findCustomerByAccountNumber(accountNumber);
    }
    public boolean deleteCustomer(
            CustomerRepository repository,
            int accountNumber) {
        boolean deleted = repository.deleteCustomer(accountNumber);
        if (deleted) {
            persistenceService.save(repository);
        }
        return deleted;
        }
    }