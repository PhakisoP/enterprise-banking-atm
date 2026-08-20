package com.phakiso.atm.service;

import com.phakiso.atm.factory.CustomerFactory;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;
import com.phakiso.atm.repository.CustomerDatabaseRepository;

import java.util.List;

public class BankService {

    private final PersistenceService persistenceService =
            new PersistenceService();
    private final CustomerDatabaseRepository customerDatabaseRepository =
            new CustomerDatabaseRepository();
    private final CustomerFactory customerFactory =
            new CustomerFactory();


    public List<Customer> getAllCustomers() {

        return customerDatabaseRepository.getAllCustomers();
    }


    public boolean validateCustomerId(int customerId) {

        return !customerDatabaseRepository
                .customerIdExists(customerId);
    }


    public Customer createCustomer(
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

        customerDatabaseRepository.saveCustomer(customer);

        return customer;
    }


    public Customer findCustomer(int accountNumber) {

        return customerDatabaseRepository.findCustomerByAccountNumber(
                accountNumber
        );
    }


    public boolean deleteCustomer(int accountNumber) {

        return customerDatabaseRepository.deleteCustomer(
                accountNumber
        );
    }
}