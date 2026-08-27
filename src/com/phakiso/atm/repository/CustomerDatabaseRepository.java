package com.phakiso.atm.repository;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CustomerDatabaseRepository {

    private static final String INSERT_CUSTOMER_SQL = """
            INSERT INTO customers
            (customer_id, id_number, phone_number, email, first_name, last_name)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String INSERT_ACCOUNT_SQL = """
            INSERT INTO accounts
            (account_number, customer_id, account_type, balance, pin)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_CUSTOMER_BY_ID_SQL = """
            SELECT
                c.customer_id,
                c.id_number,
                c.phone_number,
                c.email,
                c.first_name,
                c.last_name,
                a.account_number,
                a.account_type,
                a.balance,
                a.pin,
                a.failed_attempts,
                a.is_locked
            FROM customers c
            LEFT JOIN accounts a
                ON c.customer_id = a.customer_id
            WHERE c.customer_id = ?
            """;

    private static final String FIND_CUSTOMER_BY_ACCOUNT_SQL = """
            SELECT
                c.customer_id,
                c.id_number,
                c.phone_number,
                c.email,
                c.first_name,
                c.last_name,
                a.account_number,
                a.account_type,
                a.balance,
                a.pin,
                a.failed_attempts,
                a.is_locked
            FROM customers c
            INNER JOIN accounts a
                ON c.customer_id = a.customer_id
            WHERE a.account_number = ?
            """;

    private static final String FIND_ALL_CUSTOMERS_SQL =
            """
            SELECT
                c.customer_id,
                c.first_name,
                c.last_name,
                c.id_number,
                c.phone_number,
                c.email,
                a.account_number,
                a.account_type,
                a.balance,
                a.pin,
                a.failed_attempts,
                a.is_locked
            FROM customers c
            JOIN accounts a
                ON c.customer_id = a.customer_id
            ORDER BY c.customer_id
            """;

    private static final String CUSTOMER_ID_EXISTS_SQL =
            """
            SELECT 1
            FROM customers
            WHERE customer_id = ?
            """;

    private static final String ID_NUMBER_EXISTS_SQL =
            """
            SELECT 1
            FROM customers
            WHERE id_number = ?
            """;

    private static final String PHONE_NUMBER_EXISTS_SQL =
            """
            SELECT 1
            FROM customers
            WHERE phone_number = ?
            """;

    private static final String EMAIL_EXISTS_SQL =
            """
            SELECT 1
            FROM customers
            WHERE email = ?
            """;


    private static final String FIND_CUSTOMER_ID_BY_ACCOUNT_SQL =
            """
            SELECT customer_id
            FROM accounts
            WHERE account_number = ?
            """;

    private static final String DELETE_TRANSACTIONS_SQL =
            """
            DELETE FROM transactions
            WHERE account_number = ?
            """;

    private static final String DELETE_ACCOUNT_SQL =
            """
            DELETE FROM accounts
            WHERE account_number = ?
            """;

    private static final String DELETE_CUSTOMER_SQL =
            """
            DELETE FROM customers
            WHERE customer_id = ?
            """;


    // ============================================================
// CHECK CUSTOMER ID EXISTS
// ============================================================

    public boolean customerIdExists(int customerId)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             CUSTOMER_ID_EXISTS_SQL)) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }
        }
    }

    public boolean idNumberExists(String idNumber)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             ID_NUMBER_EXISTS_SQL)) {

            statement.setString(1, idNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }
        }
    }

    public boolean phoneNumberExists(String phoneNumber)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             PHONE_NUMBER_EXISTS_SQL)) {

            statement.setString(1, phoneNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }
        }
    }

    public boolean emailExists(String email)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             EMAIL_EXISTS_SQL)) {

            statement.setString(1, email);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                return resultSet.next();
            }
        }
    }



    // ============================================================
    // SAVE CUSTOMER + ACCOUNT
    // ============================================================

    public void saveCustomer(Customer customer)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                saveCustomerRecord(
                        connection,
                        customer
                );

                saveAccountRecord(
                        connection,
                        customer
                );

                connection.commit();

                System.out.println(
                        "Customer saved to MySQL successfully."
                );

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "Database transaction rolled back."
                );

                throw e;
            }
        }
    }


    // ============================================================
    // SAVE CUSTOMER RECORD
    // ============================================================

    private void saveCustomerRecord(
            Connection connection,
            Customer customer)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_CUSTOMER_SQL)) {

            statement.setInt(
                    1,
                    customer.getCustomerId()
            );

            statement.setString(
                    2,
                    customer.getIdNumber()
            );

            statement.setString(
                    3,
                    customer.getPhoneNumber()
            );

            statement.setString(
                    4,
                    customer.getEmail()
            );

            statement.setString(
                    5,
                    customer.getFirstName()
            );

            statement.setString(
                    6,
                    customer.getLastName()
            );

            statement.executeUpdate();
        }
    }


    // ============================================================
    // SAVE ACCOUNT RECORD
    // ============================================================

    private void saveAccountRecord(
            Connection connection,
            Customer customer)
            throws SQLException {

        BankAccount account =
                customer.getAccount();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_ACCOUNT_SQL)) {

            statement.setInt(
                    1,
                    account.getAccountNumber()
            );

            statement.setInt(
                    2,
                    customer.getCustomerId()
            );

            statement.setString(
                    3,
                    account.getAccountType()
            );

            statement.setDouble(
                    4,
                    account.getBalance()
            );

            statement.setString(
                    5,
                    account.getPin()
            );

            statement.executeUpdate();
        }
    }


    // ============================================================
    // FIND CUSTOMER BY CUSTOMER ID
    // ============================================================

    public Customer findCustomerById(int customerId)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_CUSTOMER_BY_ID_SQL)) {

            statement.setInt(
                    1,
                    customerId
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapCustomer(resultSet);
                }
            }
        }

        return null;
    }


    // ============================================================
    // FIND CUSTOMER BY ACCOUNT NUMBER
    // ============================================================

    public Customer findCustomerByAccountNumber(
            int accountNumber)
            throws SQLException {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_CUSTOMER_BY_ACCOUNT_SQL)) {

            statement.setInt(
                    1,
                    accountNumber
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapCustomer(resultSet);
                }
            }
        }

        return null;
    }


    public boolean deleteCustomer(int accountNumber)
            throws SQLException {

        Customer customer =
                findCustomerByAccountNumber(accountNumber);

        if (customer == null) {
            return false;
        }

        int customerId =
                customer.getCustomerId();

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                // 1. Delete transactions
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     DELETE_TRANSACTIONS_SQL)) {

                    statement.setInt(1, accountNumber);
                    statement.executeUpdate();
                }

                // 2. Delete account
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     DELETE_ACCOUNT_SQL)) {

                    statement.setInt(1, accountNumber);
                    statement.executeUpdate();
                }

                // 3. Delete customer
                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     DELETE_CUSTOMER_SQL)) {

                    statement.setInt(1, customerId);
                    statement.executeUpdate();
                }

                connection.commit();

                return true;

            } catch (SQLException e) {

                connection.rollback();
                throw e;
            }
        }
    }

    // ============================================================
    // FIND CUSTOMER BY ACCOUNT NUMBER
    // EXCLUDING SENDER
    // ============================================================

    public Customer findCustomerByAccountNumberExcludingSender(
            int accountNumber,
            Customer sender)
            throws SQLException {

        Customer customer =
                findCustomerByAccountNumber(
                        accountNumber
                );

        if (customer == null) {
            return null;
        }

        if (customer.getCustomerId() ==
                sender.getCustomerId()) {

            return null;
        }

        return customer;
    }

