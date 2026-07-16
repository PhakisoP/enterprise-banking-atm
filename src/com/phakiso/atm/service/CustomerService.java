package com.phakiso.atm.service;

import com.phakiso.atm.model.Customer;

public class CustomerService {

    public void displayCustomer(Customer customer) {

        System.out.println("--------------------------------");

        System.out.println("Customer ID : " + customer.getCustomerId());

        System.out.println("First Name : " + customer.getFirstName());

        System.out.println("Last Name : " + customer.getLastName());

        System.out.println("ID Number : " + customer.getIdNumber());

        System.out.println("Phone : " + customer.getPhoneNumber());

        System.out.println("Email : " + customer.getEmail());

    }

}