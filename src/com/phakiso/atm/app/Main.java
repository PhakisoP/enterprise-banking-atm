package com.phakiso.atm.app;

import com.phakiso.atm.service.ATMService;
import com.phakiso.atm.service.AdminService;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            // Create the ATM service used by the customer banking flow.
            ATMService atmService = new ATMService();

            // Launch Bank Admin System.
            AdminService adminService =
                    new AdminService(scanner, atmService);

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