// ============================================================
// FIND ALL CUSTOMERS
// ============================================================

    public List<Customer> getAllCustomers()
            throws SQLException {

        List<Customer> customers = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_ALL_CUSTOMERS_SQL
                     );
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                customers.add(
                        mapCustomer(resultSet)
                );
            }
        }

        return customers;
    }

    // ============================================================
    // MAP DATABASE RESULT TO CUSTOMER
    // ============================================================

    private Customer mapCustomer(
            ResultSet resultSet)
            throws SQLException {

        BankAccount account =
                new BankAccount(
                        resultSet.getInt(
                                "account_number"
                        ),
                        resultSet.getString(
                                "account_type"
                        ),
                        resultSet.getDouble(
                                "balance"
                        ),
                        resultSet.getString(
                                "pin"
                        )
                );

        account.setFailedAttempts(
                resultSet.getInt(
                        "failed_attempts"
                )
        );

        account.setLocked(
                resultSet.getBoolean(
                        "is_locked"
                )
        );

        return new Customer(
                resultSet.getInt(
                        "customer_id"
                ),
                resultSet.getString(
                        "first_name"
                ),
                resultSet.getString(
                        "last_name"
                ),
                resultSet.getString(
                        "id_number"
                ),
                resultSet.getString(
                        "phone_number"
                ),
                resultSet.getString(
                        "email"
                ),
                account
        );
    }

}
