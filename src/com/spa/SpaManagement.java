package com.spa;

import com.spa.screens.*;

import java.awt.*;

public class SpaManagement {

    public static final Color BACKGROUND_COLOR = new Color(255, 220, 241);
    public static final Color BUTTON_COLOR = new Color(145, 73, 116);
    public static final Color SELECTED_BUTTON_COLOR = new Color(250, 105, 192);

    public static void main(String[] args) {
        //Launch screen
        //new LoginScreen(null).setVisible(true);
        new AdminDashboardScreen(1, "admin").setVisible(true);
        //new NonAdminDashboardScreen(2, "nonadmin").setVisible(true);
    }
}
