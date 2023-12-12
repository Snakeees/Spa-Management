package com.spa.screens;

import com.spa.dto.Service;
import com.spa.dto.Therapist;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class AppointmentsPanel extends JPanel {

        ArrayList<Service> allServices;
    ArrayList<Therapist> allTherapist;
    SimpleDateFormat timeFormater = new SimpleDateFormat("HH:mm");
    private Object[][] tableData;
    private JScrollPane appointmentListTablePane;
    private JButton addAppointment;
    private JLabel appointmentsDetailLabel;
    private JTable appointmentsListTable;
    private JLabel serviceLabel;
    private JComboBox<Service> serviceList;
    private JLabel therapistLabel;
    private JComboBox<Therapist> therapistNameList;
    private JLabel dateLabel;
    private com.toedter.calendar.JDateChooser dateSelectorTxt;
    private JLabel clientNameLabel;
    private JTextField clientNameTxt;
    private JButton searchLable;



    public AppointmentsPanel() {
        updateDropdownDetails();
        tableData = getAppointments();
        initComponents();
    }

        @SuppressWarnings("unchecked")
    private void initComponents() {
        setBackground(new Color(216, 235, 243));
        appointmentListTablePane = new JScrollPane();
        updateTable(tableData);
        addAppointment = new JButton();
        appointmentsDetailLabel = new JLabel();
        therapistNameList = new JComboBox<>();
        serviceList = new JComboBox<>();
        therapistLabel = new JLabel();
        serviceLabel = new JLabel();
        dateLabel = new JLabel();
        dateSelectorTxt = new com.toedter.calendar.JDateChooser();
        dateSelectorTxt.setDateFormatString("dd-MM-yyyy");
        dateSelectorTxt.setToolTipText("DD-MM-YYYY");
        dateLabel.setBackground(new Color(216, 235, 243));
        dateLabel.setFont(new Font("Play", 1, 14));
        dateLabel.setText("DATE (DD-MM-YYYY)");
        clientNameLabel = new JLabel();
        clientNameTxt = new JTextField();
        clientNameLabel.setFont(new Font("Play", 1, 14));
        clientNameLabel.setText("CLIENT NAME");
        therapistNameList.setFont(new Font("Play", 0, 12));
        serviceList.setFont(new Font("Play", 0, 12));

        addAppointment.setBackground(new Color(53, 183, 234));
        addAppointment.setText("CREATE");
        addAppointment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addAppointmentActionPerformed(evt);
            }
        });

        appointmentListTablePane.setBackground(new Color(216, 235, 243));
        appointmentListTablePane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        appointmentListTablePane.setInheritsPopupMenu(true);

        appointmentsListTable.setBackground(new Color(216, 235, 243));
        appointmentsDetailLabel.setBackground(new Color(216, 235, 243));
        appointmentsDetailLabel.setFont(new Font("Play", 1, 18));
        appointmentsDetailLabel.setText("APPOINTMENTS LIST");

        therapistNameList.addItem(new Therapist(0, "Select"));
        serviceList.addItem(new Service(0, "Select"));

        // Inserting the active therapist list in the dropdown selector
        for (int j = 0; j < allTherapist.size(); j++) {
            therapistNameList.addItem(allTherapist.get(j));

        }
        // Inserting the active service list in the dropdown selector
        for (int j = 0; j < allServices.size(); j++) {
            serviceList.addItem(allServices.get(j));
        }

        therapistNameList.setBackground(Color.WHITE);
        serviceList.setBackground(Color.WHITE);
        therapistLabel.setBackground(new Color(216, 235, 243));
        therapistLabel.setFont(new Font("Play", 1, 14));
        therapistLabel.setText("THERAPIST NAME");

        serviceLabel.setBackground(new Color(216, 235, 243));
        serviceLabel.setFont(new Font("Play", 1, 14));
        serviceLabel.setText("SERVICE");

        searchLable = new JButton();
        searchLable.setBackground(new Color(53, 183, 234));
        searchLable.setFont(new Font("Play", 1, 12));
        searchLable.setText("SEARCH");
        searchLable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                searchLableActionPerformed(evt);
            }
        });
        serviceList.setSelectedIndex(0);
        therapistNameList.setSelectedIndex(0);
        dateSelectorTxt.setDate(new Date());

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(600,600,600)
                                .addComponent(appointmentsDetailLabel, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addAppointment, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(therapistLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(serviceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(3,3,3)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(clientNameTxt, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(serviceList, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(therapistNameList, 0, 197, Short.MAX_VALUE)
                                        .addComponent(dateSelectorTxt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(3,3,3)
                                .addComponent(searchLable, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(4, Short.MAX_VALUE)

                        )
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addComponent(appointmentListTablePane, GroupLayout.PREFERRED_SIZE, 1300, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(4, Short.MAX_VALUE)
                        )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(appointmentsDetailLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(addAppointment, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                                .addGap(10,10,10)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(therapistNameList, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(therapistLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                                .addGap(7,7,7)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(serviceList, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(serviceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(7,7,7)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(clientNameTxt, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                        .addComponent(clientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(3,3,3)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(searchLable, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)

                                        .addGap(4,4,4)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(dateSelectorTxt, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
                                .addGap(9,9,9)
                                .addComponent(appointmentListTablePane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(0, Short.MAX_VALUE))
        );
    }

    // updating the table details with the search details when search button is clicked
    private void searchLableActionPerformed(ActionEvent evt) {
        tableData = getAppointmentsWithRestriction();
       updateTable(tableData);
        validate();
    }
    public void updateTable(Object[][] data){
        appointmentsListTable = new JTable(new DefaultTableModel(
                data,
                new String[]{
                        "APPOINTMENT ID", "CLIENT NAME", "SERVICE", "THERAPIST", "TIME", "OPTIONS"
                }
        )) {
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
                return col == 5;
            }
        };
        //Customizing default cell render for good look
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < appointmentsListTable.getColumnCount() - 1; i++) {
            appointmentsListTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            appointmentsListTable.getColumnModel().getColumn(i).setMaxWidth(200);
            appointmentsListTable.getColumnModel().getColumn(i).setWidth(180);
            appointmentsListTable.getColumnModel().getColumn(i).setPreferredWidth(180);
        }
        //Customizing default header cell render for good look
        appointmentsListTable.getTableHeader().setDefaultRenderer(new BoldAndCenteredHeaderRenderer());
        JTableHeader header = appointmentsListTable.getTableHeader();
        Dimension headerSize = header.getPreferredSize();
        headerSize.height = 40;
        header.setPreferredSize(headerSize);
        appointmentsListTable.setBackground(new Color(216, 235, 243));
        if (tableData != null && tableData.length > 0)
            appointmentListTablePane.setViewportView(appointmentsListTable);
        else {
            //if data is empty, showing "No data Panel"
            JPanel noDataPanel = new JPanel();
            noDataPanel.setLayout(new FlowLayout());
            JLabel messageLabel = new JLabel("No Appointments are scheduled");
            messageLabel.setFont(new Font("Play", Font.BOLD, 16));
            noDataPanel.add(messageLabel);
            appointmentListTablePane.setViewportView(noDataPanel);
        }
    }
    //navigate to create appointment page when create appointment button is clicked
    private void addAppointmentActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AppointmentPanel(null, true));
        container.validate();
        container.repaint();
    }
    // fetching active Therapists and Services details
    private void updateDropdownDetails() {
        Database db = new Database();
        ResultSet fetchService = db.executeQuery("Select * from Service");
        ResultSet fetchTherapist = db.executeQuery("Select * from Therapist");
        try {
            allServices = new ArrayList<>();
            allTherapist = new ArrayList<>();
            while (fetchService.next()) {
                allServices.add(new Service(fetchService.getInt("ID"), fetchService.getString("ServiceName")));
            }
            while (fetchTherapist.next()) {
                allTherapist.add(new Therapist(fetchTherapist.getInt("ID"), fetchTherapist.getString("firstName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// fetching all appoinments in order to display in the appointments table
    private Object[][] getAppointments() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        java.util.Date d = new java.util.Date();
        try {
            ResultSet rs = db.executeQuery("select a.ID, a.ClientName as clientName, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t, Service s where AppointmentDate=? and a.IsActive=? and t.ID=a.TherapistID and a.ServiceID=s.ID ORDER BY a.AppointmentTime", new java.sql.Date(d.getYear(), d.getMonth(), d.getDate()), true);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String clientName = rs.getString("clientName");
                String service = rs.getString("service");
                String therapist = rs.getString("therapist");
                String time = timeFormater.format(rs.getTime("time"));

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
                for (int j = 0; j < 5; j++) {
                    obj[i][j] = cells.get(i).get(j);
                }
            }
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    // fetching the appointment details with the search filter
    private Object[][] getAppointmentsWithRestriction() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        ResultSet rs;
        List<Object> parameters = new ArrayList<>();
        StringBuilder query = new StringBuilder("select a.ID,a.ClientName as clientName, s.ServiceName as service,t.FirstName as therapist,a.AppointmentTime as time from Appointment a, Therapist t, Service s where a.IsActive=true and t.ID=a.TherapistID and a.ServiceID=s.ID  ");
        Date date = dateSelectorTxt.getDate();
        if (date != null) {
            parameters.add(new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()));
            query.append("and a.AppointmentDate=? ");
        }
        Therapist therapist = (Therapist) therapistNameList.getSelectedItem();
        if (therapist != null && !(therapist).getFirstName().equals("Select")) {
            parameters.add(therapist.getId());
            query.append("and a.TherapistID=? ");
        }
        Service service = (Service) serviceList.getSelectedItem();
        if (service != null && !(service).getServiceName().equals("Select")) {
            parameters.add(service.getId());
            query.append("and a.ServiceID=? ");
        }
        if (clientNameTxt.getText() != null && !clientNameTxt.getText().equals("")) {
            parameters.add("%" + clientNameTxt.getText() + "%");
            query.append("and a.ClientName LIKE ?");
        }
        query.append(" ORDER BY a.AppointmentTime");

        try {
            rs = db.executeQuery(query.toString(), parameters.toArray());
            while (rs.next()) {
                int id = rs.getInt("ID");
                String serviceName = rs.getString("service");
                String clientName = rs.getString("clientName");
                String therapistName = rs.getString("therapist");
                String time = timeFormater.format(rs.getTime("time"));

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

    static class ButtonsRenderer implements TableCellRenderer {
        List<String> options = Arrays.asList("view", "update", "cancel");

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

    static class BoldAndCenteredHeaderRenderer extends DefaultTableCellRenderer {
        Font boldFont = new Font(getFont().getName(), Font.BOLD, getFont().getSize() + 4);

        BoldAndCenteredHeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(boldFont);
                ((JLabel) comp).setBorder(BorderFactory.createLineBorder(Color.decode("#969999")));
            }
            return comp;
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
            container.setView(new AppointmentPanel((int) o, true));
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
            container.setView(new AppointmentPanel((int) o, false));
            container.validate();
            container.repaint();
        }
    }

    class CancelAction extends AbstractAction {
        private final JTable table;

        protected CancelAction(JTable table) {
            super("CANCEL");
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            int o = (int) table.getModel().getValueAt(row, 0);
            int result = JOptionPane.showOptionDialog(
                    getParent(),
                    "Do you want to CANCEL " + ((String) table.getModel().getValueAt(row, 1) + " the APPOINTMENT?"),
                    "Cancel Waring",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"OK", "CANCEL"},
                    "OK");
            if (result == JOptionPane.OK_OPTION) {
                try {
                    Database db = new Database();
                    db.executeUpdate("Update Appointment set IsActive=false where ID=?", o);
                    updateTable(getAppointmentsWithRestriction());
                    validate();
                    repaint();
                }catch (Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(getParent(),"Failed to cancel Appointment");
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
            options.add("View");
            options.add("update");
            options.add("Cancel");
            this.panel = new ButtonItems(options);
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
                EventQueue.invokeLater(AppointmentsPanel.ButtonsEditor.this::fireEditingStopped);
            }
        }
    }

}
