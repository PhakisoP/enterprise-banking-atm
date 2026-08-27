package com.phakiso.atm.service;

import com.phakiso.atm.factory.CustomerFactory;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerDatabaseRepository;
import java.sql.SQLException;

import java.util.List;


public class BankService {

    private final CustomerDatabaseRepository customerDatabaseRepository =
            new CustomerDatabaseRepository();

    private final CustomerFactory customerFactory =
            new CustomerFactory();


    public List<Customer> getAllCustomers()
            throws SQLException {

        return customerDatabaseRepository.getAllCustomers();
    }


    public boolean customerIdExists(int customerId)
            throws SQLException {

        return customerDatabaseRepository.customerIdExists(
                customerId
        );
    }


    public boolean idNumberExists(String idNumber)
            throws SQLException {

        return customerDatabaseRepository.idNumberExists(
                idNumber
        );
    }


    public boolean phoneNumberExists(String phoneNumber)
            throws SQLException {

        return customerDatabaseRepository.phoneNumberExists(
                phoneNumber
        );
    }


    public boolean emailExists(String email)
            throws SQLException {

        return customerDatabaseRepository.emailExists(
                email
        );
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
            String pin)
            throws SQLException {

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


    public Customer findCustomer(int accountNumber)
            throws SQLException {

        return customerDatabaseRepository.findCustomerByAccountNumber(
                accountNumber
        );
    }


    public Customer findCustomerByAccountNumberExcludingSender(
            int accountNumber,
            Customer sender)
            throws SQLException {

        return customerDatabaseRepository
                .findCustomerByAccountNumberExcludingSender(
                        accountNumber,
                        sender
                );
    }


    public boolean deleteCustomer(int accountNumber)
            throws SQLException {

        return customerDatabaseRepository.deleteCustomer(
                accountNumber
        );
    }
}