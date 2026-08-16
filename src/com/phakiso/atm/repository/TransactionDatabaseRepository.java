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

    public void saveTransaction(
            Connection connection,
            BankAccount account,
            String transactionType,
            double amount,
            double balanceAfter)
            throws SQLException {

        String sql = """
                INSERT INTO transactions
                (account_number, transaction_type, amount, balance_after)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    String.valueOf(account.getAccountNumber())
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
    public List<Transaction> findRecentTransactions(
            Connection connection,
            int accountNumber,
            int limit) throws SQLException {

        String sql = """
            SELECT transaction_type,
                   amount,
                   transaction_date
            FROM transactions
            WHERE account_number = ?
            ORDER BY transaction_id DESC
            LIMIT ?
            """;

        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, accountNumber);
            statement.setInt(2, limit);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    String transactionType =
                            resultSet.getString("transaction_type");

                    double amount =
                            resultSet.getDouble("amount");

                    Timestamp timestamp =
                            resultSet.getTimestamp("transaction_date");

                    LocalDateTime transactionDate =
                            timestamp.toLocalDateTime();

                    Transaction transaction =
                            new Transaction(
                                    transactionType,
                                    amount,
                                    transactionDate
                            );

                    transactions.add(transaction);
                }
            }
        }

        return transactions;
    }
}