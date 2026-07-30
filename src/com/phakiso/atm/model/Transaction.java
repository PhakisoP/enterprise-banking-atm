package com.phakiso.atm.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.phakiso.atm.service.TransactionService;

public class Transaction {
    private LocalDateTime transactionDate;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();

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