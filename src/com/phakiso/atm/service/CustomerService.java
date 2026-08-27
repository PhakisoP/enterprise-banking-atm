package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerService {

    private final AuthenticationService authenticationService;
    private final ATMService atmService;
    private final Scanner scanner;

    public CustomerService(Scanner scanner) {

        this.scanner = scanner;

        this.authenticationService =
                new AuthenticationService(scanner);

        this.atmService =
                new ATMService();
    }


    public void displayCustomer(Customer customer) {

        System.out.println("--------------------------------");
        System.out.println("Customer ID : " + customer.getCustomerId());
        System.out.println("Name : " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("ID Number : " + customer.getIdNumber());

        System.out.println();

        System.out.println("Account Number : " +
                customer.getAccount().getAccountNumber());

        System.out.println("Account Type : " +
                customer.getAccount().getAccountType());

        System.out.println("Balance : R" +
                customer.getAccount().getBalance());

    }

    public void displayMenu(Customer customer)
            throws SQLException {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==============================");
            System.out.println(" Enterprise Banking ATM");
            System.out.println("==============================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Change PIN");
            System.out.println("6. Transfer Money");
            System.out.println("7. Logout");
            System.out.println("8. Exit ATM");
            System.out.println("==============================");

            System.out.print("Choose option: ");

            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    atmService.checkBalance(customer);
                    break;

                case 2:
                    atmService.deposit(customer, scanner);
                    break;

                case 3:
                    atmService.withdraw(customer, scanner);
                    break;

                case 4:
                    atmService.miniStatement(customer);
                    break;
                case 5:
                    atmService.changePin(customer, scanner);
                    break;

                case 6:
                    atmService.transferMoney(customer, scanner);
                    break;

                case 7:

                    System.out.println();
                    System.out.println("Logging out...");
                    running = false;
                    break;

                case 8:

                    System.out.println();
                    System.out.println("Shutting down ATM...");
                    System.exit(0);


            }
        }
    }
}
