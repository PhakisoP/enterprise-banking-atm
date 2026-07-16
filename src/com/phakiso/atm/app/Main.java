package com.phakiso.atm.app;

import com.phakiso.atm.model.Customer;

public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer(
                1,
                "Phakiso",
                "Pitso",
                "9001015009088",
                "0812345678",
                "phakiso@email.com"
        );

        System.out.println("Customer created successfully!");
    }

}