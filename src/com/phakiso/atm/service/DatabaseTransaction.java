package com.phakiso.atm.service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a unit of database work that must be executed
 * using an existing database connection.
 *
 * The operation may throw SQLException so that the caller
 * can decide whether the transaction should be committed
 * or rolled back.
 */
@FunctionalInterface
public interface DatabaseTransaction {

    /**
     * Executes database operations using the supplied connection.
     *
     * @param connection JDBC connection used by the transaction
     * @throws SQLException if a database operation fails
     */
    void execute(Connection connection) throws SQLException;
}