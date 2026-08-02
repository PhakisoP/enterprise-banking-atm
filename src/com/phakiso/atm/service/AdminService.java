package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

import java.util.Scanner;

public class AdminService {

    private final Scanner scanner = new Scanner(System.in);

    public void createCustomer(CustomerRepository repository) {
            System.out.println("================================");
            System.out.println("    Create New Customer");
            System.out.println("================================");

            System.out.print("Enter Customer ID: ");
    int customerID = scanner.nextInt();

                    System.out.print("Enter First Name: ");
                    String firstName = scanner.next();

                    System.out.print("Enter Last Name: ");
    String lastName = scanner.next();

                    System.out.print("Enter ID number: ");
    String idNumber = scanner.next();

                    System.out.print("Enter Phone Number: ");
    String phoneNumber = scanner.next();

                    System.out.print("Enter Email: ");
    String email = scanner.next();

                    System.out.print("Enter Account Number: ");
    int accountNumber = scanner.nextInt();

                    System.out.print("Enter Account type: ");
    String accountType = scanner.next();

                    System.out.print("Enter Opening Balance: ");
        double openingBalance = scanner.nextDouble();

        System.out.print("Enter Pin: ");
    String pin = scanner.next();

        BankAccount account = new BankAccount(
                accountNumber,
                accountType,
                openingBalance,
                pin
        );

        Customer customer = new Customer(
                customerID,
                firstName,
                lastName,
                idNumber,
                phoneNumber,
                email,
                account
        );

        repository.addCustomer(customer);

        System.out.println();
        System.out.println("Customer created successfully!");
        System.out.println("Customer: " + firstName + " " + lastName);
        System.out.println("Account Number: " + accountNumber);


    }
    public void viewAllCustomers(CustomerRepository repository) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ALL CUSTOMERS");
        System.out.println("========================================");

        for (Customer customer : repository.getCustomers()) {

            System.out.println("Customer ID    : " + customer.getCustomerId());
            System.out.println("Name           : "
                    + customer.getFirstName()
                    + " "
                    + customer.getLastName());

            System.out.println("Account Number : "
                    + customer.getAccount().getAccountNumber());

            System.out.println("Balance        : R"
                    + customer.getAccount().getBalance());

            System.out.println("----------------------------------------");
        }
    }

    public void displayAdminMenu(CustomerRepository repository)  {

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("================================");
            System.out.println("    BANK ADMIN SYSTEM");
            System.out.println("================================");
            System.out.println("1. Create Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Find Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Launch ATM");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Choose Option: ");


            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    createCustomer(repository);
                    break;

                case 2:
                    viewAllCustomers(repository);
                    break;

                        case 3:
                            System.out.println("Find Customers selected");
                            break;

                            case 4:
                                System.out.println("Delete Customer selected");
                                break;

                                case 5:
                                    System.out.println("Launch ATM selected");
                                    break;

                                    case 6:
                                        System.out.println("Exiting Admin System...");
                                        running = false;
                                        break;

                                        default:
                                            System.out.println("invalid option");


            }

        }

    }

}