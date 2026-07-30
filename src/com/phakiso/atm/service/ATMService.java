package com.phakiso.atm.service;

import com.phakiso.atm.service.TransactionService;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Transaction;
import java.util.Scanner;
import com.phakiso.atm.repository.CustomerRepository;

public class ATMService {


    public void checkBalance(Customer customer) {

        System.out.println();
        System.out.println("Current Balance : R"
                + customer.getAccount().getBalance());
    }


    public void changePin(Customer customer, Scanner scanner) {

        BankAccount account = customer.getAccount();

        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();

        if (!account.validatePin(currentPin)) {
            System.out.println("Incorrect current PIN.");
            return;
        }

        System.out.print("Enter new PIN: ");
        String newPin = scanner.next();

        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.next();

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match.");
            return;
        }

        account.setPin(newPin);

        System.out.println("PIN changed successfully.");
    }

}
