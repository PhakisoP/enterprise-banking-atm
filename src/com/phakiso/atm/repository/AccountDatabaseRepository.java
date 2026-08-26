package com.phakiso.atm.repository;

import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDatabaseRepository {

    private static final String UPDATE_BALANCE_SQL = """
            UPDATE accounts
            SET balance = ?
            WHERE account_number = ?
            """;

    private static final String UPDATE_FAILED_ATTEMPTS_SQL = """
            UPDATE accounts
            SET failed_attempts = ?
            WHERE account_number = ?
            """;

    private static final String LOCK_ACCOUNT_SQL = """
            UPDATE accounts
            SET is_locked = TRUE
            WHERE account_number = ?
            """;

    private static final String RESET_LOGIN_STATE_SQL = """
            UPDATE accounts
            SET failed_attempts = 0,
                is_locked = FALSE
            WHERE account_number = ?
            """;

    private static final String UPDATE_PIN_SQL = """
            UPDATE accounts
            SET pin = ?
            WHERE account_number = ?
            """;

    private static final String CHECK_ACCOUNT_LOCKED_SQL = """
            SELECT is_locked
            FROM accounts
            WHERE account_number = ?
            """;


    // ============================================================
    // UPDATE BALANCE
    // ============================================================

    public boolean updateBalance(
            int accountNumber,
            double newBalance)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            return updateBalance(
                    connection,
                    accountNumber,
                    newBalance
            );
        }
    }


    public boolean updateBalance(
            Connection connection,
            int accountNumber,
            double newBalance)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             UPDATE_BALANCE_SQL)) {

            statement.setDouble(1, newBalance);
            statement.setInt(2, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }


    // ============================================================
    // UPDATE FAILED LOGIN ATTEMPTS
    // ============================================================

    public boolean updateFailedAttempts(
            int accountNumber,
            int failedAttempts)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             UPDATE_FAILED_ATTEMPTS_SQL)) {

            statement.setInt(1, failedAttempts);
            statement.setInt(2, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }


    // ============================================================
    // LOCK ACCOUNT
    // ============================================================

    public boolean lockAccount(int accountNumber)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             LOCK_ACCOUNT_SQL)) {

            statement.setInt(1, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }


    // ============================================================
    // UNLOCK ACCOUNT
    // ============================================================

    public boolean unlockAccount(int accountNumber)
            throws SQLException {

        return resetLoginState(
                accountNumber,
                "Error unlocking account."
        );
    }


    // ============================================================
    // RESET LOGIN ATTEMPTS
    // ============================================================

    public boolean resetLoginAttempts(int accountNumber)
            throws SQLException {

        return resetLoginState(
                accountNumber,
                "Error resetting login attempts."
        );
    }


    private boolean resetLoginState(
            int accountNumber,
            String errorMessage)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             RESET_LOGIN_STATE_SQL)) {

            statement.setInt(1, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }


    // ============================================================
    // UPDATE PIN
    // ============================================================

    public boolean updatePin(
            Connection connection,
            int accountNumber,
            String newPin)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             UPDATE_PIN_SQL)) {

            statement.setString(1, newPin);
            statement.setInt(2, accountNumber);

            return statement.executeUpdate() == 1;
        }
    }


    public boolean updatePin(
            int accountNumber,
            String newPin)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            return updatePin(
                    connection,
                    accountNumber,
                    newPin
            );
        }
    }


    // ============================================================
    // CHECK ACCOUNT LOCK STATUS
    // ============================================================

    public boolean isAccountLocked(int accountNumber)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             CHECK_ACCOUNT_LOCKED_SQL)) {

            statement.setInt(1, accountNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getBoolean(
                            "is_locked"
                    );
                }
            }
        }

        return false;
    }
}