import javax.swing.*;
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
    int searchServiceId,searchTherapistId;
    Object searchTime;
    Date searchDate;

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
            ResultSet rs = db.executeQuery("select a.ID, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t,Service s where AppointmentDate=? and a.IsActive=? and t.ID=a.TherapistID and a.ServiceID=s.ID",new java.sql.Date(d.getYear(),d.getMonth(),d.getDate()),true);
            while (rs.next()) {
                int id=rs.getInt("ID");
                String service = rs.getString("service");
                String therapist = rs.getString("therapist");
                String time=rs.getTime("time").toString();

                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(service);
                arr.add(therapist);
                arr.add(time);
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
    private Object[][] getAppointmentsWithRestriction() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        ResultSet rs;
        List<Object> parameters=new ArrayList<>();
        StringBuilder query=new StringBuilder("select a.ID, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t,Service s where a.IsActive=true and t.ID=a.TherapistID and a.ServiceID=s.ID  ");
        if(searchDate!=null){
            parameters.add(new java.sql.Date(searchDate.getYear(),searchDate.getMonth(),searchDate.getDate()));
            query.append("and a.AppointmentDate=? ");
        }
       if(searchTime!=null){
           parameters.add(searchTime);
           query.append("and a.AppointmentTime=? ");
       }
       if(searchTherapistId>0){
           parameters.add(searchTherapistId);
           query.append("and a.TherapistID=? ");
       }
       if(searchServiceId>0){
           parameters.add(searchServiceId);
           query.append("and a.ServiceID=? ");
       }

        try {
            rs = db.executeQuery(query.toString(),parameters.toArray());
            while (rs.next()) {
                int id=rs.getInt("ID");
                String service = rs.getString("service");
                String therapistName = rs.getString("therapist");
                String time=rs.getTime("time").toString();

                ArrayList<Object> arr = new ArrayList<>();
                arr.add(id);
                arr.add(service);
                arr.add(therapistName);
                arr.add(time);
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
            searchServiceId=0;
            searchTherapistId=0;
            searchTime=null;
            searchDate=null;
            therapistNameList.setSelectedItem(null);
            serviceList.setSelectedItem(null);
            dateSelectorTxt.setDate(null);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        searchServiceId=0;
        searchTherapistId=0;
        searchTime=null;
        searchDate=null;
        therapistNameList.setSelectedItem(null);
        serviceList.setSelectedItem(null);
        dateSelectorTxt.setDate(null);
        return null;

    }
    @SuppressWarnings("unchecked")
    private void initComponents() {
        Object[][] data = getAppointments();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                        "ID","SERVICE", "THERAPIST","TIME", "OPTIONS"
                }

        ) ;
        appointmentsListTable = new javax.swing.JTable(model) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(4);
                System.out.println(column.getHeaderValue());
                column.setCellRenderer(new AppointmentsPanel.ButtonsRenderer());
                column.setCellEditor(new AppointmentsPanel.ButtonsEditor(this));
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }
        };

        addAppointment = new javax.swing.JButton();
        appointmentListTablePane = new javax.swing.JScrollPane();
        searchButton = new javax.swing.JButton();
        appointmentsDetailLabel = new javax.swing.JLabel();
        therapistNameList = new javax.swing.JComboBox<>();
        serviceList = new javax.swing.JComboBox<>();
        therapistLabel = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateSelectorTxt = new com.toedter.calendar.JDateChooser();
        dateLabel.setBackground(new java.awt.Color(216, 235, 243));
        dateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        dateLabel.setText("DATE / TIME");

        dateSelectorTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dateSelectorTxtFocusLost(evt);
            }
        });
//        timeSelectorTxt.addFocusListener(new java.awt.event.FocusAdapter() {
//            public void focusLost(java.awt.event.FocusEvent evt) {
//                timeSelectorTxtFocusLost(evt);
//            }
//        });

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

        searchButton.setBackground(new java.awt.Color(53, 183, 234));
        searchButton.setText("SEARCH");
