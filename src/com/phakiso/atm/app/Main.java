package com.phakiso.atm.app;

import com.phakiso.atm.model.Customer;
import com.phakiso.atm.service.CustomerService;

public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer(
                1,
                "Phakiso",
                "Pitso",
                "9001015009088",
                "0812345678",
                "phakiso@email.com"
        );

        System.out.println("Customer Created Successfully!");

        System.out.println("----------------------------");
        CustomerService customerService = new CustomerService();

        customerService.displayCustomer(customer);
    }

}