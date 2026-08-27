package com.phakiso.atm.service;

import com.phakiso.atm.repository.AccountDatabaseRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {

    private final AccountDatabaseRepository accountDatabaseRepository =
            new AccountDatabaseRepository();


    // ============================================================
    // UPDATE BALANCE
    // ============================================================

    public boolean updateBalance(
            int accountNumber,
            double newBalance)
            throws SQLException {

        return accountDatabaseRepository.updateBalance(
                accountNumber,
                newBalance
        );
    }


    public boolean updateBalance(
            Connection connection,
            int accountNumber,
            double newBalance)
            throws SQLException {

        return accountDatabaseRepository.updateBalance(
                connection,
                accountNumber,
                newBalance
        );
    }


    // ============================================================
    // UPDATE PIN
    // ============================================================

    public boolean updatePin(
            int accountNumber,
            String newPin)
            throws SQLException {

        return accountDatabaseRepository.updatePin(
                accountNumber,
                newPin
        );
    }


    public boolean updatePin(
            Connection connection,
            int accountNumber,
            String newPin)
            throws SQLException {

        return accountDatabaseRepository.updatePin(
                connection,
                accountNumber,
                newPin
        );
    }

    // ============================================================
    // ACCOUNT NUMBER EXISTS
    // ============================================================

    public boolean accountNumberExists(
            int accountNumber)
            throws SQLException {

        return accountDatabaseRepository.accountNumberExists(
                accountNumber
        );
    }

    // ============================================================
    // LOGIN / ACCOUNT LOCKING
    // ============================================================

    public boolean isAccountLocked(
            int accountNumber)
            throws SQLException {

        return accountDatabaseRepository.isAccountLocked(
                accountNumber
        );
    }
    public boolean updateFailedAttempts(
            int accountNumber,
            int failedAttempts)
            throws SQLException {

        return accountDatabaseRepository.updateFailedAttempts(
                accountNumber,
                failedAttempts
        );
    }

    public boolean lockAccount(
            int accountNumber)
            throws SQLException {

        return accountDatabaseRepository.lockAccount(
                accountNumber
        );
    }

    public boolean resetLoginAttempts(
            int accountNumber)
            throws SQLException {

        return accountDatabaseRepository.resetLoginAttempts(
                accountNumber
        );
    }


    public boolean unlockAccount(
            int accountNumber)
            throws SQLException {

        return accountDatabaseRepository.unlockAccount(
                accountNumber
        );
    }
}