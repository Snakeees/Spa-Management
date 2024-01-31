package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.BUTTON_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class AdminDashboardScreen extends JFrame implements ActionListener {
    MyButton reports, therapists, services, accounts, changePassword, logout;
    JPanel content;
    ArrayList<MyButton> navButtons;
    int userId;

    public AdminDashboardScreen(int userId, String userName) {
        this.userId = userId;
        createNavigationButtons();
        setupFrame(userName);
        setupContentPanel();
    }

    private void createNavigationButtons() {
        reports = createNavButton("report");
        therapists = createNavButton("masseuse");
        services = createNavButton("list");
        accounts = createNavButton("accounts");
        changePassword = createNavButton("changepass");
        logout = createNavButton("logout");

        navButtons = new ArrayList<>(Arrays.asList(reports, therapists, services, accounts, changePassword, logout));
    }

    private MyButton createNavButton(String path) {
        NoScalingIcon icon = new NoScalingIcon(getClass().getResource("../../../images/" + path + "-inverted.png"), 48, 48);
        MyButton button = new MyButton(icon);
        button.setBackground(BUTTON_COLOR);
        button.addActionListener(this);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void setupFrame(String userName) {
        setTitle("Serenity SPA: " + userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH);
    }

    private void setupContentPanel() {
        content = new Reports();
        makeActive(reports);
        content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);

        JScrollPane body = new JScrollPane(content);
        body.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(body, BorderLayout.CENTER);
    }



    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        for (MyButton button : navButtons) {
            headerPanel.add(button);
        }
        return headerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyButton activeButton = null;
        if (e.getSource() == reports) {
            content = new Reports();
            activeButton = reports;
        } else if (e.getSource() == therapists) {
            content = new TherapistsPanel();
            activeButton = therapists;
        } else if (e.getSource() == services) {
            content = new ServicesPanel();
            activeButton = services;
        } else if (e.getSource() == accounts) {
            content = new AccountsPanel(userId);
            activeButton = accounts;
        } else if (e.getSource() == changePassword) {
            content = new ChangePassword(userId);
            activeButton = changePassword;
        } else if (e.getSource() == logout) {
            performLogout();
        }

        if (activeButton != null) {
            switchPanel(content, activeButton);
        }

    }

    private void switchPanel(JPanel newPanel, MyButton activeButton) {
        updateOtherButtons();
        content = newPanel;
        getContentPane().remove(1); // Remove the existing content panel

        JScrollPane body = new JScrollPane(content);
        body.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(body, BorderLayout.CENTER, 1);

        makeActive(activeButton);
        validate();
    }


    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginScreen(null).setVisible(true);
            dispose();
        }
    }

    private void updateOtherButtons() {
        for (MyButton button : navButtons) {
            makeInActive(button);
        }
    }


    public void makeActive(MyButton button) {
        button.setBackground(SELECTED_BUTTON_COLOR);
    }
    public void makeInActive(MyButton button) {
        button.setBackground(BUTTON_COLOR);
    }
}
