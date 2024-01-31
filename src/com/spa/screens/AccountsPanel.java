package com.spa.screens;

import com.spa.dto.*;
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

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class AccountsPanel extends JPanel {
    private static final String[] HEADERS = {"ID", "USER NAME", "ADMIN", "OPTIONS"};

    private MyButton Create;
    private JScrollPane TableScrollPane;
    private JTable Table;
    private Object[][] tableData;
    private int CurrentUserId;

    public AccountsPanel(int id) {
        CurrentUserId = id;
        tableData = FetchFromDatabase();
        initComponents();
        configureUI();
    }

    private void initComponents() {
        Create = createCreateButton();
        JLabel label = new JLabel("Accounts"); // Create the JLabel
        label.setFont(new Font("Play", Font.BOLD, 36));
        label.setHorizontalAlignment(JLabel.CENTER); // Center the label text
        Table = createTable();
        TableScrollPane = createScrollPane(Table);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); // Aligns the button to the right with no gaps
        buttonPanel.add(Create);
        buttonPanel.setOpaque(false); // Make the JPanel non-opaque to inherit the background color
        Create.setPreferredSize(new Dimension(120, 36)); // Example dimensions for the button

        Table.setShowVerticalLines(true); // Show vertical lines

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR); // Set the background color for the panel

        // Configure GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();

        // gbc settings for the label
        gbc.gridx = 0; // Grid x position
        gbc.gridy = 0; // Grid y position
        gbc.gridwidth = GridBagConstraints.REMAINDER; // End row
        gbc.weightx = 1.0; // Distribute space horizontally
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        gbc.insets = new Insets(10, 10, 0, 10); // Top, left, bottom, right padding
        add(label, gbc);

        // gbc settings for the button panel
        gbc.gridy = 1; // Move to the next row
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 10, 0, 10); // Reset top padding
        add(buttonPanel, gbc);

        // Adjust GridBagConstraints for the table scroll pane
        gbc.gridy = 2; // Move to the next row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // End row
        gbc.weighty = 1.0; // Distribute space vertically
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        gbc.insets = new Insets(10, 10, 10, 10); // Padding on all sides
        add(TableScrollPane, gbc);
    }

    private void configureUI() {
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
        this.setBackground(BACKGROUND_COLOR);
    }

    private MyButton createCreateButton() {
        MyButton button = new MyButton("CREATE");
        button.addActionListener(this::CreateActionPerformed);
        return button;
    }

    private JTable createTable() {
        DefaultTableModel model = createTableModel();
        JTable table = new JTable(model);
        table.setModel(model);
        table.repaint();
        configureTable(table);
        return table;
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(tableData, HEADERS) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == HEADERS.length - 1;
            }
        };
    }

    private void configureTable(JTable table) {
        // Basic table configuration
        table.setRowHeight(36);
        table.setAutoCreateRowSorter(true);
        table.setBackground(BACKGROUND_COLOR);
        table.setFillsViewportHeight(true);

        // Configure row sorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        sorter.setSortable(table.getModel().getColumnCount() - 1, false); // Make last column unsortable

        configureColumns(table);
        customizeHeader(table);
    }

    private void configureColumns(JTable table) {
        // Center alignment for columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);

            if (i == HEADERS.length - 1) {
                column.setCellRenderer(new ButtonsRenderer());
                column.setCellEditor(new ButtonsEditor(table));
            } else if (table.getModel().getValueAt(0, i).getClass() != Boolean.class){
                column.setCellRenderer(centerRenderer);
            }

            if (i == 0) { // Hide the ID column
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
            }
        }
    }

    private void customizeHeader(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(BACKGROUND_COLOR);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        Font currentFont = header.getFont();
        header.setFont(currentFont.deriveFont(Font.BOLD, currentFont.getSize() + 4));
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

    private void CreateActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AccountPanel(null, true, CurrentUserId));
        container.validate();
        container.repaint();
    }

    private JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane();
        if (tableData != null && tableData.length > 0) {
            scrollPane.setViewportView(table);
        } else {
            //if data is empty, showing "No data Panel"
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No Accounts are available");
            messageLabel.setFont(new Font("Play", Font.BOLD, 16));
            noDataPanel.add(messageLabel);
            scrollPane.setViewportView(noDataPanel);
        }
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        scrollPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        scrollPane.setInheritsPopupMenu(true);
        return scrollPane;
    }

    // Inner classes

    static class ButtonsRenderer implements TableCellRenderer {
        private final ButtonItems panel;

        ButtonsRenderer() {
            List<String> options = Arrays.asList("Update", "Delete");
            this.panel = new ButtonItems(options);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }
    }

    class UpdateAction extends AbstractAction {
        private final JTable table;

        UpdateAction(JTable table) {
            super("UPDATE");
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            navigateToDetails(true);
        }

        private void navigateToDetails(boolean isEditMode) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int Id = (int) table.getModel().getValueAt(row, 0);
            JViewport container = (JViewport) getParent();
            container.setView(new AccountPanel(Id, isEditMode, CurrentUserId));
            container.validate();
            container.repaint();
        }
    }

    class DeleteAction extends AbstractAction {
        private final JTable table;

        DeleteAction(JTable table) {
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
                    container.setView(new AccountsPanel(CurrentUserId));
                    container.validate();
                    container.repaint();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(getParent(), "Failed to Delete Account!");
                    exception.printStackTrace();
                }
            }
        }
    }

    public class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
        private final ButtonItems panel;
        private final JTable table;

        public ButtonsEditor(JTable table) {
            super();
            this.table = table;
            this.panel = initializeButtonPanel();
            addListenersToButtons();
        }

        private ButtonItems initializeButtonPanel() {
            ArrayList<String> options = new ArrayList<>();
            options.add("Update");
            options.add("Delete");

            ButtonItems buttonPanel = new ButtonItems(options);
            List<MyButton> buttons = buttonPanel.getButtons();

            buttons.get(0).setAction(new UpdateAction(table));
            buttons.get(1).setAction(new DeleteAction(table));

            return buttonPanel;
        }

        private void addListenersToButtons() {
            EditingStopHandler handler = new EditingStopHandler();
            for (MyButton button : panel.getButtons()) {
                button.addMouseListener(handler);
                button.addActionListener(handler);
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
                if (e.getSource() instanceof TableCellEditor) {
                    actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, ""));
                } else if (e.getSource() instanceof MyButton) {
                    handleButtonPress(e);
                }
            }

            private void handleButtonPress(MouseEvent e) {
                MyButton button = (MyButton) e.getComponent();
                ButtonModel model = button.getModel();
                if (model.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
                    panel.setBackground(table.getBackground());
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(ButtonsEditor.this::fireEditingStopped);
            }
        }
    }
}