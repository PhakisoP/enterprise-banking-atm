package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.service.CustomerService;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

public class Main {


    public static void main(String[] args) {

        CustomerRepository repository = new CustomerRepository();

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

        BankAccount account2 = new BankAccount(
                100002,
                "Savings",
                9500,
                "5678"
        );

        Customer customer2 = new Customer(
                2,
                "Sarah",
                "Johnson",
                "9202025009088",
                "0823456789",
                "sarah@email.com",
                account2
        );

        repository.addCustomer(customer);
        repository.addCustomer(customer2);

        CustomerService customerService = new CustomerService();

        Customer loggedInCustomer =
                customerService.login(repository);

        if (loggedInCustomer != null) {

            customerService.displayMenu(
                    loggedInCustomer,
                    repository
            );
        }

        System.out.println();

        for (Customer c : repository.getCustomers()) {

            System.out.println(c.getCustomerId());
            System.out.println(c.getFirstName());
            System.out.println(c.getAccount().getAccountNumber());
            System.out.println("----------------");

    }
    }

}
