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

public class TherapistsPanel extends JPanel {
        private JButton addTherapist;
    private JScrollPane therapistTableListPane;
    private JTable therapistTableList;
    private Object[][] tableData;

    public TherapistsPanel() {
        tableData = getTherapist();
        initComponents();
    }

        private void initComponents() {
        addTherapist = new JButton();
        Object[][] data = tableData;
        DefaultTableModel model = new DefaultTableModel(
                data,
                new String[]{
                        "ID", "THERAPIST NAME", "MOBILE", "OPTIONS"
                }) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        therapistTableListPane = new JScrollPane();
        therapistTableList = new JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(3);
                column.setCellRenderer(new TherapistsPanel.ButtonsRenderer());
                column.setCellEditor(new TherapistsPanel.ButtonsEditor(this));
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 3;
            }
        };
        setBackground(new Color(216, 235, 243));
        addTherapist.setBackground(new Color(53, 183, 234));
        addTherapist.setText("CREATE");
        addTherapist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addTherapistActionPerformed(evt);
            }
        });

        therapistTableListPane.setBackground(new Color(216, 235, 243));
        therapistTableListPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        therapistTableListPane.setInheritsPopupMenu(true);
        therapistTableList.setBackground(new Color(216, 235, 243));
        therapistTableList.setPreferredScrollableViewportSize(therapistTableList.getPreferredSize());
        if (tableData != null && tableData.length > 0)
            therapistTableListPane.setViewportView(therapistTableList);
        else {
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No Therapists are available");
            messageLabel.setFont(new Font("Play", Font.BOLD, 16));
            noDataPanel.add(messageLabel);
            therapistTableListPane.setViewportView(noDataPanel);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < therapistTableList.getColumnCount() - 1; i++) {
            therapistTableList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        therapistTableList.getTableHeader().setDefaultRenderer(new AppointmentsPanel.BoldAndCenteredHeaderRenderer());
        JTableHeader header = therapistTableList.getTableHeader();
        Dimension headerSize = header.getPreferredSize();
        headerSize.height = 40;
        header.setPreferredSize(headerSize);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addTherapist)
                                .addGap(30, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addComponent(therapistTableListPane, GroupLayout.PREFERRED_SIZE, 1280, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(4, Short.MAX_VALUE)
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addTherapist)
                                .addGap(25, 25, 25)
                                .addComponent(therapistTableListPane, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE))
        );
    }

    private Object[][] getTherapist() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Therapist");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String serviceName = rs.getString("FirstName");
                String mobile = rs.getString("PhoneNumber");
                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(serviceName);
                arr.add(mobile);
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

    private void addTherapistActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new TherapistPanel(null, true));
        container.validate();
        container.repaint();
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
            container.setView(new TherapistPanel((int) o, true));
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
            container.setView(new TherapistPanel((int) o, false));
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
            list.get(1).setAction(new TherapistsPanel.EditAction(table));
            list.get(0).setAction(new TherapistsPanel.ViewAction(table));

            TherapistsPanel.ButtonsEditor.EditingStopHandler handler = new TherapistsPanel.ButtonsEditor.EditingStopHandler();
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
                EventQueue.invokeLater(TherapistsPanel.ButtonsEditor.this::fireEditingStopped);
            }
        }
    }
}
