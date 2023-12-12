package com.spa.screens;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountsPanel extends JPanel {

    private JButton addAccount;
    private JScrollPane accountListTablePane;
    private JTable accountListTable;
    private Object[][] tableData;

        public AccountsPanel() {
        tableData = getUserAccounts();
        initComponents();
    }

        private void initComponents() {
        setBackground(new Color(216, 235, 243));

        addAccount = new JButton();
        accountListTablePane = new JScrollPane();
        DefaultTableModel model = new DefaultTableModel(
                tableData,
                new String[]{
                        "ID", "USER NAME", "ADMIN", "OPTIONS"
                }
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        accountListTable = new JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(3);
                column.setCellRenderer(new AccountsPanel.ButtonsRenderer());
                column.setCellEditor(new AccountsPanel.ButtonsEditor(this));
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 3;
            }
        };

        addAccount.setBackground(new Color(53, 183, 234));
        addAccount.setText("CREATE");
        addAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addAccountActionPerformed(evt);
            }
        });

        accountListTablePane.setBackground(new Color(216, 235, 243));
        accountListTablePane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        accountListTablePane.setInheritsPopupMenu(true);
        accountListTable.setBackground(new Color(216, 235, 243));
        accountListTable.setPreferredScrollableViewportSize(accountListTable.getPreferredSize());

        //Customizing default cell render for good look
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < accountListTable.getColumnCount() - 2; i++) {
            accountListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //Customizing default header cell render for good look
        accountListTable.getTableHeader().setDefaultRenderer(new AppointmentsPanel.BoldAndCenteredHeaderRenderer());
        JTableHeader header = accountListTable.getTableHeader();
        Dimension headerSize = header.getPreferredSize();
        headerSize.height = 40;
        header.setPreferredSize(headerSize);
        if (tableData != null && tableData.length > 0) {
            accountListTablePane.setViewportView(accountListTable);
        }
        else {
            //if data is empty, showing "No data Panel"
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No Accounts are available");
            messageLabel.setFont(new Font("Play", Font.BOLD, 16));
            noDataPanel.add(messageLabel);
            accountListTablePane.setViewportView(noDataPanel);
        }

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addAccount)
                                .addGap(30, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addComponent(accountListTablePane, GroupLayout.PREFERRED_SIZE, 1280, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(4, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addAccount)
                                .addGap(25, 25, 25)
                                .addComponent(accountListTablePane, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE))
        );
    }

 // This method fetch the list of users from DB for displaying in the table
    private Object[][] getUserAccounts() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from UserLogin");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String loginName = rs.getString("loginName");
                boolean isAdmin = rs.getBoolean("IsAdmin");
                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(loginName);
                arr.add(isAdmin);
                arr.add("");
                cells.add(arr);
            }
            Object[][] obj = new Object[cells.size()][4];
            for (int i = 0; i < cells.size(); i++) {
                obj[i][0] = cells.get(i).get(0);
                obj[i][1] = cells.get(i).get(1);
                obj[i][2] = cells.get(i).get(2);
                obj[i][3] = cells.get(i).get(3);
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //navigate to create account page when create account button is clicked
    private void addAccountActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountPanel(null, true));
        container.validate();
        container.repaint();
    }

    static class ButtonsRenderer implements TableCellRenderer {
        List<String> options = Arrays.asList("update", "delete");
        private final ButtonItems panel = new ButtonItems(options) {
            @Override
            public void updateUI() {
                super.updateUI();
                setName("Table.cellRenderer");
            }
        };

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }
    }

    class EditAction extends AbstractAction {
        private final JTable table;

        protected EditAction(JTable table) {
            super("UPDATE");
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            JViewport container = (JViewport) getParent();
            container.setView(new AccountPanel((int) o, true));
            container.validate();
            container.repaint();
        }
    }

    class DeleteAction extends AbstractAction {
        private final JTable table;

        protected DeleteAction(JTable table) {
            super("DELETE");
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
                    container.setView(new AccountsPanel());
                    container.validate();
                    container.repaint();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(getParent(), "Failed to Delete Account!");
                    exception.printStackTrace();
                }
            }
        }
    }

    class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
        protected final ButtonItems panel;
        protected final JTable table;

        protected ButtonsEditor(JTable table) {
            super();
            ArrayList<String> options = new ArrayList<>();
            options.add("Update");
            options.add("Delete");
            this.panel = new ButtonItems(options);
            this.table = table;
            List<JButton> list = panel.getButtons();
            list.get(1).setAction(new AccountsPanel.DeleteAction(table));
            list.get(0).setAction(new AccountsPanel.EditAction(table));

            EditingStopHandler handler = new EditingStopHandler();
            for (JButton b : list) {
                b.addMouseListener(handler);
                b.addActionListener(handler);
            }
            panel.addMouseListener(handler);
        }

        @Override
        public Component getTableCellEditorComponent(JTable tbl, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(tbl.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        private class EditingStopHandler extends MouseAdapter implements ActionListener {
            @Override
            public void mousePressed(MouseEvent e) {
                Object o = e.getSource();
                if (o instanceof TableCellEditor) {
                    actionPerformed(new ActionEvent(o, ActionEvent.ACTION_PERFORMED, ""));
                }
                else if (o instanceof JButton) {
                    ButtonModel m = ((JButton) e.getComponent()).getModel();
                    if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
                        panel.setBackground(table.getBackground());
                    }
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(ButtonsEditor.this::fireEditingStopped);
            }
        }
    }
}

