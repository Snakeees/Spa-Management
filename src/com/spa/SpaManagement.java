package com.spa;

import com.spa.screens.AdminDashboardScreen;
import com.spa.screens.LoginScreen;

public class SpaManagement {
    public static void main(String[] args) {
        //Launch screen
        new LoginScreen(null).setVisible(true);
        //new AdminDashboardScreen(1, "admin").setVisible(true);
    }
}
