package com.phakiso.atm.app;

import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;

public class DatabaseConnectionTest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   ENTERPRISE BANKING ATM");
        System.out.println("   DATABASE CONNECTION TEST");
        System.out.println("========================================");

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");
                System.out.println("Database: enterprise_banking");
                System.out.println("Server: localhost:3306");
            }

        } catch (Exception e) {

            System.out.println("Database connection FAILED!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}