package com.phakiso.atm.service;

import com.phakiso.atm.service.CustomerService;
import com.phakiso.atm.service.AuthenticationService;
import com.phakiso.atm.model.Customer;
import java.util.Scanner;
import java.sql.SQLException;

public class AdminService {

    private final Scanner scanner = new Scanner(System.in);
    private final ValidationService validationService =
            new ValidationService();
    private final BankService bankService =
            new BankService();
    private final AccountService accountService =
            new AccountService();


    public void createCustomer() throws SQLException {

        System.out.println("================================");
        System.out.println("    Create New Customer");
        System.out.println("================================");


        // ==========================================
        // CUSTOMER ID
        // ==========================================

        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        // Customer IDs must be unique in the database.
        if (bankService.customerIdExists(customerID)) {

            System.out.println();
            System.out.println("Customer ID already exists!");

            return;
        }


        // ==========================================
        // SOUTH AFRICAN ID NUMBER
        // ==========================================

        System.out.print("Enter ID number: ");
        String idNumber = scanner.next();

        // Validate the structure, date and checksum
        // of the South African ID number.
        if (!validationService.isValidIdNumber(idNumber)) {
            return;
        }

        // The ID number must also be unique.
        if (bankService.idNumberExists(idNumber)) {

            System.out.println();
            System.out.println(
                    "A customer with this ID already exists!"
            );

            return;
        }


        // ==========================================
        // PHONE NUMBER
        // ==========================================

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.next();

        // Phone numbers must be unique in the database.
        if (bankService.phoneNumberExists(phoneNumber)) {

            System.out.println();
            System.out.println(
                    "This Phone number is already linked to another customer!"
            );

            return;
        }


        // ==========================================
        // EMAIL
        // ==========================================

        System.out.print("Enter Email: ");
        String email = scanner.next();

        // Email addresses must be unique in the database.
        if (bankService.emailExists(email)) {

            System.out.println();
            System.out.println(
                    "This email is already linked to another customer!"
            );

            return;
        }


        // ==========================================
        // ACCOUNT NUMBER
        // ==========================================

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        // Account numbers must be unique in the database.
        if (accountService.accountNumberExists(accountNumber)) {

            System.out.println();
            System.out.println(
                    "An account with this account number already exists."
            );

            return;
        }


        // ==========================================
        // FIRST NAME
        // ==========================================

        System.out.print("Enter First Name: ");
        String firstName = scanner.next();

        if (!validationService.isValidName(firstName)) {

            System.out.println();
            System.out.println("First Name is invalid!");

            return;
        }


        // ==========================================
        // LAST NAME
        // ==========================================

        System.out.print("Enter Last Name: ");
        String lastName = scanner.next();

        if (!validationService.isValidName(lastName)) {

            System.out.println();
            System.out.println("Last Name is invalid!");

            return;
        }


        // ==========================================
        // ACCOUNT TYPE
        // ==========================================

        System.out.print("Enter Account type: ");
        String accountType = scanner.next();

        if (!validationService.isValidAccountType(accountType)) {
            return;
        }


        // ==========================================
        // OPENING BALANCE
        // ==========================================

        System.out.print("Enter Opening Balance: ");
        double openingBalance = scanner.nextDouble();

        if (!validationService.validateOpeningBalance(openingBalance)) {
            return;
        }


        // ==========================================
        // PIN
        // ==========================================

        System.out.print("Enter Pin: ");
        String pin = scanner.next();

        if (!validationService.isValidPin(pin)) {
            return;
        }


        // ==========================================
        // CREATE CUSTOMER
        // ==========================================

        Customer customer = bankService.createCustomer(
                customerID,
                firstName,
                lastName,
                idNumber,
                phoneNumber,
                email,
                accountNumber,
                accountType,
                openingBalance,
                pin
        );


        // ==========================================
        // SUCCESS MESSAGE
        // ==========================================

        System.out.println();
        System.out.println("Customer created successfully!");

        System.out.println(
                "Customer: "
                        + customer.getFirstName()
                        + " "
                        + customer.getLastName()
        );

        System.out.println(
                "Account Number: "
                        + customer.getAccount().getAccountNumber()
        );
    }

    public void findCustomer() throws SQLException {

        System.out.println("================================");
        System.out.println("          FIND CUSTOMER");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        Customer customer = bankService.findCustomer(
                accountNumber);

        if (customer == null) {

            System.out.println();
            System.out.println("Customer not found.");
            return;
        }
        System.out.println();
        System.out.println("================================");
        System.out.println("     CUSTOMER DETAILS");
        System.out.println("================================");

        System.out.println("Customer ID : "
                + customer.getCustomerId());

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

    public void deleteCustomer() throws SQLException {

        System.out.println("================================");
        System.out.println("      DELETE CUSTOMER");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        boolean deleted =
                bankService.deleteCustomer(accountNumber);

        System.out.println();

        if (deleted) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }


    public void viewAllCustomers() throws SQLException {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          ALL CUSTOMERS");
        System.out.println("========================================");

        for (Customer customer : bankService.getAllCustomers()) {

            System.out.println("Customer ID    : "
                    + customer.getCustomerId());

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


    public void unlockAccount() throws SQLException {

        System.out.println();
        System.out.println("================================");
        System.out.println("       UNLOCK ACCOUNT");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");

        int accountNumber =
                scanner.nextInt();

        boolean unlocked =
                accountService.unlockAccount(
                        accountNumber
                );

        if (unlocked) {

            System.out.println();
            System.out.println(
                    "Account unlocked successfully."
            );

            System.out.println(
                    "Failed login attempts reset."
            );

        } else {

            System.out.println();
            System.out.println(
                    "Account could not be unlocked."
            );
        }
    }


    public void displayAdminMenu() throws SQLException {

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
            System.out.println("6. Unlock Account");
            System.out.println("7. Exit");
            System.out.println("================================");

            System.out.print("Choose Option: ");


            int option = scanner.nextInt();
            switch (option) {

                case 1:
                    createCustomer();
                    break;

                case 2:
                    viewAllCustomers();
                    break;

                case 3:
                    findCustomer();
                    break;

                case 4:
                    deleteCustomer();
                    break;

                case 5:

                    AuthenticationService authenticationService =
                            new AuthenticationService(scanner);

                    CustomerService customerService =
                            new CustomerService(scanner);

                    while (true) {

                        Customer loggedInCustomer =
                                authenticationService.login();

                        if (loggedInCustomer != null) {

                            customerService.displayMenu(
                                    loggedInCustomer
                            );

                            break;
                        }

                        System.out.println();
                        System.out.println("Please try again.");
                    }

                    break;

                case 6:

                    unlockAccount();
                    break;

                case 7:
                    System.out.println("Exiting Admin System...");
                    running = false;
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid option. Please choose between 1 and 7."
                    );
                    break;
            }
        }
    }
}
