package com.phakiso.atm.app;

import com.phakiso.atm.service.ATMService;
import com.phakiso.atm.service.AdminService;
import com.phakiso.atm.service.AccountService;
import com.phakiso.atm.service.BankService;
import com.phakiso.atm.service.TransactionService;
import com.phakiso.atm.service.ValidationService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            // Create the services required by the banking flow.
            AccountService accountService = new AccountService();
            BankService bankService = new BankService();

            TransactionService transactionService =
                    new TransactionService(
                            accountService,
                            bankService
                    );

            ValidationService validationService =
                    new ValidationService();

// Create the ATM service.
            ATMService atmService =
                    new ATMService(
                            transactionService,
                            validationService
                    );

// Launch Bank Admin System.
            AdminService adminService =
                    new AdminService(
                            scanner,
                            atmService,
                            accountService
                    );

            adminService.displayAdminMenu();

        } catch (SQLException e) {

            System.out.println();

            System.out.println(
                    "=============================="
            );

            System.out.println(
                    "         DATABASE ERROR"
            );

            System.out.println(
                    "=============================="
            );

            System.out.println(
                    "The banking system could not access the database."
            );

            System.out.println(
                    "Please check the database connection."
            );

            System.out.println(
                    "=============================="
            );

            e.printStackTrace();
        }
    }
}