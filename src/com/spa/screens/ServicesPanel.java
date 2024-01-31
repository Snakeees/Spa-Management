package com.spa.screens;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicesPanel extends JPanel {
    private static final String[] HEADERS = {"ID", "SERVICE NAME", "ACTIVE", "OPTIONS"};

    public ServicesPanel() {
        String[] buttonNames = {"View", "Update"};
        TableFunction optionOneFunction = (JTable table) -> navigateToDetails(false, table);
        TableFunction optionTwoFunction = (JTable table) -> navigateToDetails(true, table);

        TablePanel tablePanel = new TablePanel("Services", HEADERS, FetchFromDatabase(), e -> CreateActionPerformed(), buttonNames, optionOneFunction, optionTwoFunction);

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
    }

    private void CreateActionPerformed() {
        JViewport container = (JViewport) getParent();
        container.setView(new ServicePanel(null, true));
        container.validate();
        container.repaint();
    }

    private void navigateToDetails(boolean isEditMode, JTable table) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int Id = (int) table.getModel().getValueAt(row, 0);
        JViewport container = (JViewport) getParent();
        container.setView(new ServicePanel(Id, isEditMode));
        container.validate();
        container.repaint();
    }

    private Object[][] FetchFromDatabase() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("SELECT * FROM Service");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String serviceName = rs.getString("ServiceName");
                boolean isActive = rs.getBoolean("IsActive");
                cells.add(Arrays.asList(id, serviceName, isActive, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0]; // Return an empty array in case of failure
        }
        return cells.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);
    }
}