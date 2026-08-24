package com.phakiso.atm.app;

import com.phakiso.atm.service.AdminService;

public class Main {

    public static void main(String[] args) {

        // ========================================
        // APPLICATION STARTUP
        // ========================================

        // Launch Bank Admin System
        AdminService adminService =
                new AdminService();

        adminService.displayAdminMenu();
    }
}