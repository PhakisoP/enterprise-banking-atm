package com.phakiso.atm.service;

import com.phakiso.atm.factory.CustomerFactory;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.util.SouthAfricanIdGenerator;
import java.util.Random;

public class TestDataService {

    private int nextAccountNumber = 200001;

    public int generateAccountNumber() {

        return nextAccountNumber++;
    }

    private final SouthAfricanIdGenerator idGenerator =
            new SouthAfricanIdGenerator();

    private final Random random =
            new Random();

    private final CustomerFactory customerFactory =
            new CustomerFactory();

    private final String[] firstNames = {
            "Phakiso",
            "David",
            "Sarah",
            "John",
            "Ceryl",
            "Trevor",
            "Malaika",
            "Thatohatsi"
    };

    private final String[] lastNames = {
            "Pitso",
            "Jones",
            "Smith",
            "Johnson",
            "Martin",
            "Mokoena",
            "Dlamini",
            "Nkosi"
    };

    public String generateFirstName() {

        return firstNames[
                random.nextInt(firstNames.length)
                ];

    }

    public String generateLastName() {

        return lastNames[
                random.nextInt(lastNames.length)
                ];
    }

    public String generateIdNumber() {

        return idGenerator.generateId();
    }

    private final String[] phonePrefixes = {
            "060",
            "061",
            "062",
            "063",
            "064",
            "065",
            "066",
            "067",
            "068",
            "069",
            "071",
            "072",
            "073",
            "074",
            "076",
            "078",
            "079",
            "081",
            "082",
            "083",
            "084"
    };

    public String generatePhoneNumber() {

        String prefix =
                phonePrefixes[
                        random.nextInt(phonePrefixes.length)
                        ];

        int remainingDigits =
                random.nextInt(10_000_000);

        return prefix +
                String.format("%07d", remainingDigits);
    }

    public String generateEmail(
            String firstName,
            String lastName,
            int customerId) {

        return firstName.toLowerCase()
                + "."
                + lastName.toLowerCase()
                + customerId
                + "@testbank.co.za";
    }

    public String generateAccountType() {

        String[] accountTypes = {
                "Savings",
                "Current",
                "Credit"
        };

        return accountTypes[
                random.nextInt(accountTypes.length)
                ];
    }

    public double generateOpeningBalance() {

        return 1000 +
                random.nextInt(19001);
    }

    public String generatePin() {

        return String.format(
                "%04d",
                random.nextInt(10000)
        );
    }

    public Customer generateCustomer(int customerId) {

        String firstName =
                generateFirstName();

        String lastName =
                generateLastName();

        String idNumber =
                generateIdNumber();

        String phoneNumber =
                generatePhoneNumber();

        int accountNumber =
                generateAccountNumber();

        String email =
                generateEmail(
                        firstName,
                        lastName,
                        customerId
                );

        String accountType =
                generateAccountType();

        double openingBalance =
                generateOpeningBalance();

        String pin =
                generatePin();

        return customerFactory.createCustomer(
                customerId,
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
    }
}