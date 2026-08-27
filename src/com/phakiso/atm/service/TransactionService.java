package com.phakiso.atm.service;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.model.Transaction;
import com.phakiso.atm.repository.TransactionDatabaseRepository;
import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    private final AccountService accountService =
            new AccountService();

    private final TransactionDatabaseRepository transactionDatabaseRepository =
            new TransactionDatabaseRepository();

    private final BankService bankService =
            new BankService();

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

        double newBalance =
                account.getBalance() + amount;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                executeTransaction(
                        connection,
                        account,
                        "Deposit",
                        amount,
                        newBalance
                );

                connection.commit();

                // Update Java object only after successful commit
                account.increaseBalanceSilently(amount);

                System.out.println(
                        "Deposit completed successfully."
                );

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Deposit failed. Database transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error."
            );

            e.printStackTrace();
        }
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
            System.out.println(
                    "Insufficient funds."
            );
            return;
        }

        double newBalance =
                account.getBalance() - amount;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                executeTransaction(
                        connection,
                        account,
                        "Withdrawal",
                        amount,
                        newBalance
                );

                connection.commit();

                // Update Java object after successful commit
                account.decreaseBalanceSilently(amount);

                System.out.println(
                        "Withdrawal completed successfully."
                );

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Withdrawal failed. Database transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error."
            );

            e.printStackTrace();
        }
    }

    private void executeTransaction(
            Connection connection,
            BankAccount account,
            String transactionType,
            double amount,
            double newBalance)
            throws SQLException {

        boolean balanceUpdated =
                accountService.updateBalance(
                        connection,
                        account.getAccountNumber(),
                        newBalance
                );

        if (!balanceUpdated) {
            throw new SQLException(
                    "Account balance could not be updated."
            );
        }

        transactionDatabaseRepository.saveTransaction(
                connection,
                account,
                transactionType,
                amount,
                newBalance
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

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            List<Transaction> transactions =
                    transactionDatabaseRepository.findRecentTransactions(
                            connection,
                            account.getAccountNumber(),
                            5
                    );

            if (transactions.isEmpty()) {

                System.out.println(
                        "No transactions found."
                );

            } else {

                for (Transaction transaction : transactions) {

                    System.out.printf(
                            "%-20s %-15s R%.2f%n",
                            transaction.getTransactionDate(),
                            transaction.getType(),
                            transaction.getAmount()
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to retrieve transaction history."
            );

            e.printStackTrace();
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
            int recipientAccountNumber,
            double amount) {

        Customer recipient;

        try {

            recipient =
                    bankService.findCustomer(
                            recipientAccountNumber
                    );

        } catch (SQLException e) {

            System.out.println();
            System.out.println(
                    "Unable to find recipient account."
            );

            e.printStackTrace();

            return;
        }

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

        if (recipient.getCustomerId() ==
                sender.getCustomerId()) {

            System.out.println();
            System.out.println(
                    "You cannot transfer money to your own account."
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

        double senderNewBalance =
                senderAccount.getBalance() - amount;

        double recipientNewBalance =
                recipientAccount.getBalance() + amount;


        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                // ======================================
                // UPDATE SENDER
                // ======================================

                boolean senderUpdated =
                        accountService.updateBalance(
                                connection,
                                senderAccount.getAccountNumber(),
                                senderNewBalance
                        );

                if (!senderUpdated) {

                    throw new SQLException(
                            "Sender account balance could not be updated."
                    );
                }


                // ======================================
                // UPDATE RECIPIENT
                // ======================================

                boolean recipientUpdated =
                        accountService.updateBalance(
                                connection,
                                recipientAccount.getAccountNumber(),
                                recipientNewBalance
                        );

                if (!recipientUpdated) {

                    throw new SQLException(
                            "Recipient account balance could not be updated."
                    );
                }


                // ======================================
                // SAVE SENDER TRANSACTION
                // ======================================

                transactionDatabaseRepository.saveTransaction(
                        connection,
                        senderAccount,
                        "Transfer Out",
                        amount,
                        senderNewBalance
                );

                // ======================================
                // SAVE RECIPIENT TRANSACTION
                // ======================================

                transactionDatabaseRepository.saveTransaction(
                        connection,
                        recipientAccount,
                        "Transfer In",
                        amount,
                        recipientNewBalance
                );


                // ======================================
                // COMMIT
                // ======================================

                connection.commit();

                // Update Java objects silently after successful commit
                senderAccount.decreaseBalanceSilently(amount);
                recipientAccount.increaseBalanceSilently(amount);


                System.out.println();

                System.out.println(
                        "=============================="
                );

                System.out.println(
                        "       TRANSFER SUCCESSFUL"
                );

                System.out.println(
                        "=============================="
                );

                System.out.println();

                System.out.printf(
                        "From Account      : %d%n",
                        senderAccount.getAccountNumber()
                );

                System.out.printf(
                        "Recipient Account : %d%n",
                        recipientAccount.getAccountNumber()
                );

                System.out.printf(
                        "Amount            : R%.2f%n",
                        amount
                );

                System.out.printf(
                        "New Balance       : R%.2f%n",
                        senderAccount.getBalance()
                );

                System.out.println();

                System.out.println(
                        "=============================="
                );

            } catch (SQLException e) {

                connection.rollback();

                System.out.println();
                System.out.println(
                        "Transfer failed."
                );

                System.out.println(
                        "Database transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database connection error."
            );

            e.printStackTrace();
        }
    }

        }