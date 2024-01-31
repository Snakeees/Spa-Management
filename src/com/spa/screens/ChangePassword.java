package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import static com.spa.SpaManagement.*;

public class ChangePassword extends JPanel {

    private final int userId;
    private JLabel TitleLabel, oldPasswordLabel, newPasswordLabel, confirmPasswordLabel;
    private JPasswordField oldPasswordTxt, newPasswordTxt, confirmPasswordTxt;


    public ChangePassword(int userId) {
        this.userId = userId;
        initComponents();
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
    }

    public int getUserId() {
        return userId;
    }

    private void initComponents() {
        setupLabelsAndFields();
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(oldPasswordLabel, oldPasswordTxt)));
        items.add(new ArrayList<>(Arrays.asList(newPasswordLabel, newPasswordTxt)));
        items.add(new ArrayList<>(Arrays.asList(confirmPasswordLabel, confirmPasswordTxt)));

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> {};
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();

        InfoPanel infoPanel = new InfoPanel(TitleLabel, false, true, "UPDATE", Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }


    private void setupLabelsAndFields() {
        TitleLabel = InfoPanel.createLabel("CREATE ACCOUNT", 20);
        oldPasswordLabel = InfoPanel.createLabel("USER NAME", 15);
        newPasswordLabel = InfoPanel.createLabel("PASSWORD", 15);
        confirmPasswordLabel = InfoPanel.createLabel("CONFIRM PASSWORD", 15);

        oldPasswordTxt = new JPasswordField(20);
        oldPasswordTxt.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
        newPasswordTxt = new JPasswordField(20);
        newPasswordTxt.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
        confirmPasswordTxt = new JPasswordField(20);
        confirmPasswordTxt.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
    }


    private void handleSubmitAction() {
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

