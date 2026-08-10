package com.phakiso.atm.app;

import com.phakiso.atm.repository.CustomerRepository;
import com.phakiso.atm.service.AdminService;
import com.phakiso.atm.service.PersistenceService;

public class Main {

    public static void main(String[] args) {

        // ========================================
        // APPLICATION STARTUP
        // ========================================

        PersistenceService persistenceService =
                new PersistenceService();

        // Load the saved repository
        CustomerRepository repository =
                persistenceService.load();

        // Launch Bank Admin System
        AdminService adminService =
                new AdminService();

        adminService.displayAdminMenu(repository);
    }
}