package com.phakiso.atm.repository;

import com.phakiso.atm.model.BankAccount;
import com.phakiso.atm.model.Customer;
import com.phakiso.atm.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDatabaseRepository {

    // ============================================================
    // SAVE CUSTOMER + ACCOUNT
    // ============================================================

    public void saveCustomer(Customer customer) {

        String customerSql = """
                INSERT INTO customers
                (customer_id, id_number, phone_number, email, first_name, last_name)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String accountSql = """
                INSERT INTO accounts
                (account_number, customer_id, account_type, balance, pin)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                // Save customer
                try (PreparedStatement customerStatement =
                             connection.prepareStatement(customerSql)) {

                    customerStatement.setInt(
                            1,
                            customer.getCustomerId()
                    );

                    customerStatement.setString(
                            2,
                            customer.getIdNumber()
                    );

                    customerStatement.setString(
                            3,
                            customer.getPhoneNumber()
                    );

                    customerStatement.setString(
                            4,
                            customer.getEmail()
                    );

                    customerStatement.setString(
                            5,
                            customer.getFirstName()
                    );

                    customerStatement.setString(
                            6,
                            customer.getLastName()
                    );

                    customerStatement.executeUpdate();
                }

                // Save account
                BankAccount account =
                        customer.getAccount();

                try (PreparedStatement accountStatement =
                             connection.prepareStatement(accountSql)) {

                    accountStatement.setString(
                            1,
                            String.valueOf(
                                    account.getAccountNumber()
                            )
                    );

                    accountStatement.setInt(
                            2,
                            customer.getCustomerId()
                    );

                    accountStatement.setString(
                            3,
                            account.getAccountType()
                    );

                    accountStatement.setDouble(
                            4,
                            account.getBalance()
                    );

                    accountStatement.setString(
                            5,
                            account.getPin()
                    );

                    accountStatement.executeUpdate();
                }

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

        } catch (SQLException e) {

            System.out.println(
                    "Error saving customer to MySQL."
            );

            e.printStackTrace();
        }
    }


    // ============================================================
    // FIND CUSTOMER BY CUSTOMER ID
    // ============================================================

    public Customer findCustomerById(int customerId) {

        String sql = """
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
                    a.pin

                FROM customers c

                LEFT JOIN accounts a
                    ON c.customer_id = a.customer_id

                WHERE c.customer_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapCustomer(resultSet);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding customer by ID."
            );

            e.printStackTrace();
        }

        return null;
    }


    // ============================================================
    // FIND CUSTOMER BY ACCOUNT NUMBER
    // ============================================================

    public Customer findCustomerByAccountNumber(int accountNumber) {

        String sql = """
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
                    a.pin

                FROM customers c

                INNER JOIN accounts a
                    ON c.customer_id = a.customer_id

                WHERE a.account_number = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, accountNumber);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return mapCustomer(resultSet);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding customer by account number."
            );

            e.printStackTrace();
        }

        return null;
    }


    // ============================================================
    // MAP DATABASE RESULT TO CUSTOMER OBJECT
    // ============================================================

    private Customer mapCustomer(ResultSet resultSet)
            throws SQLException {

        BankAccount account = new BankAccount(

                resultSet.getInt("account_number"),

                resultSet.getString("account_type"),

                resultSet.getDouble("balance"),

                resultSet.getString("pin")
        );

        return new Customer(

                resultSet.getInt("customer_id"),

                resultSet.getString("first_name"),

                resultSet.getString("last_name"),

                resultSet.getString("id_number"),

                resultSet.getString("phone_number"),

                resultSet.getString("email"),

                account
        );
    }
}