import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicesPanel extends javax.swing.JPanel {

    /**
     * Creates new form servicesPanel
     */
    private javax.swing.JButton addService;
    private javax.swing.JScrollPane serviceTableListPane;
    private javax.swing.JTable serviceTableList;
    public ServicesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {


        addService = new javax.swing.JButton();

        Object[][] data = getUserServices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                        "ID","SERVICE NAME", "ACTIVE", "OPTIONS"
                }

        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        serviceTableListPane = new javax.swing.JScrollPane();
        serviceTableList = new JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(3);
                System.out.println(column.getHeaderValue());
                column.setCellRenderer(new ServicesPanel.ButtonsRenderer());
                column.setCellEditor(new ServicesPanel.ButtonsEditor(this));
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 3:
                        return true;
                    default:
                        return false;
                }
            }
        };


        setBackground(new java.awt.Color(216, 235, 243));

        addService.setBackground(new java.awt.Color(53, 183, 234));
        addService.setText("CREATE");
        addService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addServiceActionPerformed(evt);
            }
        });

        serviceTableListPane.setBackground(new java.awt.Color(216, 235, 243));
        serviceTableListPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        serviceTableListPane.setInheritsPopupMenu(true);

        serviceTableList.setBackground(new java.awt.Color(216, 235, 243));
        serviceTableList.setPreferredScrollableViewportSize(serviceTableList.getPreferredSize());


        serviceTableListPane.setViewportView(serviceTableList);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < serviceTableList.getColumnCount()-1; i++) {
            serviceTableList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addService)
                                .addGap(30, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(serviceTableListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addService)
                                .addGap(25, 25, 25)
                                .addComponent(serviceTableListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>

    private Object[][] getUserServices() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Service");
            while (rs.next()) {
                int id=rs.getInt("ID");
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

    private void addServiceActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        JViewport container = (JViewport)getParent();
        container.setView(new ServicePanel(null,true));
        container.validate();
    }

    class EditAction extends AbstractAction {
        private final JTable table;

        protected EditAction(JTable table) {
            super("UPDATE");
            this.table = table;
        }

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            JViewport container = (JViewport)getParent();
            container.setView(new ServicePanel((int) o,true));
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

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            JViewport container = (JViewport)getParent();
            container.setView(new ServicePanel((int) o,false));
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

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int o =(int) table.getModel().getValueAt(row, 0);
            int result = JOptionPane.showOptionDialog(
                    getParent(),
                    "Do you want to delete "+((String)table.getModel().getValueAt(row, 1)+"'s service?"),
                    "Delete Waring",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"OK", "Close"},
                    "OK");

            // Check which button was clicked
            if (result == JOptionPane.OK_OPTION) {
                Database db=new Database();
                db.executeUpdate("Delete from Service where ID=?",o);
                JViewport container = (JViewport)getParent();
                container.setView(new ServicesPanel());
                container.validate();
                container.repaint();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                System.out.println("Close button clicked");
            }

        }
    }

    class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
        protected final SingleItem panel ;
        protected final JTable table;



        private class EditingStopHandler extends MouseAdapter implements ActionListener {
            @Override public void mousePressed(MouseEvent e) {
                Object o = e.getSource();
                if (o instanceof TableCellEditor) {
                    actionPerformed(new ActionEvent(o, ActionEvent.ACTION_PERFORMED, ""));
                } else if (o instanceof JButton) {
                    ButtonModel m = ((JButton) e.getComponent()).getModel();
                    if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
                        panel.setBackground(table.getBackground());
                    }
                }
            }

            @Override public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(ServicesPanel.ButtonsEditor.this::fireEditingStopped);
            }
        }

        protected ButtonsEditor(JTable table) {
            super();
            ArrayList<String> options=new ArrayList<>();
            options.add("View");
            options.add("update");
            //removing delete option
//            options.add("Delete");
            this.panel=new SingleItem(options);
            this.table = table;
            List<JButton> list = panel.getButtons();
            //removing delete option
//            list.get(2).setAction(new ServicesPanel.DeleteAction(table));
            list.get(1).setAction(new ServicesPanel.EditAction(table));
            list.get(0).setAction(new ServicesPanel.ViewAction(table));

            ServicesPanel.ButtonsEditor.EditingStopHandler handler = new ServicesPanel.ButtonsEditor.EditingStopHandler();
            for (JButton b : list) {
                b.addMouseListener(handler);
                b.addActionListener(handler);
            }
            panel.addMouseListener(handler);
        }

        @Override public Component getTableCellEditorComponent(JTable tbl, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(tbl.getSelectionBackground());
            return panel;
        }

        @Override public Object getCellEditorValue() {
            return "";
        }
    }
    static class ButtonsRenderer implements TableCellRenderer {
        //removing delete option
//        List<String> options = Arrays.asList("view","update","delete");
List<String> options = Arrays.asList("view","update");

        private final SingleItem panel = new SingleItem(options) {
            @Override public void updateUI() {
                super.updateUI();
                setName("Table.cellRenderer");
            }
        };

        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }
    }
    // End of variables declaration
}
