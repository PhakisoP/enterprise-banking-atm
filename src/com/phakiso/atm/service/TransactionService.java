package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.model.Transaction;
import com.phakiso.atm.repository.CustomerRepository;
import com.phakiso.atm.repository.TransactionDatabaseRepository;

public class TransactionService {

    private final TransactionDatabaseRepository transactionRepository;

    public TransactionService() {
        this.transactionRepository =
                new TransactionDatabaseRepository();
    }


    // ==========================================
    // DEPOSIT
    // ==========================================

    public void deposit(Customer customer, double amount) {

        if (amount <= 0) {
            System.out.println(
                    "Deposit amount must be greater than zero."
            );
            return;
        }

        BankAccount account =
                customer.getAccount();

        account.deposit(amount);

        transactionRepository.saveTransaction(
                account,
                "Deposit",
                amount
        );
    }


    // ==========================================
    // WITHDRAWAL
    // ==========================================

    public void withdraw(Customer customer, double amount) {

        if (amount <= 0) {
            System.out.println(
                    "Withdrawal amount must be greater than zero."
            );
            return;
        }

        BankAccount account =
                customer.getAccount();

        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds.");
            return;
        }

        account.withdraw(amount);

        transactionRepository.saveTransaction(
                account,
                "Withdrawal",
                amount
        );
    }


    // ==========================================
    // MINI STATEMENT
    // ==========================================

    public void displayMiniStatement(Customer customer) {

        BankAccount account =
                customer.getAccount();

        System.out.println();
        System.out.println("==============================");
        System.out.println("      MINI STATEMENT");
        System.out.println("==============================");

        if (account.getTransactions().isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (Transaction transaction :
                    account.getTransactions()) {

                System.out.printf(
                        "%-20s %-15s R%.2f%n",
                        transaction.getTransactionDate(),
                        transaction.getType(),
                        transaction.getAmount()
                );
            }
        }

        System.out.println("------------------------------");
        System.out.println(
                "Current Balance : R" +
                        account.getBalance()
        );
        System.out.println("==============================");
    }


    // ==========================================
    // TRANSFER
    // ==========================================

    public void transferMoney(
            Customer sender,
            CustomerRepository repository,
            int recipientAccountNumber,
            double amount) {

        Customer recipient =
                repository.findCustomerByAccountNumberExcludingSender(
                        recipientAccountNumber,
                        sender
                );

        if (recipient == null) {
            System.out.println(
                    "Recipient account not found."
            );
            return;
        }

        if (amount <= 0) {
            System.out.println(
                    "Transfer amount must be greater than zero."
            );
            return;
        }

        BankAccount senderAccount =
                sender.getAccount();

        BankAccount recipientAccount =
                recipient.getAccount();

        if (amount > senderAccount.getBalance()) {
            System.out.println(
                    "Insufficient funds."
            );
            return;
        }

        // Withdraw from sender
        senderAccount.withdraw(amount);

        // Deposit into recipient
        recipientAccount.deposit(amount);

        // Save sender transaction
        transactionRepository.saveTransaction(
                senderAccount,
                "Transfer Out",
                amount
        );

        // Save recipient transaction
        transactionRepository.saveTransaction(
                recipientAccount,
                "Transfer In",
                amount
        );

        System.out.println();
        System.out.println("Transfer Successful!");
        System.out.println("------------------------------");
        System.out.println(
                "From : " + sender.getFirstName()
        );
        System.out.println(
                "To   : " + recipient.getFirstName()
        );
        System.out.println(
                "Amount : R" + amount
        );
    }
}