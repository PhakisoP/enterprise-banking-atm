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
    public void displayMenu() {

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
        System.out.println("You selected option " + option);


    }
}