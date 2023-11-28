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

public class ServicesPanel extends JPanel {

    /**
     * Creates new form servicesPanel
     */
    private JButton addService;
    private JScrollPane serviceTableListPane;
    private JTable serviceTableList;
    private Object[][] tableData;

    public ServicesPanel() {
        tableData = getUserServices();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        setBackground(new Color(216, 235, 243));
        addService = new JButton();
        DefaultTableModel model = new DefaultTableModel(
                tableData,
                new String[]{
                        "ID", "SERVICE NAME", "ACTIVE", "OPTIONS"
                }
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        serviceTableListPane = new JScrollPane();
        serviceTableList = new JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(3);
                column.setCellRenderer(new ServicesPanel.ButtonsRenderer());
                column.setCellEditor(new ServicesPanel.ButtonsEditor(this));
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 3;
            }
        };

        addService.setBackground(new Color(53, 183, 234));
        addService.setText("CREATE");
        addService.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addServiceActionPerformed(evt);
            }
        });

        serviceTableListPane.setBackground(new Color(216, 235, 243));
        serviceTableListPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        serviceTableListPane.setInheritsPopupMenu(true);

        serviceTableList.setBackground(new Color(216, 235, 243));
        serviceTableList.setPreferredScrollableViewportSize(serviceTableList.getPreferredSize());

        if (tableData != null && tableData.length > 0)
            serviceTableListPane.setViewportView(serviceTableList);
        else {
            //if data is empty, showing "No data Panel"
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No Services are available");
            messageLabel.setFont(new Font("Play", Font.BOLD, 16));
            noDataPanel.add(messageLabel);
            serviceTableListPane.setViewportView(noDataPanel);
        }
        //Customizing default cell render for good look
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < serviceTableList.getColumnCount() - 2; i++) {
            serviceTableList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        //Customizing default header cell render for good look
        serviceTableList.getTableHeader().setDefaultRenderer(new AppointmentsPanel.BoldAndCenteredHeaderRenderer());
        JTableHeader header = serviceTableList.getTableHeader();
        Dimension headerSize = header.getPreferredSize();
        headerSize.height = 40;
        header.setPreferredSize(headerSize);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addService)
                                .addGap(30, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addComponent(serviceTableListPane, GroupLayout.PREFERRED_SIZE, 1280, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(4, Short.MAX_VALUE)
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addService)
                                .addGap(25, 25, 25)
                                .addComponent(serviceTableListPane, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE))
        );
    }
// fetching all services in order to display in the services table
    private Object[][] getUserServices() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Service");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String serviceName = rs.getString("ServiceName");
                boolean isActive = rs.getBoolean("IsActive");
                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(serviceName);
                arr.add(isActive);
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
    //navigate to create service page when create service button is clicked
    private void addServiceActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new ServicePanel(null, true));
        container.validate();
    }

    static class ButtonsRenderer implements TableCellRenderer {
        List<String> options = Arrays.asList("view", "update");

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
            container.setView(new ServicePanel((int) o, true));
            container.validate();
            container.repaint();
        }
    }

    class ViewAction extends AbstractAction {
        private final JTable table;

        protected ViewAction(JTable table) {
            super("VIEW");
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            JViewport container = (JViewport) getParent();
            container.setView(new ServicePanel((int) o, false));
            container.validate();
            container.repaint();

        }
    }

    class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
        protected final ButtonItems panel;
        protected final JTable table;
        protected ButtonsEditor(JTable table) {
            super();
            ArrayList<String> options = new ArrayList<>();
            options.add("View");
            options.add("update");
            this.panel = new ButtonItems(options);
            this.table = table;
            List<JButton> list = panel.getButtons();
            list.get(1).setAction(new ServicesPanel.EditAction(table));
            list.get(0).setAction(new ServicesPanel.ViewAction(table));

            ServicesPanel.ButtonsEditor.EditingStopHandler handler = new ServicesPanel.ButtonsEditor.EditingStopHandler();
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
                EventQueue.invokeLater(ServicesPanel.ButtonsEditor.this::fireEditingStopped);
            }
        }
    }
}
