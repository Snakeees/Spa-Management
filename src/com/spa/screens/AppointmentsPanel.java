package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import java.awt.TrayIcon.MessageType;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class AppointmentsPanel extends JPanel {

    ArrayList<Service> allServices;
    ArrayList<Therapist> allTherapist;
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private Object[][] tableData;
    private JComboBox<Service> serviceList;
    private JComboBox<Therapist> therapistNameList;
    private com.toedter.calendar.JDateChooser dateSelectorTxt;
    private MyTextField clientNameTxt;
    private Timer timer;
    private boolean isTaskRunning = false;
    private TablePanel tablePanel;

    private static final String[] HEADERS = {"APPOINTMENT ID", "CLIENT NAME", "SERVICE", "THERAPIST", "DATE", "TIME", "OPTIONS"};
    String[] buttonNames = {"View", "Update"};
    TableFunction optionOneFunction = (JTable table) -> navigateToDetails(false, table);
    TableFunction optionTwoFunction = (JTable table) -> navigateToDetails(true, table);


    public AppointmentsPanel() {
        updateDropdownDetails();
        tableData = FetchFromDatabase();
        initComponents();
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
    }

    private void initComponents() {
        setBackground(BACKGROUND_COLOR);
        clientNameTxt = new MyTextField(12);
        MyButton addAppointment = new MyButton();
        therapistNameList = new JComboBox<>();
        serviceList = new JComboBox<>();
        dateSelectorTxt = new com.toedter.calendar.JDateChooser();
        dateSelectorTxt.setDateFormatString("dd-MM-yyyy");
        dateSelectorTxt.setToolTipText("DD-MM-YYYY");

        Dimension size = clientNameTxt.getPreferredSize();
        therapistNameList.setPreferredSize(size);
        serviceList.setPreferredSize(size);
        dateSelectorTxt.setPreferredSize(size);

        addAppointment.setText("CREATE");
        addAppointment.addActionListener(this::addAppointmentActionPerformed);

        therapistNameList.addItem(new Therapist(0, "Select"));
        serviceList.addItem(new Service(0, "Select"));

        // Inserting the active therapist list in the dropdown selector
        for (Therapist therapist : allTherapist) {
            therapistNameList.addItem(therapist);

        }
        // Inserting the active service list in the dropdown selector
        for (Service allService : allServices) {
            serviceList.addItem(allService);
        }

        therapistNameList.setBackground(Color.WHITE);
        therapistNameList.setFont(new Font("Play", Font.PLAIN, 14));
        serviceList.setBackground(Color.WHITE);
        serviceList.setFont(new Font("Play", Font.PLAIN, 14));

        JLabel therapistLabel = InfoPanel.createLabel("THERAPIST NAME", 16);
        JLabel serviceLabel = InfoPanel.createLabel("SERVICE", 16);
        JLabel clientNameLabel = InfoPanel.createLabel("CLIENT NAME", 16);
        JLabel dateLabel = InfoPanel.createLabel("DATE (DD-MM-YYYY)", 16);

        MyButton searchButton = new MyButton("SEARCH");
        searchButton.addActionListener(this::searchButtonActionPerformed);
        serviceList.setSelectedIndex(0);
        therapistNameList.setSelectedIndex(0);
        dateSelectorTxt.setDate(new Date());

        tablePanel = new TablePanel("Appointments", HEADERS, FetchFromDatabase(), e -> CreateActionPerformed(), buttonNames, optionOneFunction, optionTwoFunction);

        JPanel SearchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        SearchPanel.setBackground(BACKGROUND_COLOR);
        SearchPanel.add(therapistLabel);
        SearchPanel.add(therapistNameList);
        SearchPanel.add(Box.createHorizontalStrut(20));
        SearchPanel.add(serviceLabel);
        SearchPanel.add(serviceList);
        SearchPanel.add(Box.createHorizontalStrut(20));
        SearchPanel.add(clientNameLabel);
        SearchPanel.add(clientNameTxt);
        SearchPanel.add(Box.createHorizontalStrut(20));
        SearchPanel.add(dateLabel);
        SearchPanel.add(dateSelectorTxt);
        SearchPanel.add(Box.createHorizontalStrut(20));
        SearchPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(SearchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        startBackgroundTask();
    }

    private void CreateActionPerformed() {
        JViewport container = (JViewport) getParent();
        container.setView(new AppointmentPanel(null, true));
        container.validate();
        container.repaint();
    }

    private void navigateToDetails(boolean isEditMode, JTable table) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int Id = (int) table.getModel().getValueAt(row, 0);
        JViewport container = (JViewport) getParent();
        container.setView(new AppointmentPanel(Id, isEditMode));
        container.validate();
        container.repaint();
    }


    // updating the table details with the search details when search button is clicked
    private void searchButtonActionPerformed(ActionEvent evt) {
        tableData = getAppointmentsWithRestriction();
        updateTable(tableData);
        validate();
    }
    public void updateTable(Object[][] data){
        remove(tablePanel);
        tablePanel = new TablePanel("Appointments", HEADERS, data, e -> CreateActionPerformed(), buttonNames, optionOneFunction, optionTwoFunction);
        add(tablePanel, BorderLayout.CENTER);

        revalidate();
        repaint();
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

    // fetching all appointments in order to display in the appointments table
    private Object[][] FetchFromDatabase() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        java.util.Date d = new java.util.Date();
        try {
            ResultSet rs = db.executeQuery("select a.ID, a.ClientName as clientName, s.ServiceName as service, t.FirstName as therapist, a.AppointmentDate as date,a.AppointmentTime as time from Appointment a, Therapist t, Service s where AppointmentDate=? and a.IsActive=? and t.ID=a.TherapistID and a.ServiceID=s.ID ORDER BY a.AppointmentDate, a.AppointmentTime", new java.sql.Date(d.getYear(), d.getMonth(), d.getDate()), true);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String clientName = rs.getString("clientName");
                String service = rs.getString("service");
                String therapist = rs.getString("therapist");
                String appdate = dateFormatter.format(rs.getDate("date"));
                String time = timeFormatter.format(rs.getTime("time"));
                cells.add(Arrays.asList(id, clientName, service, therapist, appdate, time, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0]; // Return an empty array in case of failure
        }
        return cells.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

    }
    // fetching the appointment details with the search filter
    private Object[][] getAppointmentsWithRestriction() {
        Database db = new Database();
        List<List<Object>> cells = new ArrayList<>();
        ResultSet rs;
        List<Object> parameters = new ArrayList<>();
        StringBuilder query = new StringBuilder("select a.ID,a.ClientName as clientName, s.ServiceName as service,t.FirstName as therapist,a.AppointmentDate as date,a.AppointmentTime as time from Appointment a, Therapist t, Service s where a.IsActive=true and t.ID=a.TherapistID and a.ServiceID=s.ID  ");
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
        if (clientNameTxt.getText() != null && !clientNameTxt.getText().isEmpty()) {
            parameters.add("%" + clientNameTxt.getText() + "%");
            query.append("and a.ClientName LIKE ?");
        }
        query.append(" ORDER BY a.AppointmentDate DESC, a.AppointmentTime DESC");

        try {
            rs = db.executeQuery(query.toString(), parameters.toArray());
            while (rs.next()) {
                int id = rs.getInt("ID");
                String clientName = rs.getString("clientName");
                String serviceName = rs.getString("service");
                String therapistName = rs.getString("therapist");
                String appdate = dateFormatter.format(rs.getDate("date"));
                String time = timeFormatter.format(rs.getTime("time"));
                cells.add(Arrays.asList(id, clientName, serviceName, therapistName, appdate, time, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0]; // Return an empty array in case of failure
        }
        return cells.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);
    }

    private void startBackgroundTask() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isTaskRunning) {
                    isTaskRunning = true;
                    try {
                        checkAppointments();
                    } finally {
                        isTaskRunning = false;
                    }
                }
            }
        }, 0, 60000); // Schedule the task to run every 60 seconds
    }

    private void checkAppointments() {
        String currdate = dateFormatter.format(java.sql.Date.valueOf(LocalDate.now()));
        String currtime = timeFormatter.format(Time.valueOf(LocalTime.now()));
        Database db = new Database();
        try {
            ResultSet rs = db.executeQuery("select a.ID, a.ClientName as clientName, s.ServiceName as service, t.FirstName as therapist, a.AppointmentDate as date,a.AppointmentTime as time from Appointment a, Therapist t, Service s where t.ID=a.TherapistID and a.ServiceID=s.ID ORDER BY a.AppointmentDate, a.AppointmentTime");
            while (rs.next()) {
                String clientName = rs.getString("clientName");
                String service = rs.getString("service");
                String therapist = rs.getString("therapist");
                String appdate = dateFormatter.format(rs.getDate("date"));
                String apptime = timeFormatter.format(rs.getTime("time"));

                if(currdate.equals(appdate) && currtime.equals(apptime)) {
                    String Message = "Reminder: You have an appointment now.\n"
                            + "Client: " + clientName + "\n"
                            + "Service: " + service + "\n"
                            + "Therapist: " + therapist + "\n"
                            + "Time: " + apptime;

                    if (SystemTray.isSupported()) {
                        SystemTray tray = SystemTray.getSystemTray();
                        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("../../../images/logo.png"));
                        TrayIcon trayIcon = new TrayIcon(image, "Logo");
                        trayIcon.setImageAutoSize(true);
                        tray.add(trayIcon);
                        trayIcon.displayMessage("Appointment Notification", Message, MessageType.INFO);
                    } else {
                        JOptionPane.showMessageDialog(this, Message, "Appointment Notification", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