//        searchButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                searchButtonActionPerformed(evt);
//            }
//        });

        appointmentsDetailLabel.setBackground(new java.awt.Color(216, 235, 243));
        appointmentsDetailLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        appointmentsDetailLabel.setText("TODAY'S APPOINTMENT LIST");

        therapistNameList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                therapistNameListItemStateChanged(evt);
            }
        });
        for (int i=0;i<allTherapist.size();i++) {
            therapistNameList.addItem(allTherapist.get(i));
//            if(selectedTherapist!=null && allTherapist.get(i).getId()==selectedTherapist.getId()){
//                selectedTherapistIndex=i;
//            }
        }
        for (int i=0;i<allServices.size();i++) {
            serviceList.addItem(allServices.get(i));
//            if(selectedService!=null && allServices.get(i).getId()==selectedService.getId()){
//                selectedServiceIndex=i;
//            }
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
        timeLabel=new JLabel();
        timeLabel.setBackground(new java.awt.Color(216, 235, 243));
        timeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        timeLabel.setText("TIME");
        timeLabel = new javax.swing.JLabel();
        Date date = new Date(); SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        timeSelectorTxt = new javax.swing.JSpinner(sm);
        searchLable = new javax.swing.JButton();

        JSpinner.DateEditor de = new JSpinner.DateEditor(timeSelectorTxt, "HH:mm");
        timeSelectorTxt.setEditor(de);

        searchLable.setBackground(new java.awt.Color(53, 183, 234));
        searchLable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchLable.setText("SEARCH");
        searchLable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup( layout.createSequentialGroup()
                                .addGap(0, 32, Short.MAX_VALUE)
                                .addComponent(appointmentListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 1264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                        .addGroup( layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(535, 535, 535)
                                                .addComponent(appointmentsDetailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(504, 504, 504)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(therapistLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(serviceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(67, 67, 67)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(serviceList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(therapistNameList, 0, 197, Short.MAX_VALUE)
                                                        .addComponent(dateSelectorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(timeSelectorTxt))
                                                .addGap(35, 35, 35)
                                                .addComponent(searchLable, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(therapistLabel)
                                                        .addComponent(therapistNameList, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(serviceList, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(serviceLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(dateSelectorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(timeSelectorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(dateLabel))
                                                .addGap(18, 18, 18))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(searchLable, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                .addComponent(appointmentListTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(43, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void timeSelectorTxtFocusLost(FocusEvent evt) {
        searchTime=timeSelectorTxt.getValue();
    }


    private void serviceListItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        if(serviceList.getSelectedItem()!=null)
            searchTherapistId=((Service)serviceList.getSelectedItem()).getId();
    }

    private void therapistNameListItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        if(therapistNameList.getSelectedItem()!=null)
            searchTherapistId=((Therapist)therapistNameList.getSelectedItem()).getId();
    }

    private void dateSelectorTxtFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        searchDate=dateSelectorTxt.getDate();
    }

    private void searchLableActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        appointmentsListTable= new javax.swing.JTable(new javax.swing.table.DefaultTableModel(
                getAppointmentsWithRestriction(),
                new String[]{
                        "ID","SERVICE", "THERAPIST","TIME", "OPTIONS"
                }

        )) {
            @Override
            public void updateUI() {
                super.updateUI();
                setRowHeight(36);
                setAutoCreateRowSorter(true);
                TableColumn column = getColumnModel().getColumn(4);
                System.out.println(column.getHeaderValue());
                column.setCellRenderer(new AppointmentsPanel.ButtonsRenderer());
                column.setCellEditor(new AppointmentsPanel.ButtonsEditor(this));
            }
            @Override
            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 4:
                        return true;
                    default:
                        return false;
                }
            }
        };
        appointmentsListTable.setBackground(new java.awt.Color(216, 235, 243));
        appointmentListTablePane.setViewportView(appointmentsListTable);
        validate();


    }
    private void addAppointmentActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Container container = getParent();
        getParent().remove(1);
        container.add(new AppointmentPanel(null,true), BorderLayout.CENTER, 1);
        container.validate();
        container.repaint();
    }




    // Variables declaration - do not modify
    private javax.swing.JScrollPane appointmentListTablePane;
    private javax.swing.JButton addAppointment;
    private javax.swing.JLabel appointmentsDetailLabel;
    private javax.swing.JTable appointmentsListTable;
    private javax.swing.JLabel serviceLabel;
    private JButton searchButton;
    private javax.swing.JComboBox<Service> serviceList;
    private javax.swing.JLabel therapistLabel;
    private javax.swing.JComboBox<Therapist> therapistNameList;
    private javax.swing.JLabel dateLabel;
    private com.toedter.calendar.JDateChooser dateSelectorTxt;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JSpinner timeSelectorTxt;
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
            container.add(new AppointmentPanel((int) o,true), BorderLayout.CENTER, 1);
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
            container.add(new AppointmentPanel((int) o,false), BorderLayout.CENTER, 1);
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
                container.add(new AppointmentsPanel(), BorderLayout.CENTER, 1);
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
