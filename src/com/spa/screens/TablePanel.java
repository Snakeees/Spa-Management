package com.spa.screens;

import com.spa.SpaManagement;
import com.spa.dto.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class TablePanel extends JPanel {

    private String Title;
    private String[] Headers;
    private ActionListener CreateListener;
    private String[] ButtonNames;
    private TableFunction OptionOneFunction;
    private TableFunction OptionTwoFunction;


    private MyButton Create;
    private JScrollPane TableScrollPane;
    private JTable Table;
    private Object[][] tableData;

    public TablePanel(String title, String[] headers, Object[][] Data, ActionListener createListener, String[] buttonNames, TableFunction optionOneFunction, TableFunction optionTwoFunction) {
        Title = title;
        Headers = headers;
        tableData = Data;
        CreateListener = createListener;
        ButtonNames = buttonNames;
        OptionOneFunction = optionOneFunction;
        OptionTwoFunction = optionTwoFunction;

        initComponents();
        configureUI();
    }

    public TablePanel() {
    }
    private void initComponents() {
        Create = createCreateButton();
        JLabel label = new JLabel(Title); // Create the JLabel
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
        button.addActionListener(CreateListener);
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
        return new DefaultTableModel(tableData, Headers) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == Headers.length - 1;
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

            if (i == Headers.length - 1) {
                column.setCellRenderer(new ButtonsRenderer(ButtonNames));
                column.setCellEditor(new ButtonsEditor(table));
            } else if (tableData.length != 0 && table.getModel().getValueAt(0, i).getClass() != Boolean.class){
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

    private JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane();
        if (tableData != null && tableData.length > 0) {
            scrollPane.setViewportView(table);
        } else {
            //if data is empty, showing "No data Panel"
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No "+ Title +" are available");
            messageLabel.setFont(new Font("Play", Font.BOLD, 20));
            noDataPanel.add(messageLabel);
            noDataPanel.setBackground(BACKGROUND_COLOR);
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

        ButtonsRenderer(String[] names) {
            List<String> options = Arrays.asList(names[0], names[1]);
            this.panel = new ButtonItems(options);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }
    }

    class FirstAction extends AbstractAction {
        private final JTable table;

        FirstAction(JTable table) {
            super(ButtonNames[0].toUpperCase());
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            OptionOneFunction.call(table);
        }
    }

    class SecondAction extends AbstractAction {
        private final JTable table;

        SecondAction(JTable table) {
            super(ButtonNames[1].toUpperCase());
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            OptionTwoFunction.call(table);
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
            options.add("View");
            options.add("Update");

            ButtonItems buttonPanel = new ButtonItems(options);
            List<MyButton> buttons = buttonPanel.getButtons();

            buttons.get(0).setAction(new FirstAction(table));
            buttons.get(1).setAction(new SecondAction(table));

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
