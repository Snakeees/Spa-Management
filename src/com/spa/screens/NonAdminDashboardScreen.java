package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class NonAdminDashboardScreen extends JFrame implements ActionListener {
    MyButton appointments;
    MyButton changePassword;
    MyButton attendance;
    MyButton logout;
    JPanel content;
    int userId;
    ArrayList<MyButton> navButtons;

    public NonAdminDashboardScreen(int userId, String userName) {
        this.userId = userId;
        setTitle("Serenity SPA: " + userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(255, 220, 241));
        createHeaderPanel().setLocation(0, 0);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        // Displaying Appointment page as default when nonAdmin login
        content = new AppointmentsPanel();
        // Making Appointment navigation button active at the time of admin login
        makeActive(appointments);
        content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);

        JScrollPane body = new JScrollPane();
        body.getViewport().setBackground(new Color(255, 220, 241));
        body.setBorder(BorderFactory.createEmptyBorder());
        body.setViewportView(content);
        getContentPane().add(body, BorderLayout.CENTER, 1);
        UIManager.put("Button.select", new Color(250, 105, 192));
    }
// Highlight the navigation button upon navigation to the page.
    public void makeActive(MyButton button) {
        button.setFont(new Font("Play", Font.BOLD, 25));
    }
// diminish the navigation button upon navigation away from the page.
    public void makeInActive(MyButton button) {
        button.setFont(new Font("Play", Font.PLAIN, 20));
    }
    // Creating the Header panel with navigation buttons
    private JPanel createHeaderPanel() {
        navButtons = new ArrayList<>();
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);
        if(appointments==null)
            appointments = getButton("Appointments");
        if(attendance==null)
            attendance = getButton("Attendance");
        if(changePassword==null)
            changePassword = getButton("Reset Password");
        if(logout==null)
            logout = getButton("Logout");
        headerPanel.add(appointments);
        headerPanel.add(attendance);
        headerPanel.add(changePassword);
        headerPanel.add(logout);
        navButtons.add(appointments);
        navButtons.add(attendance);
        navButtons.add(changePassword);
        navButtons.add(logout);
        headerPanel.setLocation(0, 0);
        headerPanel.setSize(MAXIMIZED_HORIZ, 40);
        headerPanel.setBackground(new Color(145, 73, 116));
        return headerPanel;
    }
    // To create navigation buttons
    public MyButton getButton(String label) {
        MyButton button;
        new JButton("String").setText("a");
        button = new MyButton(label);
        button.setFont(new Font("Play", Font.PLAIN, 20));
        button.setBackground(new Color(145, 73, 116));
        button.addActionListener(this);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
    // diminish all the buttons
    private void updateOtherButtons() {
        for (MyButton button : navButtons) {
            makeInActive(button);
        }
    }
    // This method helps in the navigation between pages by clicking navigation buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane body = new JScrollPane();
        body.getViewport().setBackground(new Color(255, 220, 241));
        if (e.getSource() == appointments) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new AppointmentsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(appointments);
        }
        else if (e.getSource() == attendance) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new TherapistsAttendance();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(attendance);
        }
        else if (e.getSource() == changePassword) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new ChangePassword(userId);
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(changePassword);
        }
        else if (e.getSource() == logout) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                LoginScreen loginScreen = new LoginScreen(null);
                loginScreen.setVisible(true);
                dispose();
            }
        }
        validate();
    }
}
