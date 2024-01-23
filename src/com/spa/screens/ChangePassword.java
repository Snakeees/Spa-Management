package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class ChangePassword extends JPanel {

    private final int userId;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordTxt;
    private JPanel container;
    private JPanel content;
    private JLabel oldPasswordLabel;
    private JPasswordField oldPasswordTxt;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordTxt;
    private MyButton submit;
    public ChangePassword(int userId) {
        this.userId = userId;
        initComponents();
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    public int getUserId() {
        return userId;
    }

        private void initComponents() {
        setBackground(new Color(255, 220, 241));

        container = new JPanel();
        content = new JPanel();
        oldPasswordLabel = new JLabel();
        confirmPasswordLabel = new JLabel();
        newPasswordLabel = new JLabel();
        confirmPasswordTxt = new JPasswordField();
        confirmPasswordTxt.setBorder(BorderFactory.createLineBorder(new Color(255, 89, 149), 3));
        oldPasswordTxt = new JPasswordField();
        oldPasswordTxt.setBorder(BorderFactory.createLineBorder(new Color(255, 89, 149), 3));
        newPasswordTxt = new JPasswordField();
        newPasswordTxt.setBorder(BorderFactory.createLineBorder(new Color(255, 89, 149), 3));
        submit = new MyButton();

        container.setBackground(new Color(255, 220, 241));
        content.setBackground(new Color(255, 220, 241));
        oldPasswordLabel.setText("OLD PASSWORD");
        confirmPasswordLabel.setText("CONFIRM NEW PASSWORD");
        newPasswordLabel.setText("NEW PASSWORD");

        oldPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        newPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordTxt.setToolTipText("");
        submit.setBackground(new Color(145, 73, 116));
        submit.setBorder(BorderFactory.createLineBorder(null));
        submit.setLabel("UPDATE");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        GroupLayout contentLayout = new GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
                contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addGap(400, 400, 400)
                                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(oldPasswordLabel)
                                                        .addComponent(newPasswordLabel)
                                                        .addComponent(confirmPasswordLabel, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                                )
                                                .addGap(80, 80, 80)
                                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(confirmPasswordTxt, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                        .addComponent(oldPasswordTxt)
                                                        .addComponent(newPasswordTxt)
                                                ))
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addGap(600, 600, 600)
                                                .addComponent(submit, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        contentLayout.setVerticalGroup(
                contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addGap(21, 21, 21)
                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(oldPasswordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(oldPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(newPasswordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(confirmPasswordTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGap(40, 40, 40)
                                .addComponent(submit, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(962, Short.MAX_VALUE))
        );
        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, GroupLayout.PREFERRED_SIZE, 1340, GroupLayout.PREFERRED_SIZE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(content, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );


    }

    private void submitActionPerformed(ActionEvent evt) {
        if (oldPasswordTxt.getText() != null && !oldPasswordTxt.getText().trim().equals("") && newPasswordTxt.getText() != null && !newPasswordTxt.getText().trim().equals("") && confirmPasswordTxt.getText() != null && !confirmPasswordTxt.getText().trim().equals("")) {
            Database db = new Database();
            boolean userWithPasswordExist = false;
            String oldHashedPassword = HashPassword.hashPassword(oldPasswordTxt.getText());
            try {
                ResultSet rs = db.executeQuery("SELECT id FROM UserLogin WHERE ID = ? AND Password = ? AND IsActive = ?", getUserId(), oldHashedPassword, true);
                if (rs.next()) {
                    userWithPasswordExist = true;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!userWithPasswordExist) {
                JOptionPane.showMessageDialog(this, "Old password did not match with existing password.");
            }
            else if (!newPasswordTxt.getText().equals(confirmPasswordTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Confirm new password should match with new password ");
            }
            else {
                int result = JOptionPane.showOptionDialog(
                        getParent(),
                        "Do you want to update your password?",
                        "UPDATE ALERT",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"YES", "NO"},
                        "YES");

                // Check which button was clicked
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String newHashedPassword = HashPassword.hashPassword(newPasswordTxt.getText());
                        db.executeUpdate("update UserLogin set password=? where ID=? ;", newHashedPassword, getUserId());
                        LoginScreen loginScreen = new LoginScreen("Password updated successfully!");
                        loginScreen.setVisible(true);
                        SwingUtilities.getWindowAncestor(this).dispose();
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, "Failed to update password ");
                        e.printStackTrace();
                    }
                }
                else {
                    confirmPasswordTxt.setText("");
                    oldPasswordTxt.setText("");
                    newPasswordTxt.setText("");
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "All fields are required");
        }
    }
}

