package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.service.CustomerService;
import com.phakiso.atm.model.Customer;

public class Main {

    public static void main(String[] args) {


        BankAccount account = new BankAccount(
                100001,
                "Savings",
                15000,
                "1234"
        );

        Customer customer = new Customer(
                1,
                "Phakiso",
                "Pitso",
                "9001015009088",
                "0812345678",
                "phakiso@email.com",
                account
        );

        CustomerService customerService = new CustomerService();
        if (customerService.authenticate(customer)) {

            customerService.displayMenu(customer);

        }
    }

}