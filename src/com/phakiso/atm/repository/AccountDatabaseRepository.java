package com.phakiso.atm.repository;

import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDatabaseRepository {

    public boolean updateBalance(
            int accountNumber,
            double newBalance) {

        String sql = """
                UPDATE accounts
                SET balance = ?
                WHERE account_number = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);

            statement.setString(
                    2,
                    String.valueOf(accountNumber)
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating account balance in MySQL."
            );

            e.printStackTrace();

            return false;
        }
    }

    public boolean updateBalance(
            Connection connection,
            int accountNumber,
            double newBalance)
            throws SQLException {

        String sql = """
                UPDATE accounts
                SET balance = ?
                WHERE account_number = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);

            statement.setString(
                    2,
                    String.valueOf(accountNumber)
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated == 1;


        }

    }
    public boolean updateFailedAttempts(
            int accountNumber,
            int failedAttempts) {

        String sql = """
            UPDATE accounts
            SET failed_attempts = ?
            WHERE account_number = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, failedAttempts);

            statement.setString(
                    2,
                    String.valueOf(accountNumber)
            );

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating failed login attempts."
            );

            e.printStackTrace();

            return false;
        }
    }
    public boolean lockAccount(int accountNumber) {

        String sql = """
            UPDATE accounts
            SET is_locked = TRUE
            WHERE account_number = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    String.valueOf(accountNumber)
            );

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Error locking account."
            );

            e.printStackTrace();

            return false;
        }
    }



    public boolean resetLoginAttempts(int accountNumber) {

        String sql = """
            UPDATE accounts
            SET failed_attempts = 0,
                is_locked = FALSE
            WHERE account_number = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    String.valueOf(accountNumber)
            );

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Error resetting login attempts."
            );

            e.printStackTrace();

            return false;
        }
    }


    public boolean updatePin(
            Connection connection,
            int accountNumber,
            String newPin) throws SQLException {

        String sql = """
            UPDATE accounts
            SET pin = ?
            WHERE account_number = ?
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, newPin);
            statement.setString(
                    2,
                    String.valueOf(accountNumber)
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated == 1;
        }
    }
    public boolean updatePin(
            int accountNumber,
            String newPin) {

        String sql = """
            UPDATE accounts
            SET pin = ?
            WHERE account_number = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, newPin);

            statement.setString(
                    2,
                    String.valueOf(accountNumber)
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Error updating PIN in MySQL."
            );

            e.printStackTrace();

            return false;
        }
    }


    public boolean isAccountLocked(int accountNumber) {

        String sql = """
        SELECT is_locked
        FROM accounts
        WHERE account_number = ?
        """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    String.valueOf(accountNumber)
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getBoolean("is_locked");
                }

            }

        } catch (SQLException e) {

            System.out.println(
                    "Error checking account lock status."
            );

            e.printStackTrace();
        }
        return false;
     }
    }
