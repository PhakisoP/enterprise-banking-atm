package com.phakiso.atm.app;

import com.phakiso.atm.model.Customer;
import com.phakiso.atm.service.CustomerService;
import com.phakiso.atm.model.BankAccount;

public class Main {

    public static void main(String[] args) {

        BankAccount account =
                new BankAccount(
                        100001,
                        "Savings",
                        15000,
                        "1234"
                );
        Customer customer =
                new Customer(
                        1,
                        "Phakiso",
                        "Pitso",
                        "9001015009088",
                        "0812345678",
                        "phakiso@email.com",
                        account
                );

        System.out.println(customer.getFirstName());

        System.out.println(customer.getAccount().getAccountNumber());

        System.out.println(customer.getAccount().getBalance());

        System.out.println(customer.getAccount().getAccountType());
    }

}