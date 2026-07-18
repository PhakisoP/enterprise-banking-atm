package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;
import java.util.Scanner;

public class CustomerService {

    private Scanner scanner = new Scanner(System.in);

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
    public void displayMenu(Customer customer) {

        System.out.println();
        System.out.println("==============================");
        System.out.println(" Enterprise Banking ATM");
        System.out.println("==============================");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.println("==============================");

        System.out.print("Choose option: ");

        int option = scanner.nextInt();

        System.out.println();
        switch (option) {

            case 1:
                System.out.println("Current Balance : R" + customer.getAccount().getBalance());
                break;

            case 2:
                System.out.print("Enter deposit amount: R");
                double depositAmount = scanner.nextDouble();

                customer.getAccount().deposit(depositAmount);
                break;

            case 3:
                System.out.print("Enter withdrawal amount: R");
                double withdrawalAmount = scanner.nextDouble();

                customer.getAccount().withdraw(withdrawalAmount);
                break;

            case 4:
                System.out.println("Thank you for using Enterprise Banking ATM.");
                break;

            default:
                System.out.println("Invalid option.");
        }


    }
}