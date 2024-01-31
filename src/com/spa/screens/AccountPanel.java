package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import static com.spa.SpaManagement.*;

public class AccountPanel extends JPanel {

    UserLogin userLogin;
    private JLabel TitleLabel, userNameLabel, passwordLabel, confirmPasswordLabel, isAdminLabel;
    private MyTextField userNameTxt;
    private JPasswordField passwordTxt, confirmPasswordTxt;
    private JCheckBox isAdmin;
    private int CurrentUserId;

    public AccountPanel(Integer id, int currentUserId) {
        CurrentUserId = currentUserId;
        this.userLogin = getAccount(id);
        initComponents();
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
    }

    private void initComponents() {
        setupLabelsAndFields();
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(userNameLabel, userNameTxt)));

        if (userLogin == null) {
            items.add(new ArrayList<>(Arrays.asList(passwordLabel, passwordTxt)));
            items.add(new ArrayList<>(Arrays.asList(confirmPasswordLabel, confirmPasswordTxt)));
        }

        items.add(new ArrayList<>(Arrays.asList(isAdminLabel, isAdmin)));

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> handleBackAction();
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();

        InfoPanel infoPanel = new InfoPanel(TitleLabel, true, true, "UPDATE", Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }


    private void setupLabelsAndFields() {
        TitleLabel = InfoPanel.createLabel("CREATE ACCOUNT", 20);
        userNameLabel = InfoPanel.createLabel("USER NAME", 15);
        passwordLabel = InfoPanel.createLabel("PASSWORD", 15);
        confirmPasswordLabel = InfoPanel.createLabel("CONFIRM PASSWORD", 15);
        isAdminLabel = InfoPanel.createLabel("ADMIN ACCOUNT", 15);
        isAdmin = new JCheckBox();
        isAdmin.setBackground(BACKGROUND_COLOR);

        userNameTxt = new MyTextField(20);
        passwordTxt = new JPasswordField(20);
        passwordTxt.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
        confirmPasswordTxt = new JPasswordField(20);
        confirmPasswordTxt.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));

        setDetails();
    }

    private void setDetails() {
        if (userLogin != null) {
            userNameTxt.setText(userLogin.getLoginName());
            isAdmin.setSelected(userLogin.isAdmin());
            TitleLabel.setText("UPDATE ACCOUNT");
        }
    }

    private void handleSubmitAction() {
        Database db = new Database();
        if (userLogin == null) {
            if (userNameTxt.getText() != null && !userNameTxt.getText().trim().equals("") && passwordTxt.getPassword() != null && confirmPasswordTxt.getPassword() != null && !passwordTxt.getText().trim().equals("")) {
                if (passwordTxt.getText().equals(confirmPasswordTxt.getText())) {
                    passwordTxt.setText(HashPassword.hashPassword(passwordTxt.getText()));
                    try {
                        db.executeUpdate("INSERT INTO UserLogin ( LoginName, Password, IsAdmin, IsActive) VALUES(?,?,?,?)", userNameTxt.getText(), passwordTxt.getText(), isAdmin.isSelected(), true);
                        JViewport parentContainer = (JViewport) getParent();
                        parentContainer.setView(new AccountsPanel(CurrentUserId));
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
                JOptionPane.showMessageDialog(this, "All fields are required ");
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
                        container.setView(new AccountsPanel(CurrentUserId));
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
                    container.setView(new AccountsPanel(CurrentUserId));
                    container.validate();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "User name can not be empty.");
            }
        }


    }

    //This method navigates to Accounts Table page when back button is clicked
    private void handleBackAction() {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountsPanel(CurrentUserId));
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
