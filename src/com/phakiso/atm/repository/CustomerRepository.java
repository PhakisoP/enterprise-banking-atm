package com.phakiso.atm.repository;

import com.phakiso.atm.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer findCustomerById(int customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId() == customerId) {
                return customer;
            }

        }

        return null;
    }
    public Customer findCustomerByAccountNumber(int accountNumber) {

        for (Customer customer : customers) {

            if (customer.getAccount().getAccountNumber() == accountNumber) {
                return customer;
            }

        }

        return null;
    }
    public Customer findCustomerByAccountNumberExcludingSender(
            int accountNumber,
            Customer sender) {

        for (Customer customer : customers) {

            if (customer.getAccount().getAccountNumber() == accountNumber
                    && customer != sender) {

                return customer;
            }
        }

        return null;

    }
    public boolean deleteCustomer(int accountNumber) {

        Customer customer =
                findCustomerByAccountNumber(accountNumber);

        if (customer != null) {

            customers.remove(customer);

            return true;
        }

        return false;
    }
}
