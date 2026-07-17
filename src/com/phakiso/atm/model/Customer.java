package com.phakiso.atm.model;

public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private BankAccount account;

    public Customer(
            int customerId,
            String firstName,
            String lastName,
            String idNumber,
            String phoneNumber,
            String email,
            BankAccount account
    ) {

        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.account = account;
    }
    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;


    }

    public BankAccount getAccount() {
        return account;
    }
}