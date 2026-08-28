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


    /**
     * Executes database work inside a JDBC transaction.
     *
     * The transaction is committed when the operation completes
     * successfully. If a SQLException occurs, the transaction
     * is rolled back.
     *
     * @param transaction database operation to execute
     */
    private void executeInTransaction(
            DatabaseTransaction transaction)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                transaction.execute(connection);

                connection.commit();

            } catch (SQLException e) {

                connection.rollback();

                throw e;
            }
        }
    }


// ==========================================
// DEPOSIT
// ==========================================

    /**
     * Deposits money into a customer's account.
     *
     * The database balance and transaction record are written
     * within the same database transaction. The in-memory account
     * is updated only after the database transaction succeeds.
     *
     * @param customer customer making the deposit
     * @param amount amount to deposit
     */
    public void deposit(
            Customer customer,
            double amount) {

        // The deposit amount must be greater than zero.
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

        try {

            /*
             * Execute the balance update and transaction recording
             * inside one database transaction.
             */
            executeInTransaction(connection -> {

                executeTransaction(
                        connection,
                        account,
                        "Deposit",
                        amount,
                        newBalance
                );
            });

            /*
             * Only update the Java object after the database
             * transaction has successfully committed.
             */
            account.increaseBalanceSilently(amount);

            System.out.println(
                    "Deposit completed successfully."
            );

        } catch (SQLException e) {

            /*
             * executeInTransaction() has already rolled back
             * the database transaction if a SQL error occurred.
             */
            System.out.println();
            System.out.println(
                    "Deposit failed. Database transaction rolled back."
            );

            e.printStackTrace();
        }
    }




    // ==========================================
// WITHDRAWAL
// ==========================================

    /**
     * Withdraws money from a customer's account.
     *
     * The database balance and transaction record are written
     * within the same database transaction. The in-memory account
     * is updated only after the database transaction succeeds.
     *
     * @param customer customer making the withdrawal
     * @param amount amount to withdraw
     */
    public void withdraw(
            Customer customer,
            double amount) {

        // The withdrawal amount must be greater than zero.
        if (amount <= 0) {

            System.out.println(
                    "Withdrawal amount must be greater than zero."
            );

            return;
        }

        BankAccount account =
                customer.getAccount();

        // The customer cannot withdraw more money
        // than is currently available in the account.
        if (amount > account.getBalance()) {

            System.out.println(
                    "Insufficient funds."
            );

            return;
        }

        double newBalance =
                account.getBalance() - amount;

        try {

            /*
             * Execute the balance update and transaction recording
             * inside one database transaction.
             */
            executeInTransaction(connection -> {

                executeTransaction(
                        connection,
                        account,
                        "Withdrawal",
                        amount,
                        newBalance
                );
            });

            /*
             * Only update the Java object after the database
             * transaction has successfully committed.
             */
            account.decreaseBalanceSilently(amount);

            System.out.println(
                    "Withdrawal completed successfully."
            );

        } catch (SQLException e) {

            /*
             * executeInTransaction() has already rolled back
             * the database transaction if a SQL error occurred.
             */
            System.out.println();
            System.out.println(
                    "Withdrawal failed. Database transaction rolled back."
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