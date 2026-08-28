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
     * <p>
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
     * <p>
     * The database balance and transaction record are written
     * within the same database transaction. The in-memory account
     * is updated only after the database transaction succeeds.
     *
     * @param customer customer making the deposit
     * @param amount   amount to deposit
     * @throws SQLException if the database operation fails
     */
    public void deposit(
            Customer customer,
            double amount) throws SQLException {

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

        executeInTransaction(connection -> {

            executeTransaction(
                    connection,
                    account,
                    "Deposit",
                    amount,
                    newBalance
            );
        });

        // Update the Java object only after the database
        // transaction has successfully committed.
        account.increaseBalanceSilently(amount);

        System.out.println(
                "Deposit completed successfully."
        );
    }


    // ==========================================
// WITHDRAWAL
// ==========================================


    /**
     * Withdraws money from a customer's account.
     * <p>
     * The database balance and transaction record are written
     * within the same database transaction. The in-memory account
     * is updated only after the database transaction succeeds.
     *
     * @param customer customer making the withdrawal
     * @param amount   amount to withdraw
     * @throws SQLException if the database operation fails
     */
    public void withdraw(
            Customer customer,
            double amount) throws SQLException {

        // The withdrawal amount must be greater than zero.
        if (amount <= 0) {

            System.out.println(
                    "Withdrawal amount must be greater than zero."
            );

            return;
        }

        BankAccount account =
                customer.getAccount();

        // The customer cannot withdraw more money than
        // the current account balance.
        if (amount > account.getBalance()) {

            System.out.println(
                    "Insufficient funds."
            );

            return;
        }

        double newBalance =
                account.getBalance() - amount;

        executeInTransaction(connection -> {

            executeTransaction(
                    connection,
                    account,
                    "Withdrawal",
                    amount,
                    newBalance
            );
        });

        // Update the Java object only after the database
        // transaction has successfully committed.
        account.decreaseBalanceSilently(amount);

        System.out.println(
                "Withdrawal completed successfully."
        );
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

    /**
     * Displays the five most recent transactions for a customer's account.
     *
     * This is a read-only database operation, so no explicit
     * database transaction is required.
     *
     * @param customer customer whose transactions should be displayed
     * @throws SQLException if the transaction history cannot be retrieved
     */
    public void displayMiniStatement(
            Customer customer)
            throws SQLException {

        BankAccount account =
                customer.getAccount();

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

    /**
     * Transfers money from one customer's account to another account.
     * <p>
     * The sender and recipient balance updates, together with both
     * transaction records, are executed inside one database transaction.
     * <p>
     * If any database operation fails, executeInTransaction() rolls
     * the entire transaction back.
     * <p>
     * The Java account objects are updated only after the database
     * transaction has successfully committed.
     *
     * @param sender                 customer sending the money
     * @param recipientAccountNumber account number receiving the money
     * @param amount                 amount to transfer
     * @throws SQLException if the database operation fails
     */
    public void transferMoney(
            Customer sender,
            int recipientAccountNumber,
            double amount)
            throws SQLException {

        // ==========================================
        // FIND RECIPIENT
        // ==========================================

        Customer recipient =
                bankService.findCustomer(
                        recipientAccountNumber
                );

        if (recipient == null) {

            System.out.println();
            System.out.println(
                    "Recipient account not found."
            );

            return;
        }


        // ==========================================
        // VALIDATE TRANSFER AMOUNT
        // ==========================================

        if (amount <= 0) {

            System.out.println();
            System.out.println(
                    "Transfer amount must be greater than zero."
            );

            return;
        }


        // ==========================================
        // PREVENT SELF-TRANSFER
        // ==========================================

        if (recipient.getCustomerId() ==
                sender.getCustomerId()) {

            System.out.println();
            System.out.println(
                    "You cannot transfer money to your own account."
            );

            return;
        }


        // ==========================================
        // GET ACCOUNTS
        // ==========================================

        BankAccount senderAccount =
                sender.getAccount();

        BankAccount recipientAccount =
                recipient.getAccount();


        // ==========================================
        // CHECK AVAILABLE BALANCE
        // ==========================================

        if (amount > senderAccount.getBalance()) {

            System.out.println();
            System.out.println(
                    "Insufficient funds."
            );

            return;
        }


        // Calculate the balances that will exist
        // after the transfer succeeds.
        double senderNewBalance =
                senderAccount.getBalance() - amount;

        double recipientNewBalance =
                recipientAccount.getBalance() + amount;


        // ==========================================
        // EXECUTE DATABASE TRANSACTION
        // ==========================================

        /*
         * The complete transfer is treated as one
         * atomic database transaction.
         *
         * If any operation inside this block fails,
         * executeInTransaction() will roll back
         * everything.
         */
        executeInTransaction(connection -> {

            // ======================================
            // UPDATE SENDER BALANCE
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
            // UPDATE RECIPIENT BALANCE
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
        });


        // ==========================================
        // UPDATE JAVA OBJECTS
        // ==========================================

        /*
         * The database transaction has successfully
         * committed at this point.
         *
         * Only now do we update the in-memory objects.
         */
        senderAccount.decreaseBalanceSilently(amount);

        recipientAccount.increaseBalanceSilently(amount);


        // ==========================================
        // DISPLAY SUCCESS MESSAGE
        // ==========================================

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
    }
}