package com.phakiso.atm.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    private final LocalDateTime transactionDate;
    private final String type;
    private final double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(
            String type,
            double amount,
            LocalDateTime transactionDate) {

        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getTransactionDate() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return transactionDate.format(formatter);

    }
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}