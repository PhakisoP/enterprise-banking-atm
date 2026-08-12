package com.phakiso.atm.repository;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDatabaseRepository {

    public void saveTransaction(
            BankAccount account,
            String transactionType,
            double amount) {

        String sql = """
                INSERT INTO transactions
                (account_number, transaction_type, amount, balance_after)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
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
                    account.getBalance()
            );

            statement.executeUpdate();

            System.out.println(
                    "Transaction saved to MySQL successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error saving transaction to MySQL."
            );

            e.printStackTrace();
        }
    }
}