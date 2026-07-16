package com.phakiso.atm.model;

public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String phoneNumber;
    private String email;

    public Customer(int customerId,
                    String firstName,
                    String lastName,
                    String idNumber,
                    String phoneNumber,
                    String email) {

        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}