package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDashboardScreen extends JFrame implements ActionListener {
    MyButton reports;
    MyButton therapists;
    MyButton services;
    MyButton accounts;
    MyButton changePassword;
    MyButton logout;
    JPanel content;
    ArrayList<MyButton> navButtons;
    int userId;
    public int getUserId() {
        return userId;
    }

    public AdminDashboardScreen(int userId, String userName) {
        this.userId = userId;
        // setting title of the window
        setTitle("Serenity SPA: " + userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(255, 220, 241));
        createHeaderPanel().setLocation(0, 0);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);

        // Displaying Reports page as default when admin login
        content = new Reports();
        makeActive(reports);
        // Making Reports navigation button active at the time of admin login
        content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);


        JScrollPane body = new JScrollPane();
        body.getViewport().setBackground(new Color(255, 220, 241));
        body.setBorder(BorderFactory.createEmptyBorder());
        body.setViewportView(content);
        getContentPane().add(body, BorderLayout.CENTER, 1);
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    // Creating the Header panel with navigation buttons
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);
        navButtons = new ArrayList<>();
        if (reports == null) {
            reports = getButton("report");
        }
        if (therapists == null) {
            therapists = getButton("masseuse");
        }
        if (services == null) {
            services = getButton("list");
        }
        if (accounts == null) {
            accounts = getButton("accounts");
        }
        if (changePassword == null) {
            changePassword = getButton("changepass");
        }
        if (logout == null) {
            logout = getButton("logout");
        }
        headerPanel.add(reports);
        headerPanel.add(therapists);
        headerPanel.add(services);
        headerPanel.add(accounts);
        headerPanel.add(changePassword);
        headerPanel.add(logout);
        navButtons.add(reports);
        navButtons.add(therapists);
        navButtons.add(services);
        navButtons.add(accounts);
        navButtons.add(changePassword);
        navButtons.add(logout);
        headerPanel.setLocation(0, 0);
        headerPanel.setSize(MAXIMIZED_HORIZ, 40);
        headerPanel.setBackground(new Color(145, 73, 116));
        return headerPanel;
    }
    // To create navigation buttons
    public MyButton getButton(String path) {
        NoScalingIcon icon = new NoScalingIcon(getClass().getResource("../../../images/" + path + "-48x48.png"), true);
        MyButton button = new MyButton(icon);
        button.setBackground(new Color(145, 73, 116));
        button.addActionListener(this);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // This method helps in the navigation between pages by clicking navigation buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane body = new JScrollPane();
        if (e.getSource() == reports) {
            ClearButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new Reports();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(reports);
        }
        else if (e.getSource() == therapists) {
            ClearButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new TherapistsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(therapists);
        }
        else if (e.getSource() == accounts) {
            ClearButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new AccountsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(accounts);
        }
        else if (e.getSource() == services) {
            ClearButtons();
            getContentPane().remove(1);
            setLayout(new BorderLayout());
            content = new ServicesPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            body.setBorder(BorderFactory.createEmptyBorder());
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(services);
        }
        else if (e.getSource() == changePassword) {
            ClearButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new ChangePassword(getUserId());
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
    private void ClearButtons() {
        for (MyButton button : navButtons) {
            makeInactive(button);
        }
    }
    public BufferedImage toBufferedImage(ImageIcon icon) {
        Image img = icon.getImage();
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public void makeActive(MyButton button) {
        button.setBackground(new Color(250, 105, 192));
    }
    public void makeInactive(MyButton button) {
        button.setBackground(new Color(145, 73, 116));
    }


}
