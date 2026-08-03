package com.phakiso.atm.app;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;
import com.phakiso.atm.service.AdminService;
import com.phakiso.atm.service.PersistenceService;

public class Main {

    public static void main(String[] args) {

        PersistenceService persistenceService =
                new PersistenceService();

        CustomerRepository repository =
                persistenceService.load();

        // -------------------------
        // Sample Customer 1
        // -------------------------
        BankAccount account1 = new BankAccount(
                100001,
                "Savings",
                15000,
                "1234"
        );

        Customer customer1 = new Customer(
                1,
                "Phakiso",
                "Pitso",
                "9001015009088",
                "0812345678",
                "phakiso@email.com",
                account1
        );

        // -------------------------
        // Sample Customer 2
        // -------------------------
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

        // Save the sample customers
        repository.addCustomer(customer1);
        repository.addCustomer(customer2);

        // Start the Admin System
        AdminService adminService = new AdminService();
        adminService.displayAdminMenu(repository);
    }
}