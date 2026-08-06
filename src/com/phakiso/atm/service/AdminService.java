package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.repository.CustomerRepository;

import java.util.Scanner;

public class AdminService {

    private final Scanner scanner = new Scanner(System.in);

    private final ValidationService validationService =
            new ValidationService();

    private final PersistenceService persistenceService =
            new PersistenceService();

    public void createCustomer(CustomerRepository repository) {
            System.out.println("================================");
            System.out.println("    Create New Customer");
            System.out.println("================================");

            System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();
        if (repository.customerIDExists(customerID)) {
            System.out.println();
            System.out.println("Customer ID already exists!");
            return;
    }


        System.out.print("Enter ID number: ");
        String idNumber = scanner.next();

        if (!validationService.validateIdNumber(idNumber, repository)) {
            return;
        }
        if (!validationService.isValidIdNumber(idNumber)) {
            return;
        }


                    System.out.print("Enter Phone Number: ");
    String phoneNumber = scanner.next();
        if (!validationService.validatePhoneNumber(phoneNumber, repository)) {
            return;
        }

                    System.out.print("Enter Email: ");
    String email = scanner.next();
        if (!validationService.validateEmail(email, repository)) {
            return;
        }

                    System.out.print("Enter Account Number: ");
    int accountNumber = scanner.nextInt();
        if (!validationService.validateAccountNumber(accountNumber, repository)) {
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.next();
        if (!validationService.isValidName(firstName)) {
            System.out.println();
            System.out.println("First Name is invalid!");
            return;
        }

        System.out.print("Enter Last Name: ");
        String lastName = scanner.next();
        if (!validationService.isValidName(lastName)) {
            System.out.println();
            System.out.println("Last Name is invalid!");
            return;
        }

                    System.out.print("Enter Account type: ");
    String accountType = scanner.next();
        if (!validationService.isValidAccountType(accountType)) {
            return;
        }


                    System.out.print("Enter Opening Balance: ");
        double openingBalance = scanner.nextDouble();
        if (!validationService.validateOpeningBalance(openingBalance)) {
            return;
        }

        System.out.print("Enter Pin: ");
    String pin = scanner.next();
        if (!validationService.isValidPin(pin)) {
            return;
        }

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
        persistenceService.save(repository);

        System.out.println();
        System.out.println("Customer created successfully!");
        System.out.println("Customer: " + firstName + " " + lastName);
        System.out.println("Account Number: " + accountNumber);


    }

    public void findCustomer(CustomerRepository repository) {

        System.out.println("================================");
        System.out.println("       FIND CUSTOMER");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        Customer customer =
                repository.findCustomerByAccountNumber(accountNumber);

        if (customer == null) {

            System.out.println();
            System.out.println("Customer not found.");
            return;
        }



        System.out.println();
        System.out.println("================================");
        System.out.println("     CUSTOMER DETAILS");
        System.out.println("================================");

        System.out.println("Customer ID : " + customer.getCustomerId());

        System.out.println("Name        : "
                + customer.getFirstName()
                + " "
                + customer.getLastName());

        System.out.println("ID Number   : "
                + customer.getIdNumber());

        System.out.println("Phone       : "
                + customer.getPhoneNumber());

        System.out.println("Email       : "
                + customer.getEmail());

        System.out.println();

        System.out.println("Account Number : "
                + customer.getAccount().getAccountNumber());

        System.out.println("Account Type   : "
                + customer.getAccount().getAccountType());

        System.out.println("Balance        : R"
                + customer.getAccount().getBalance());
    }

    public void deleteCustomer(CustomerRepository repository) {

        System.out.println("================================");
        System.out.println("      DELETE CUSTOMER");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        boolean deleted = repository.deleteCustomer(accountNumber);

        System.out.println();

        if (deleted) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
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
                    findCustomer(repository);
                    break;

                case 4:
                    deleteCustomer(repository);
                    break;

                case 5:

                    CustomerService customerService = new CustomerService();

                    while (true) {

                        Customer loggedInCustomer =
                                customerService.login(repository);

                        if (loggedInCustomer != null) {

                            customerService.displayMenu(
                                    loggedInCustomer,
                                    repository
                            );

                            break;
                        }

                        System.out.println();
                        System.out.println("Please try again.");
                    }

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