import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountsPanel extends javax.swing.JPanel {

    private javax.swing.JButton addAccount;
    private javax.swing.JScrollPane accountListTablePane;
    private javax.swing.JTable accountListTable;
    private ImageIcon editIcon;
    private ImageIcon deleteIcon;

    /**
     * Creates new form AccountsPanel
     */
    public AccountsPanel() {
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

        editIcon = (ImageIcon) UIManager.getIcon("OptionPane.warningIcon");
        deleteIcon = (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");
        addAccount = new javax.swing.JButton();

        Object[][] data = getUserAccounts();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                        "ACCOUNT ID","ACCOUNT", "ADMIN", "OPTION"
                }

        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        accountListTablePane = new javax.swing.JScrollPane();
        accountListTable = new JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(3);
                System.out.println(column.getHeaderValue());
                column.setCellRenderer(new AccountsPanel.ButtonsRenderer());
                column.setCellEditor(new AccountsPanel.ButtonsEditor(this));
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

        addAccount.setBackground(new java.awt.Color(53, 183, 234));
        addAccount.setText("ADD");
        addAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAccountActionPerformed(evt);
            }
        });

        accountListTablePane.setBackground(new java.awt.Color(216, 235, 243));
        accountListTablePane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        accountListTablePane.setInheritsPopupMenu(true);

        accountListTable.setBackground(new java.awt.Color(216, 235, 243));
        accountListTable.setPreferredScrollableViewportSize(accountListTable.getPreferredSize());


        accountListTablePane.setViewportView(accountListTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addAccount)
                                .addGap(45, 45, 45))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(accountListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(459, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(addAccount)
                                .addGap(58, 58, 58)
                                .addComponent(accountListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private Object[][] getUserAccounts() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from UserLogin");
            while (rs.next()) {
                int id=rs.getInt("ID");
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

    private void addAccountActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Container container = getParent();
        getParent().remove(1);
        container.add(new AccountPanel(null,true), BorderLayout.CENTER, 1);
        container.validate();
        container.repaint();
    }

    class EditAction extends AbstractAction {
        private final JTable table;

        protected EditAction(JTable table) {
            super("edit");
            this.table = table;
        }

        @Override public void actionPerformed(ActionEvent e) {
            // Object o = table.getModel().getValueAt(table.getSelectedRow(), 0);
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            Container container = getParent();
            getParent().remove(1);
            container.add(new AccountPanel((int) o,true), BorderLayout.CENTER, 1);
            container.validate();
            container.repaint();

        }
    }
   class DeleteAction extends AbstractAction {
        private final JTable table;

        protected DeleteAction(JTable table) {
            super("delete");
            this.table = table;
        }

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int o =(int) table.getModel().getValueAt(row, 0);
            Database db=new Database();
            db.executeUpdate("Delete from UserLogin where ID=?",o);
            Container container = getParent();
            getParent().remove(1);
            container.add(new AccountsPanel(), BorderLayout.CENTER, 1);
            container.validate();
            container.repaint();
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
                EventQueue.invokeLater(ButtonsEditor.this::fireEditingStopped);
            }
        }

        protected ButtonsEditor(JTable table) {
            super();
            ArrayList<String> options=new ArrayList<>();
            options.add("edit");
            options.add("delete");
            this.panel=new SingleItem(options);
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

        @Override public Component getTableCellEditorComponent(JTable tbl, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(tbl.getSelectionBackground());
            return panel;
        }

        @Override public Object getCellEditorValue() {
            return "";
        }
    }
    static class ButtonsRenderer implements TableCellRenderer {
        List<String> options = Arrays.asList("edit","delete");
//        public ButtonsRenderer() {
//            this.options=new ArrayList<>();
//            options.add("edit");
//            options.add("delete");
//        }

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

}

