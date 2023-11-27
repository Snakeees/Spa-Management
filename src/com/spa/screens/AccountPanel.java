package com.spa.screens;

import com.spa.dto.UserLogin;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class AccountPanel extends javax.swing.JPanel {

    UserLogin userLogin;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JPasswordField confirmPasswordTxt;
    private javax.swing.JPanel container;
    private javax.swing.JPanel content;
    private javax.swing.JLabel isAdminLabel;
    private javax.swing.JCheckBox isAdmin;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JButton submit;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JTextField userNameTxt;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel addAccountLabel;
    public AccountPanel(Integer id, boolean isEditable) {
        this.userLogin = getAccount(id);
        initComponents(userLogin, isEditable);
    }

    private UserLogin getAccount(Integer id) {
        UserLogin user = null;
        try {
            Database db = new Database();
            ResultSet rs = db.executeQuery("Select * from UserLogin where ID=?", id);
            while (rs.next()) {
                user = new UserLogin();
                user.setActive(rs.getBoolean("IsActive"));
                user.setId(rs.getInt("ID"));
                user.setLoginName(rs.getString("LoginName"));
                user.setPassword(rs.getString("Password"));
                user.setAdmin(rs.getBoolean("IsAdmin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     *
     * @param userLogin
     * @param isEditable
     */
    private void initComponents(UserLogin userLogin, boolean isEditable) {

        container = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        userNameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        confirmPasswordLabel = new javax.swing.JLabel();
        userNameTxt = new javax.swing.JTextField();
        confirmPasswordTxt = new javax.swing.JPasswordField();
        passwordTxt = new javax.swing.JPasswordField();
        submit = new javax.swing.JButton();
        isAdminLabel = new javax.swing.JLabel();
        addAccountLabel = new javax.swing.JLabel();
        isAdmin = new javax.swing.JCheckBox();
        backButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(216, 235, 243));
        container.setBackground(new java.awt.Color(216, 235, 243));
        content.setBackground(new java.awt.Color(216, 235, 243));
        isAdmin.setBackground(new java.awt.Color(216, 235, 243));

        userNameLabel.setText("USER NAME");
        passwordLabel.setText("PASSWORD");
        confirmPasswordLabel.setText("RE-ENTER PASSWORD");
        addAccountLabel.setFont(new Font("Play", Font.BOLD, 20));
        userNameLabel.setFont(new Font("Play", Font.BOLD, 15));
        passwordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        isAdminLabel.setFont(new Font("Play", Font.BOLD, 15));

        submit.setBackground(new java.awt.Color(53, 183, 234));
        submit.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        submit.setLabel("CREATE");
        submit.addActionListener(this::submitActionPerformed);
        isAdminLabel.setText("ADMIN ACCOUNT");
        addAccountLabel.setText("CREATE ACCOUNT");

        backButton.setBackground(new java.awt.Color(53, 183, 234));
        backButton.setText("BACK");
        backButton.addActionListener(this::backButtonActionPerformed);

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        if (userLogin != null && isEditable) {
            userNameTxt.setText(userLogin.getLoginName());
            passwordTxt.setText("");
            isAdmin.setSelected(userLogin.isAdmin());
            confirmPasswordTxt.setText("");
            submit.setText("UPDATE");
            addAccountLabel.setText("UPDATE ACCOUNT");

            content.setLayout(contentLayout);
            contentLayout.setHorizontalGroup(
                    contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(500, 500, 500)
                                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameLabel)
                                                            .addComponent(isAdminLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(87, 87, 87)
                                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                            .addComponent(isAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(63, 63, 63)
                                                    .addComponent(backButton)
                                                    .addGap(480, 480, 480)
                                                    .addComponent(addAccountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(640, 640, 640)
                                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
            );
            contentLayout.setVerticalGroup(
                    contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(backButton))
                                    .addGap(10, 10, 10)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(addAccountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(38, 38, 38)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(userNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(userNameLabel))
                                    .addGap(21, 21, 21)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(isAdminLabel)
                                            .addComponent(isAdmin))
                                    .addGap(97, 97, 97)
                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
        } else if (isEditable) {
            content.setLayout(contentLayout);
            contentLayout.setHorizontalGroup(
                    contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(500, 500, 500)
                                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameLabel)
                                                            .addComponent(passwordLabel)
                                                            .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                                            .addComponent(isAdminLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(87, 87, 87)
                                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(confirmPasswordTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                            .addComponent(passwordTxt)
                                                            .addComponent(userNameTxt)
                                                            .addComponent(isAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(63, 63, 63)
                                                    .addComponent(backButton)
                                                    .addGap(480, 480, 480)
                                                    .addComponent(addAccountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(640, 640, 640)
                                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
            );
            contentLayout.setVerticalGroup(
                    contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(backButton))
                                    .addGap(10, 10, 10)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(addAccountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(38, 38, 38)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(userNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(userNameLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(passwordLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(confirmPasswordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(confirmPasswordLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(isAdminLabel)
                                            .addComponent(isAdmin))
                                    .addGap(97, 97, 97)
                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            );
        }


        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );


    }

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {
        Database db = new Database();
        if (userLogin == null) {
            if (userNameTxt.getText() != null && !userNameTxt.getText().trim().equals("") && passwordTxt.getPassword() != null && confirmPasswordTxt.getPassword() != null && !passwordTxt.getText().trim().equals("")) {
                if (passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
                    passwordTxt.setText(HashPassword.hashPassword(passwordTxt.getText()));
                    try {
                        db.executeUpdate("INSERT INTO UserLogin ( LoginName, Password, IsAdmin, IsActive) VALUES(?,?,?,?)", userNameTxt.getText(), passwordTxt.getText(), isAdmin.isSelected(), true);
                        JViewport parentContainer = (JViewport) getParent();
                        parentContainer.setView(new AccountsPanel());
                        parentContainer.validate();
                        parentContainer.repaint();
                        JOptionPane.showMessageDialog(this, "Account created successfully!");

                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, "Failed to Create Account!");
                        e.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Confirm password should match password ");
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Required fields can not be empty. ");
            }
        } else {
            if (userNameTxt.getText() != null && !userNameTxt.getText().trim().equals("")) {
                int result = JOptionPane.showOptionDialog(
                        getParent(),
                        "Do you want to update the Account Details?",
                        "UPDATE ALERT",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"YES", "NO"},
                        "YES");

                // Check which button was clicked
                if (result == JOptionPane.OK_OPTION) {
                    userLogin.setLoginName(userNameTxt.getText());
                    userLogin.setAdmin(isAdmin.isSelected());
                    try {
                        db.executeUpdate("update UserLogin set LoginName=? , IsAdmin=? where ID=? ;", userLogin.getLoginName(), userLogin.isAdmin(), userLogin.getId());
                        JViewport container = (JViewport) getParent();
                        container.setView(new AccountsPanel());
                        container.validate();
                        container.repaint();
                        JOptionPane.showMessageDialog(this, "Account details updated successfully!");
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, "Failed to updated Account details");
                        e.printStackTrace();
                    }
                }
                else if (result == JOptionPane.CANCEL_OPTION) {
                    JViewport container = (JViewport) getParent();
                    container.setView(new AccountsPanel());
                    container.validate();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "User name can not be empty.");
            }
        }


    }
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountsPanel());
        container.validate();
        container.repaint();
    }
}
