package com.phakiso.atm.repository;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDatabaseRepository {

    private static final String INSERT_TRANSACTION_SQL = """
            INSERT INTO transactions
            (account_number, transaction_type, amount, balance_after)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_RECENT_TRANSACTIONS_SQL = """
            SELECT transaction_type,
                   amount,
                   transaction_date
            FROM transactions
            WHERE account_number = ?
            ORDER BY transaction_id DESC
            LIMIT ?
            """;


    // ============================================================
    // SAVE TRANSACTION
    // ============================================================

    public void saveTransaction(
            Connection connection,
            BankAccount account,
            String transactionType,
            double amount,
            double balanceAfter)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_TRANSACTION_SQL)) {

            statement.setInt(
                    1,
                    account.getAccountNumber()
            );

            statement.setString(
                    2,
                    transactionType
            );

            statement.setDouble(
                    3,
                    amount
            );

            statement.setDouble(
                    4,
                    balanceAfter
            );

            statement.executeUpdate();
        }
    }


    // ============================================================
    // FIND RECENT TRANSACTIONS
    // ============================================================

    public List<Transaction> findRecentTransactions(
            Connection connection,
            int accountNumber,
            int limit)
            throws SQLException {

        List<Transaction> transactions =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_RECENT_TRANSACTIONS_SQL)) {

            statement.setInt(1, accountNumber);
            statement.setInt(2, limit);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    transactions.add(
                            mapTransaction(resultSet)
                    );
                }
            }
        }

        return transactions;
    }


    // ============================================================
    // MAP DATABASE RESULT TO TRANSACTION
    // ============================================================

    private Transaction mapTransaction(
            ResultSet resultSet)
            throws SQLException {

        String transactionType =
                resultSet.getString(
                        "transaction_type"
                );

        double amount =
                resultSet.getDouble("amount");

        Timestamp timestamp =
                resultSet.getTimestamp(
                        "transaction_date"
                );

        LocalDateTime transactionDate =
                timestamp.toLocalDateTime();

        return new Transaction(
                transactionType,
                amount,
                transactionDate
        );
    }
}