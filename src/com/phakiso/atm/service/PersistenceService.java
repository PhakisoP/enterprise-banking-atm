package com.phakiso.atm.service;

import com.phakiso.atm.repository.CustomerRepository;

import java.io.*;

public class PersistenceService {

    private static final String FILE_NAME = "customers.dat";

    public void save(CustomerRepository repository) {

        try {

            FileOutputStream fileOutputStream =
                    new FileOutputStream(FILE_NAME);

            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(repository);

            objectOutputStream.close();

            System.out.println("Data saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving data.");

            e.printStackTrace();
        }


    }
    public CustomerRepository load() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {

                System.out.println("No saved data found.");

                return new CustomerRepository();
            }

            FileInputStream fileInputStream =
                    new FileInputStream(FILE_NAME);

            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);

            CustomerRepository repository =
                    (CustomerRepository) objectInputStream.readObject();

            objectInputStream.close();

            System.out.println("Data loaded successfully.");

            return repository;

        } catch (FileNotFoundException e) {

            return new CustomerRepository();

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();

            return new CustomerRepository();
        }
    }
}