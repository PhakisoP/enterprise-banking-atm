package com.phakiso.atm.repository;

import com.phakiso.atm.model.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CustomerRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Customer> customers = new ArrayList<>();


    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
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


    public boolean idNumberExists(String idNumber) {

        for (Customer customer : customers) {

            if (customer.getIdNumber().equals(idNumber)) {
                return true;
            }
        }

        return false;
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


    public boolean accountNumberExists(int accountNumber) {

        return findCustomerByAccountNumber(accountNumber) != null;
    }


    public boolean customerIdExists(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return true;
            }
        }
        return false;
    }


    public boolean phoneNumberExists(String phoneNumber) {

        for (Customer customer : customers) {

            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }

        return false;
    }


    public boolean emailExists(String email) {

        for (Customer customer : customers) {

            if (customer.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }

        return false;
    }
}