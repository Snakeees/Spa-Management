package com.spa.screens;

import com.spa.SpaManagement;
import com.spa.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.Objects;

import static com.spa.SpaManagement.*;

public class LoginScreen extends JFrame {

    private MyTextField userField;
    private JPasswordField passField;


    public LoginScreen(String message) {
        if (message != null) {
            JOptionPane.showMessageDialog(this, "Password updated successfully!");
        }
        createFrame();
        JPanel mainPanel = createMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER); // Use BorderLayout for JFrame
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
    }

    private void createFrame() {
        setTitle("Serenity Spa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout for auto-spacing
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        panel.add(createHeader(), gbc);
        panel.add(createUsernamePanel(), gbc);
        panel.add(createPasswordPanel(), gbc);
        panel.add(createSubmitButtonPanel(), gbc);
        setSize(500, 400);

        return panel;
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        JLabel heading = new JLabel("Serenity SPA");
        heading.setFont(new Font("Play", Font.BOLD, 40));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load and resize the image
        NoScalingIcon imgIcon = new NoScalingIcon(getClass().getResource("../../../images/logo.png"), 384, 384);
        JLabel imageLabel = new JLabel(imgIcon);
        imageLabel.setOpaque(false);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to headerPanel
        headerPanel.add(heading);
        headerPanel.add(imageLabel);
        return headerPanel;
    }

    private JPanel createUsernamePanel() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("USERNAME ");
        userLabel.setFont(new Font("Play", Font.PLAIN, 20));
        userField = new MyTextField(20);
        userPanel.add(userLabel);
        userPanel.add(userField);

        return userPanel;
    }

    private JPanel createPasswordPanel() {
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        passPanel.setOpaque(false);

        JLabel passLabel = new JLabel("PASSWORD ");
        passLabel.setFont(new Font("Play", Font.PLAIN, 20));
        passField = new JPasswordField(20);
        passField.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
        passPanel.add(passLabel);
        passPanel.add(passField);

        return passPanel;
    }

    private JPanel createSubmitButtonPanel() {
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        MyButton submitButton = new MyButton("SUBMIT");
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Play", Font.BOLD, 20));

        submitButton.addActionListener(action -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (!Objects.equals(username, "") && !password.isEmpty()) {
                String role = validateCredentials(username, password);
                int userId = getUserId(username);
                if ("admin".equals(role)) {
                    AdminDashboardScreen adminDashboardScreen = new AdminDashboardScreen(userId, username);
                    adminDashboardScreen.setVisible(true);
                    dispose();
                }
                else if ("nonadmin".equals(role)) {
                    NonAdminDashboardScreen nonAdminDashboardScreen = new NonAdminDashboardScreen(userId, username);
                    nonAdminDashboardScreen.setVisible(true);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Invalid username or password", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(LoginScreen.this,
                        "Empty username or password field", "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        submitPanel.add(submitButton);
        getRootPane().setDefaultButton(submitButton);
        return submitPanel;
    }

    public int getUserId(String username) {
        Database db = new Database();
        try {
            ResultSet rs = db.executeQuery("SELECT ID FROM UserLogin WHERE LoginName = ? limit 1", username);

            if (rs.next()) {
                int userId = rs.getInt("ID");
                db.disconnect();
                return userId;
            }
            else {
                db.disconnect();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String validateCredentials(String username, String password) {
        Database db = new Database();
        try {
            String hashedPassword = HashPassword.hashPassword(password);
            ResultSet rs = db.executeQuery("SELECT IsAdmin FROM UserLogin WHERE LoginName = ? AND Password = ? limit 1", username, hashedPassword);

            if (rs.next()) {
                boolean isAdmin = rs.getBoolean("IsAdmin");
                db.disconnect();
                return isAdmin ? "admin" : "nonadmin";
            }
            else {
                db.disconnect();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

