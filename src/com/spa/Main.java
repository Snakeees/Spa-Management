package com.spa;

import com.spa.screens.LoginScreen;

public class Main {
    public static void main(String[] args) {
        //Launch screen
        LoginScreen loginScreen = new LoginScreen(null);
        loginScreen.setVisible(true);
    }
}
