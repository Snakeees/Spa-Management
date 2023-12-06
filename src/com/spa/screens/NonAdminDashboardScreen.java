package com.spa.screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class NonAdminDashboardScreen extends JFrame implements ActionListener {
    JButton appointments;
    JButton profile;
    JButton attendance;
    JPanel content;
    int userId;
    ArrayList<JButton> navButtons;
    JPopupMenu popupMenu;
    JMenuItem changePassword;
    JMenuItem logout;

    public NonAdminDashboardScreen(int userId, String userName) {
        this.userId = userId;
        setTitle("Serenity SPA: " + userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode("#d8ebf3"));
        createHeaderPanel().setLocation(0, 0);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        // Displaying Appointment page as default when nonAdmin login
        content = new AppointmentsPanel();
        // Making Appointment navigation button active at the time of admin login
        makeActive(appointments);
        content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);

        JScrollPane body = new JScrollPane();
        body.setBorder(BorderFactory.createEmptyBorder());
        body.setViewportView(content);
        getContentPane().add(body, BorderLayout.CENTER, 1);
    }
// Highlight the navigation button upon navigation to the page.
    public void makeActive(JButton button) {
        button.setFont(new Font("Play", Font.BOLD, 25));
    }
// diminish the navigation button upon navigation away from the page.
    public void makeInActive(JButton button) {
        button.setFont(new Font("Play", Font.PLAIN, 20));
    }
    private void makeActiveProfile() {
        ImageIcon icon = new ImageIcon("src/images/user6.png");
        Image image = icon.getImage().getScaledInstance(48, 43, Image.SCALE_SMOOTH);
        profile.setIcon(new ImageIcon(image));
    }
    private void makeInActiveProfile() {
        ImageIcon icon = new ImageIcon("src/images/user6.png");
        Image image = icon.getImage().getScaledInstance(40, 43, Image.SCALE_SMOOTH);
        profile.setIcon(new ImageIcon(image));
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

        if(profile==null){
            ImageIcon icon = new ImageIcon("src/images/user6.png");
            Image image = icon.getImage().getScaledInstance(40, 43, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(image);
            profile = new JButton(resizedIcon);
            profile.setFont(new Font("Play", Font.PLAIN, 20));
            profile.setBackground(new Color(53, 183, 234));
            profile.addActionListener(this);
        }
        if(changePassword==null){
            changePassword = new JMenuItem("Change Password");
            changePassword.setPreferredSize(new Dimension(250,35));
            changePassword.setMaximumSize(new Dimension(250,35));
            changePassword.setMinimumSize(new Dimension(250,35));
            changePassword.setBorder(BorderFactory.createLineBorder(new Color(128, 148, 158),1));
            changePassword.setBackground(new Color(53, 183, 234));
            changePassword.addActionListener(this);
        }
        if(logout==null){
            logout = new JMenuItem("Logout");
            logout.setPreferredSize(new Dimension(250,35));
            logout.setMaximumSize(new Dimension(250,35));
            logout.setMinimumSize(new Dimension(250,35));
            logout.setBorder(BorderFactory.createLineBorder(new Color(128, 148, 158),1));
            logout.setBackground(new Color(53, 183, 234));
            logout.addActionListener(this);
        }
        if(popupMenu==null){
            popupMenu = new JPopupMenu();
            popupMenu.add(changePassword);
            popupMenu.add(logout);
            popupMenu.setMaximumSize(new Dimension(250,72));
            popupMenu.setPreferredSize(new Dimension(250,72));
            popupMenu.setMinimumSize(new Dimension(250,72));
            popupMenu.setBorder(BorderFactory.createLineBorder(new Color(128, 148, 158),1));
            popupMenu.setBackground(new Color(53, 183, 234));
        }
        headerPanel.add(appointments);
        headerPanel.add(attendance);
        headerPanel.add(profile);
        navButtons.add(appointments);
        navButtons.add(attendance);
        navButtons.add(profile);

        headerPanel.setLocation(0, 0);
        headerPanel.setSize(MAXIMIZED_HORIZ, 40);
        headerPanel.setBackground(new Color(53, 183, 234));
        return headerPanel;
    }
    // To create navigation buttons
    public JButton getButton(String label) {
        JButton button;
        button = new JButton(label);
        button.setFont(new Font("Play", Font.PLAIN, 20));
        button.setBackground(new Color(53, 183, 234));
        button.addActionListener(this);
        return button;
    }
    // diminish all the buttons
    private void updateOtherButtons() {
        for (JButton button : navButtons) {
            makeInActive(button);
        }
    }
    // This method helps in the navigation between pages by clicking navigation buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane body = new JScrollPane();
        if (e.getSource() == appointments) {
            makeInActiveProfile();
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
            makeInActiveProfile();
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
        else if (e.getSource() == profile) {
            popupMenu.show(profile, 0, profile.getHeight());
        }
        else if(e.getSource()==changePassword){
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new ChangePassword(userId);
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActiveProfile();
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

    }
}
