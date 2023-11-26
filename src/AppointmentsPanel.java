import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class AppointmentsPanel  extends javax.swing.JPanel {

    /**
     * Creates new form appointmentsPanel
     */
    ArrayList<Service> allServices;
    ArrayList<Therapist> allTherapist;

    public AppointmentsPanel() {
        updateDropdownDetails();
        initComponents();
    }
    private void updateDropdownDetails() {
        Database db=new Database();
        ResultSet fetchService=db.executeQuery("Select * from Service where IsActive=true");
        ResultSet fetchTherapist=db.executeQuery("Select * from Therapist where IsActive=true");
        try{
            allServices=new ArrayList<>();
            allTherapist=new ArrayList<>();
            while(fetchService.next()){
                allServices.add(new Service(fetchService.getInt("ID"),fetchService.getString("ServiceName")));
            }
            while(fetchTherapist.next()){
                allTherapist.add(new Therapist(fetchTherapist.getInt("ID"),fetchTherapist.getString("firstName")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private Object[][] getAppointments() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        java.util.Date d=new java.util.Date();
        try {
            ResultSet rs = db.executeQuery("select a.ID, a.ClientName as clientName, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t,Service s where AppointmentDate=? and a.IsActive=? and t.ID=a.TherapistID and a.ServiceID=s.ID",new java.sql.Date(d.getYear(),d.getMonth(),d.getDate()),true);
            while (rs.next()) {
                int id=rs.getInt("ID");
                String clientName=rs.getString("clientName");
                String service = rs.getString("service");
                String therapist = rs.getString("therapist");
                String time=rs.getTime("time").toString();

                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(clientName);
                arr.add(service);
                arr.add(therapist);
                arr.add(time);
                arr.add("");
                cells.add(arr);
            }
            Object[][] obj = new Object[cells.size()][5];
            for (int i = 0; i < cells.size(); i++) {
                obj[i][0] = cells.get(i).get(0);
                obj[i][1] = cells.get(i).get(1);
                obj[i][2] = cells.get(i).get(2);
                obj[i][3] = cells.get(i).get(3);
                obj[i][4] = cells.get(i).get(4);
            }
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    private Object[][] getAppointmentsWithRestriction() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        ResultSet rs;
        List<Object> parameters=new ArrayList<>();
        StringBuilder query=new StringBuilder("select a.ID,a.ClientName as clientName, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t,Service s where a.IsActive=true and t.ID=a.TherapistID and a.ServiceID=s.ID  ");
        Date date=dateSelectorTxt.getDate();
        if(date!=null){
            parameters.add(new java.sql.Date(date.getYear(),date.getMonth(),date.getDate()));
            query.append("and a.AppointmentDate=? ");
        }
        Therapist therapist=(Therapist)therapistNameList.getSelectedItem();
       if(therapist!=null && !(therapist).getFirstName().equals("select")){
           parameters.add(therapist.getId());
           query.append("and a.TherapistID=? ");
       }
       Service service=(Service)serviceList.getSelectedItem();
       if(service!=null && !(service).getServiceName().equals("select")){
           parameters.add(service.getId());
           query.append("and a.ServiceID=? ");
       }
       if(clientNameTxt.getText()!=null && !clientNameTxt.getText().equals("")){
           parameters.add(clientNameTxt.getText()+"%");
           query.append("and a.ClientName LIKE ?");
       }

        try {
            rs = db.executeQuery(query.toString(),parameters.toArray());
            while (rs.next()) {
                int id=rs.getInt("ID");
                String serviceName = rs.getString("service");
                String clientName=rs.getString("clientName");
                String therapistName = rs.getString("therapist");
                String time=rs.getTime("time").toString();

                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(clientName);
                arr.add(serviceName);
                arr.add(therapistName);
                arr.add(time);
                arr.add("");
                cells.add(arr);
            }
            Object[][] obj = new Object[cells.size()][5];
            for (int i = 0; i < cells.size(); i++) {
                obj[i][0] = cells.get(i).get(0);
                obj[i][1] = cells.get(i).get(1);
                obj[i][2] = cells.get(i).get(2);
                obj[i][3] = cells.get(i).get(3);
                obj[i][4] = cells.get(i).get(4);
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    @SuppressWarnings("unchecked")
    private void initComponents() {
        Object[][] data = getAppointments();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                        "APPOINTMENT ID","CLIENT NAME","SERVICE", "THERAPIST","TIME", "OPTIONS"
                }

        ) ;
        appointmentsListTable = new javax.swing.JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(5);
                column.setCellRenderer(new AppointmentsPanel.ButtonsRenderer());
                column.setCellEditor(new AppointmentsPanel.ButtonsEditor(this));
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 5:
                        return true;
                    default:
                        return false;
                }
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < appointmentsListTable.getColumnCount()-1; i++) {
            appointmentsListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        addAppointment = new javax.swing.JButton();
        appointmentListTablePane = new javax.swing.JScrollPane();
        appointmentsDetailLabel = new javax.swing.JLabel();
        therapistNameList = new javax.swing.JComboBox<>();
        serviceList = new javax.swing.JComboBox<>();
        therapistLabel = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateSelectorTxt = new com.toedter.calendar.JDateChooser();
        dateLabel.setBackground(new java.awt.Color(216, 235, 243));
        dateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dateLabel.setText("DATE");
        clientNameLabel = new javax.swing.JLabel();
        clientNameTxt = new javax.swing.JTextField();
        clientNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clientNameLabel.setText("CLIENT NAME");


        therapistLabel.setBackground(new java.awt.Color(216, 235, 243));
        therapistLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        therapistLabel.setText("THERAPIST");

        serviceLabel.setBackground(new java.awt.Color(216, 235, 243));
        serviceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        serviceLabel.setText("SERVICE");

        setBackground(new java.awt.Color(216, 235, 243));

        addAppointment.setBackground(new java.awt.Color(53, 183, 234));
        addAppointment.setText("CREATE");
        addAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAppointmentActionPerformed(evt);
            }
        });

        appointmentListTablePane.setBackground(new java.awt.Color(216, 235, 243));
        appointmentListTablePane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        appointmentListTablePane.setInheritsPopupMenu(true);



        appointmentsListTable.setBackground(new java.awt.Color(216, 235, 243));
        appointmentListTablePane.setViewportView(appointmentsListTable);

        appointmentsDetailLabel.setBackground(new java.awt.Color(216, 235, 243));
        appointmentsDetailLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        appointmentsDetailLabel.setText("TODAY'S APPOINTMENT LIST");

        therapistNameList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                therapistNameListItemStateChanged(evt);
            }
        });
        therapistNameList.addItem(new Therapist(0,"select"));
        serviceList.addItem(new Service(0,"select"));
        for (int i=0;i<allTherapist.size();i++) {
            therapistNameList.addItem(allTherapist.get(i));

        }
        for (int i=0;i<allServices.size();i++) {
            serviceList.addItem(allServices.get(i));

        }

        serviceList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                serviceListItemStateChanged(evt);
            }
        });

        therapistLabel.setBackground(new java.awt.Color(216, 235, 243));
        therapistLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        therapistLabel.setText("THERAPIST");

        serviceLabel.setBackground(new java.awt.Color(216, 235, 243));
        serviceLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        serviceLabel.setText("SERVICE");

        searchLable = new javax.swing.JButton();



        searchLable.setBackground(new java.awt.Color(53, 183, 234));
        searchLable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchLable.setText("SEARCH");
        searchLable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLableActionPerformed(evt);
            }
        });
        serviceList.setSelectedItem(null);
        therapistNameList.setSelectedItem(null);
        dateSelectorTxt.setDate(new Date());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup( layout.createSequentialGroup()
                                .addGap(535, 535, 535)
                                .addComponent(appointmentsDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(453, 453, 453)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(therapistLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(serviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clientNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(clientNameTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(serviceList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(therapistNameList, 0, 197, Short.MAX_VALUE)
                                        .addComponent(dateSelectorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(35, 35, 35)
                                .addComponent(searchLable, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(135, 135, 135))
                        .addGroup( layout.createSequentialGroup()
                                .addContainerGap(47, Short.MAX_VALUE)
                                .addComponent(appointmentListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(appointmentsDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(addAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(therapistNameList, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(therapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(searchLable, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(serviceList, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                                        .addComponent(serviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(clientNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                                        .addComponent(clientNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(dateSelectorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(appointmentListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>


    private void serviceListItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        if(serviceList.getSelectedItem()!=null && ((Service)serviceList.getSelectedItem()).getServiceName().equals("select")){
            serviceList.setSelectedItem(null);
        }
    }

    private void therapistNameListItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        if(therapistNameList.getSelectedItem()!=null && ((Therapist)therapistNameList.getSelectedItem()).getFirstName().equals("select")){
            therapistNameList.setSelectedItem(null);
        }

    }


    private void clientNameTxtItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
    }

    private void searchLableActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        appointmentsListTable= new javax.swing.JTable(new javax.swing.table.DefaultTableModel(
                getAppointmentsWithRestriction(),
                new String[]{
                        "APPOINTMENT ID","CLIENT NAME","SERVICE", "THERAPIST","TIME", "OPTIONS"
                }

        )) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(5);
                System.out.println(column.getHeaderValue());
                column.setCellRenderer(new AppointmentsPanel.ButtonsRenderer());
                column.setCellEditor(new AppointmentsPanel.ButtonsEditor(this));
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 5:
                        return true;
                    default:
                        return false;
                }
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < appointmentsListTable.getColumnCount()-1; i++) {
            appointmentsListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        appointmentsListTable.setBackground(new java.awt.Color(216, 235, 243));
        appointmentListTablePane.setViewportView(appointmentsListTable);
        validate();


    }
    private void addAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Container container = getParent();
        getParent().remove(1);
        JScrollPane body = new JScrollPane();
        body.setViewportView(new AppointmentPanel(null,true));
        container.add(body, BorderLayout.CENTER, 1);
        container.validate();
        container.repaint();
    }




    // Variables declaration - do not modify
    private javax.swing.JScrollPane appointmentListTablePane;
    private javax.swing.JButton addAppointment;
    private javax.swing.JLabel appointmentsDetailLabel;
    private javax.swing.JTable appointmentsListTable;
    private javax.swing.JLabel serviceLabel;
    private javax.swing.JComboBox<Service> serviceList;
    private javax.swing.JLabel therapistLabel;
    private javax.swing.JComboBox<Therapist> therapistNameList;
    private javax.swing.JLabel dateLabel;
    private com.toedter.calendar.JDateChooser dateSelectorTxt;
    private javax.swing.JLabel clientNameLabel;
    private javax.swing.JTextField clientNameTxt;
    private javax.swing.JButton searchLable;

    // End of variables declaration



class EditAction extends AbstractAction {
        private final JTable table;

        protected EditAction(JTable table) {
            super("UPDATE");
            this.table = table;
        }

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            Object o = table.getModel().getValueAt(row, 0);
            Container container = getParent();
            getParent().remove(1);
            JScrollPane body = new JScrollPane();
            body.setViewportView(new AppointmentPanel((int) o,true));
            container.add(body, BorderLayout.CENTER, 1);
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
            Container container = getParent();
            getParent().remove(1);
            JScrollPane body = new JScrollPane();
            body.setViewportView(new AppointmentPanel((int) o,false));
            container.add(body, BorderLayout.CENTER, 1);
            container.validate();
            container.repaint();

        }
    }
    class CancelAction extends AbstractAction {
        private final JTable table;

        protected CancelAction(JTable table) {
            super("DELETE");
            this.table = table;
        }

        @Override public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int o =(int) table.getModel().getValueAt(row, 0);
            int result = JOptionPane.showOptionDialog(
                    getParent(),
                    "Do you want to CANCEL "+((String)table.getModel().getValueAt(row, 1)+" the APPOINTMENT?"),
                    "Cancel Waring",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"OK", "Close"},
                    "OK");

            // Check which button was clicked
            if (result == JOptionPane.OK_OPTION) {
                Database db=new Database();
                db.executeUpdate("Update Appointment set IsActive=false where ID=?",o);
                Container container = getParent();
                getParent().remove(1);
                JScrollPane body=new JScrollPane();
                body.setViewportView(new AppointmentsPanel());
                container.add(body, BorderLayout.CENTER, 1);
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
                EventQueue.invokeLater(AppointmentsPanel.ButtonsEditor.this::fireEditingStopped);
            }
        }

        protected ButtonsEditor(JTable table) {
            super();
            ArrayList<String> options=new ArrayList<>();
            options.add("View");
            options.add("update");
            options.add("Cancel");
            this.panel=new SingleItem(options);
            this.table = table;
            List<JButton> list = panel.getButtons();
            list.get(2).setAction(new AppointmentsPanel.CancelAction(table));
            list.get(1).setAction(new AppointmentsPanel.EditAction(table));
            list.get(0).setAction(new AppointmentsPanel.ViewAction(table));

            AppointmentsPanel.ButtonsEditor.EditingStopHandler handler = new AppointmentsPanel.ButtonsEditor.EditingStopHandler();
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
        List<String> options = Arrays.asList("view","update","cancel");

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
