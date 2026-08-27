package com.phakiso.atm.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.sql.SQLException;


public class ValidationService {

    private final BankService bankService =
            new BankService();

    private final AccountService accountService =
            new AccountService();

    public boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return name.matches("[A-Za-z]+");
    }

    public boolean validateCustomerId(int customerId)
            throws SQLException {

        if (bankService.customerIdExists(customerId)) {

            System.out.println();
            System.out.println("Customer ID already exists!");

            return false;
        }

        return true;
    }

    public boolean validateIdNumber(String idNumber)
            throws SQLException {

        if (bankService.idNumberExists(idNumber)) {

            System.out.println();
            System.out.println(
                    "A customer with this ID already exists!"
            );

            return false;
        }

        return true;
    }

    public boolean validatePhoneNumber(String phoneNumber)
            throws SQLException {

        if (bankService.phoneNumberExists(phoneNumber)) {

            System.out.println();
            System.out.println(
                    "This Phone number is already linked to another customer!!"
            );

            return false;
        }

        return true;
    }

    public boolean isValidIdNumber(String idNumber) {

        if (!idNumber.matches("\\d{13}")) {
            System.out.println();
            System.out.println("ID Number must contain exactly 13 digits!");
            return false;
        }

        int month = Integer.parseInt(idNumber.substring(2, 4));

        if (month < 1 || month > 12) {
            System.out.println();
            System.out.println("Invalid month in ID Number!");
            return false;
        }

        int day = Integer.parseInt(idNumber.substring(4, 6));

        if (day < 1 || day > 31) {
            System.out.println();
            System.out.println("Invalid day in ID Number!");
            return false;
        }

        int year = Integer.parseInt(idNumber.substring(0, 2));
        year += (year <= 25) ? 2000 : 1900;

        try {

            LocalDate.of(year, month, day);

        } catch (DateTimeException e) {

            System.out.println();
            System.out.println("Invalid birth date in ID Number!");
            return false;
        }
        if (!isValidLuhnChecksum(idNumber)) {

            System.out.println();
            System.out.println("Invalid South African ID checksum!");
            return false;
        }
        return true;
    }

    private boolean isValidLuhnChecksum(String idNumber) {

        int oddSum = 0;

        // Step 1: Sum digits in odd positions (1st, 3rd, 5th...)
        for (int i = 0; i < 12; i += 2) {
            oddSum += Character.getNumericValue(idNumber.charAt(i));
        }

        // Step 2: Concatenate digits in even positions
        StringBuilder evenDigits = new StringBuilder();

        for (int i = 1; i < 12; i += 2) {
            evenDigits.append(idNumber.charAt(i));
        }

        // Step 3: Multiply by 2
        int evenNumber = Integer.parseInt(evenDigits.toString()) * 2;

        // Step 4: Sum all digits of the result
        int evenSum = 0;

        for (char c : String.valueOf(evenNumber).toCharArray()) {
            evenSum += Character.getNumericValue(c);
        }

        // Step 5: Total
        int total = oddSum + evenSum;

        // Step 6: Calculate expected check digit
        int checkDigit = (10 - (total % 10)) % 10;

        // Step 7: Compare with last digit
        int actualDigit = Character.getNumericValue(idNumber.charAt(12));

        return checkDigit == actualDigit;
    }


    public boolean validateEmail(String email)
            throws SQLException {

        if (bankService.emailExists(email)) {

            System.out.println();
            System.out.println(
                    "This email is already linked to another customer!"
            );

            return false;
        }

        return true;
    }


    public boolean validateAccountNumber(int accountNumber)
            throws SQLException {

        if (accountService.accountNumberExists(accountNumber)) {

            System.out.println();
            System.out.println(
                    "An account with this account number already exists."
            );

            return false;
        }


        return true;
    }

        public boolean validateOpeningBalance(double openingBalance) {
        if (openingBalance < 0) {
            System.out.println();
            System.out.println("Opening balance cannot be negative!");
            return false;
        }
        return true;
        }

    public boolean isValidPin(String pin) {

        if (!pin.matches("\\d{4}")) {

            System.out.println();
            System.out.println("PIN must contain exactly 4 digits!");
            return false;
        }

        return true;
    }

    public boolean isValidAccountType(String accountType) {

        if (!(accountType.equalsIgnoreCase("Savings")
                || accountType.equalsIgnoreCase("Current")
                || accountType.equalsIgnoreCase("Credit"))) {

            System.out.println();
            System.out.println("Invalid account type!");
            return false;
        }

        return true;
    }
        }



