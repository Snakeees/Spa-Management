package com.spa.screens;

import com.spa.dto.UserLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class AccountPanel extends JPanel {

    UserLogin userLogin;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordTxt;
    private JPanel container;
    private JPanel content;
    private JLabel isAdminLabel;
    private JCheckBox isAdmin;
    private JLabel passwordLabel;
    private JPasswordField passwordTxt;
    private JButton submit;
    private JLabel userNameLabel;
    private JTextField userNameTxt;
    private JButton backButton;
    private JLabel addAccountLabel;
    public AccountPanel(Integer id, boolean isEditable) {
        this.userLogin = getAccount(id);
        initComponents(userLogin, isEditable);
    }
    /**
     *
     * @param userLogin
     * @param isEditable
     */
    private void initComponents(UserLogin userLogin, boolean isEditable) {
        setBackground(new Color(216, 235, 243));

        container = new JPanel();
        content = new JPanel();
        userNameLabel = new JLabel();
        passwordLabel = new JLabel();
        confirmPasswordLabel = new JLabel();
        userNameTxt = new JTextField();
        confirmPasswordTxt = new JPasswordField();
        passwordTxt = new JPasswordField();
        submit = new JButton();
        isAdminLabel = new JLabel();
        addAccountLabel = new JLabel();
        isAdmin = new JCheckBox();
        backButton = new JButton();

        container.setBackground(new Color(216, 235, 243));
        content.setBackground(new Color(216, 235, 243));
        isAdmin.setBackground(new Color(216, 235, 243));
        submit.setBackground(new Color(53, 183, 234));
        submit.setBorder(BorderFactory.createLineBorder(null));
        backButton.setBackground(new Color(53, 183, 234));

        addAccountLabel.setFont(new Font("Play", Font.BOLD, 20));
        userNameLabel.setFont(new Font("Play", Font.BOLD, 15));
        passwordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        isAdminLabel.setFont(new Font("Play", Font.BOLD, 15));

        userNameLabel.setText("USER NAME");
        passwordLabel.setText("PASSWORD");
        confirmPasswordLabel.setText("RE-ENTER PASSWORD");
        submit.setLabel("CREATE");
        isAdminLabel.setText("ADMIN ACCOUNT");
        addAccountLabel.setText("CREATE ACCOUNT");
        backButton.setText("BACK");

        backButton.addActionListener(this::backButtonActionPerformed);
        submit.addActionListener(this::submitActionPerformed);


        GroupLayout contentLayout = new GroupLayout(content);
        if (userLogin != null && isEditable) {
            userNameTxt.setText(userLogin.getLoginName());
            passwordTxt.setText("");
            isAdmin.setSelected(userLogin.isAdmin());
            confirmPasswordTxt.setText("");
            submit.setText("UPDATE");
            addAccountLabel.setText("UPDATE ACCOUNT");

            content.setLayout(contentLayout);
            contentLayout.setHorizontalGroup(
                    contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(500, 500, 500)
                                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameLabel)
                                                            .addComponent(isAdminLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(87, 87, 87)
                                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameTxt, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                            .addComponent(isAdmin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(63, 63, 63)
                                                    .addComponent(backButton)
                                                    .addGap(480, 480, 480)
                                                    .addComponent(addAccountLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(640, 640, 640)
                                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))))
            );
            contentLayout.setVerticalGroup(
                    contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(backButton))
                                    .addGap(10, 10, 10)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(addAccountLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                    .addGap(38, 38, 38)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(userNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(userNameLabel))
                                    .addGap(21, 21, 21)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(isAdminLabel)
                                            .addComponent(isAdmin))
                                    .addGap(97, 97, 97)
                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
            );
        } else if (isEditable) {
            content.setLayout(contentLayout);
            contentLayout.setHorizontalGroup(
                    contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(500, 500, 500)
                                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(userNameLabel)
                                                            .addComponent(passwordLabel)
                                                            .addComponent(confirmPasswordLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                                            .addComponent(isAdminLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(87, 87, 87)
                                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(confirmPasswordTxt, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                            .addComponent(passwordTxt)
                                                            .addComponent(userNameTxt)
                                                            .addComponent(isAdmin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(63, 63, 63)
                                                    .addComponent(backButton)
                                                    .addGap(480, 480, 480)
                                                    .addComponent(addAccountLabel, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(contentLayout.createSequentialGroup()
                                                    .addGap(640, 640, 640)
                                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))))
            );
            contentLayout.setVerticalGroup(
                    contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(contentLayout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(backButton))
                                    .addGap(10, 10, 10)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(addAccountLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                    .addGap(38, 38, 38)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(userNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(userNameLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(passwordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(passwordLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(confirmPasswordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(confirmPasswordLabel))
                                    .addGap(27, 27, 27)
                                    .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(isAdminLabel)
                                            .addComponent(isAdmin))
                                    .addGap(97, 97, 97)
                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
            );
        }


        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );


    }

    // This method get execute when submit button is clicked while updating and creating account details
    private void submitActionPerformed(ActionEvent evt) {
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

    //This method navigates to Accounts Table page when back button is clicked
    private void backButtonActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountsPanel());
        container.validate();
        container.repaint();
    }

    // This method fetches the Account details when Account form page is opened in the update or edit mode
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
}
