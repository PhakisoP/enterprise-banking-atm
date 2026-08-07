package com.phakiso.atm.factory;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;

public class CustomerFactory {
    public Customer createCustomer(
            int customerID,
            String firstName,
            String lastName,
            String idNumber,
            String phoneNumber,
            String email,
            int accountNumber,
            String accountType,
            double openingBalance,
            String pin) {

        BankAccount account = new BankAccount(
                accountNumber,
                accountType,
                openingBalance,
                pin
        );

        return new Customer(
                customerID,
                firstName,
                lastName,
                idNumber,
                phoneNumber,
                email,
                account
        );
    }
}
