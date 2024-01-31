package com.spa.screens;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountsPanel extends JPanel {
    private static final String[] HEADERS = {"ID", "USER NAME", "ADMIN", "OPTIONS"};

    private int CurrentUserId;

    public AccountsPanel(int id) {
        CurrentUserId = id;

        String[] buttonNames = {"Update", "Delete"};
        TableFunction optionOneFunction = (JTable table) -> navigateToDetails(table);
        TableFunction optionTwoFunction = (JTable table) -> DeleteUser(table);

        TablePanel tablePanel = new TablePanel("Accounts", HEADERS, FetchFromDatabase(), e -> CreateActionPerformed(), buttonNames, optionOneFunction, optionTwoFunction);

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
    }

    private void CreateActionPerformed() {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountPanel(null, CurrentUserId));
        container.validate();
        container.repaint();
    }

    private void navigateToDetails(JTable table) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int Id = (int) table.getModel().getValueAt(row, 0);
        JViewport container = (JViewport) getParent();
        container.setView(new AccountPanel(Id, CurrentUserId));
        container.validate();
        container.repaint();
    }

    private void DeleteUser(JTable table) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int o = (int) table.getModel().getValueAt(row, 0);
        int result = JOptionPane.showOptionDialog(
                getParent(),
                "Do you want to delete " + ((String) table.getModel().getValueAt(row, 1) + "'s account?"),
                "Delete Waring",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK", "Close"},
                "OK");

        // Check which button was clicked
        if (result == JOptionPane.OK_OPTION) {
            Database db = new Database();
            try {
                db.executeUpdate("Delete from UserLogin where ID=?", o);
                JViewport container = (JViewport) getParent();
                container.setView(new AccountsPanel(CurrentUserId));
                container.validate();
                container.repaint();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(getParent(), "Failed to Delete Account!");
                exception.printStackTrace();
            }
        }
    }

    private Object[][] FetchFromDatabase() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from UserLogin");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String loginName = rs.getString("loginName");
                boolean isAdmin = rs.getBoolean("IsAdmin");
                cells.add(Arrays.asList(id, loginName, isAdmin, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0]; // Return an empty array in case of failure
        }
        return cells.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);
    }
}