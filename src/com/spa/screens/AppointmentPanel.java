package com.spa.screens;

import com.spa.dto.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.spa.SpaManagement.BACKGROUND_COLOR;

public class AppointmentPanel extends JPanel {

    Appointment appointment;
    Service selectedService;
    Therapist selectedTherapist;
    int selectedServiceIndex = 0, selectedTherapistIndex = 0;
    ArrayList<Service> allServices;
    ArrayList<Therapist> allTherapist;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private JLabel TitleLabel;
    private JLabel appointmentDateLabel;
    private com.toedter.calendar.JDateChooser appointmentDateTxt;
    private JLabel appointmentTimeLabel;
    private JSpinner appointmentTimeTxt;
    private MyButton backLabel;
    private JLabel clientNameLabel;
    private MyTextField clientNameTxt;
    private JLabel phoneNumberLabel;
    private MyTextField phoneNumberTxt;
    private JLabel serviceLabel;
    private JComboBox<Service> serviceListSelector;
    private MyButton submitLabel;
    private JLabel therapistLabel;
    private JComboBox<Therapist> therapistListSelector;

    public AppointmentPanel(Integer appointmentId, boolean isEditable) {
        appointment = getAppointmentDetails(appointmentId);
        updateDropdownDetails();
        initComponents(isEditable);
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    private void initComponents(boolean isEditable) {
        setupLabelsAndFields(isEditable);
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(clientNameLabel, clientNameTxt)));
        items.add(new ArrayList<>(Arrays.asList(phoneNumberLabel, phoneNumberTxt)));
        items.add(new ArrayList<>(Arrays.asList(serviceLabel, serviceListSelector)));
        items.add(new ArrayList<>(Arrays.asList(therapistLabel, therapistListSelector)));
        items.add(new ArrayList<>(Arrays.asList(appointmentDateLabel, appointmentDateTxt)));
        items.add(new ArrayList<>(Arrays.asList(appointmentTimeLabel, appointmentTimeTxt)));


        String submitButtonText = (appointment != null) ? "UPDATE" : "CREATE";

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> handleBackAction();
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();

        InfoPanel infoPanel = new InfoPanel(TitleLabel, true, isEditable, submitButtonText, Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }


    private void setupLabelsAndFields(Boolean isEditable) {
        TitleLabel = InfoPanel.createLabel("CREATE APPOINTMENT", 20);
        clientNameLabel = InfoPanel.createLabel("CLIENT NAME", 15);
        phoneNumberLabel = InfoPanel.createLabel("CLIENT PHONE NUMBER", 15);
        serviceLabel = InfoPanel.createLabel("SERVICE", 15);
        therapistLabel = InfoPanel.createLabel("THERAPIST NAME", 15);
        appointmentDateLabel = InfoPanel.createLabel("APPOINTMENT DATE (DD-MM-YYYY)", 15);
        appointmentTimeLabel = InfoPanel.createLabel("APPOINTMENT TIME 24h (HH:MM)", 15);

        clientNameTxt = new MyTextField(20);
        phoneNumberTxt = new MyTextField(20);

        serviceListSelector = new JComboBox<>();
        therapistListSelector = new JComboBox<>();
        Font SelectorFont = new Font("Play", Font.PLAIN, serviceListSelector.getFont().getSize());
        serviceListSelector.setFont(SelectorFont);
        therapistListSelector.setFont(SelectorFont);


        boolean foundTherapist = false;
        for (int i = 0; i < allTherapist.size(); i++) {
            therapistListSelector.addItem(allTherapist.get(i));
            if (selectedTherapist != null && allTherapist.get(i).getId() == selectedTherapist.getId()) {
                selectedTherapistIndex = i;
                foundTherapist = true;
            }
        }
        // To show Inactive therapist in the dropdown list in the view and update appointment page
        if (!foundTherapist && appointment != null) {
            therapistListSelector.addItem(selectedTherapist);
            selectedTherapistIndex = allTherapist.size();
        }
        boolean foundService = false;
        for (int i = 0; i < allServices.size(); i++) {
            serviceListSelector.addItem(allServices.get(i));
            if (selectedService != null && allServices.get(i).getId() == selectedService.getId()) {
                selectedServiceIndex = i;
                foundService = true;
            }
        }
        // To show Inactive service in the dropdown list in the view and update appointment page
        if (!foundService && appointment != null) {
            serviceListSelector.addItem(selectedService);
            selectedServiceIndex = allServices.size();
        }

        appointmentDateTxt = new JDateChooser();
        appointmentDateTxt.setDateFormatString("dd-MM-yyyy");

        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        appointmentTimeTxt = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(appointmentTimeTxt, "HH:mm");
        appointmentTimeTxt.setEditor(de);

        setDetails(isEditable);
    }

    private void setDetails(boolean isEditable) {
        if (appointment != null) {
            appointmentTimeTxt.setValue(appointment.getAppointmentTime());
            appointmentDateTxt.setDate(appointment.getAppointmentDate());
            clientNameTxt.setText(appointment.getClientName());
            phoneNumberTxt.setText(appointment.getClientPhoneNumber());
            serviceListSelector.setSelectedIndex(selectedServiceIndex);
            therapistListSelector.setSelectedIndex(selectedTherapistIndex);
            TitleLabel.setText(isEditable ? "UPDATE SERVICE" : "VIEW SERVICE");
        }

        appointmentDateTxt.setEnabled(isEditable);
        appointmentTimeTxt.setEnabled(isEditable);
        clientNameTxt.setEnabled(isEditable);
        phoneNumberTxt.setEnabled(isEditable);
        serviceListSelector.setEditable(isEditable);
        serviceListSelector.setEnabled(isEditable);
        therapistListSelector.setEnabled(isEditable);
        therapistListSelector.setEditable(isEditable);
    }

    //This method navigates to Appointment Table page when back button is clicked
    private void handleBackAction() {
        JViewport container = (JViewport) getParent();
        container.setView(new AppointmentsPanel());
        container.validate();
        container.repaint();
    }

    // This method get execute when submit button is clicked while updating and creating appointment details
    private void handleSubmitAction() {
        Database db = new Database();
        if (appointment == null) {
            if (clientNameTxt.getText() != null && !clientNameTxt.getText().trim().equals("") && phoneNumberTxt.getText() != null && appointmentDateTxt.getDate() != null && !phoneNumberTxt.getText().trim().equals("") && serviceListSelector.getSelectedItem() != null && therapistListSelector.getSelectedItem() != null) {
                try {
                    String modifiedDate = dateFormat.format(appointmentDateTxt.getDate().getTime());
                    db.executeUpdate("INSERT INTO Appointment ( ClientName, ClientPhoneNumber, AppointmentDate, AppointmentTime,TherapistID, ServiceID) VALUES(?,?,?,?,?,?)", clientNameTxt.getText(), phoneNumberTxt.getText(), modifiedDate, appointmentTimeTxt.getValue(), ((Therapist) therapistListSelector.getSelectedItem()).getId(), ((Service) serviceListSelector.getSelectedItem()).getId());
                    JViewport container = (JViewport) getParent();
                    container.setView(new AppointmentsPanel());
                    container.validate();
                    container.repaint();
                    JOptionPane.showMessageDialog(this, "Appointment created successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Failed to create Appointment");
                    e.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "All fields are required");
            }
        }
        else {
            if (clientNameTxt.getText() != null && !clientNameTxt.getText().trim().equals("") && phoneNumberTxt.getText() != null && appointmentDateTxt.getDate() != null && !phoneNumberTxt.getText().trim().equals("") && serviceListSelector.getSelectedItem() != null && therapistListSelector.getSelectedItem() != null) {
                int result = JOptionPane.showOptionDialog(
                        getParent(),
                        "Do you want to update the Appointment Details?",
                        "UPDATE ALERT",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"YES", "NO"},
                        "YES");

                // Check which button was clicked
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String modifiedDate = dateFormat.format(appointmentDateTxt.getDate().getTime());
                        db.executeUpdate("update Appointment set ClientName=?, ClientPhoneNumber=?, AppointmentDate=?, AppointmentTime=?,TherapistID=?, ServiceID=? where ID=? ;", clientNameTxt.getText(), phoneNumberTxt.getText(), modifiedDate, appointmentTimeTxt.getValue(), ((Therapist) therapistListSelector.getSelectedItem()).getId(), ((Service) serviceListSelector.getSelectedItem()).getId(), appointment.getId());
                        JViewport container = (JViewport) getParent();
                        container.setView(new AppointmentsPanel());
                        container.validate();
                        container.repaint();
                        JOptionPane.showMessageDialog(this, "Appointment details updated successfully!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to update Appointment details");
                    }
                }
                else if (result == JOptionPane.CANCEL_OPTION) {
                    JViewport container = (JViewport) getParent();
                    container.setView(new AppointmentsPanel());
                    container.validate();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Required details can not be empty.");
            }
        }
    }
    // fetching active Therapists and Services details
    private void updateDropdownDetails() {
        try {
            Database db = new Database();
            ResultSet fetchService = db.executeQuery("Select * from Service where IsActive=true");
            ResultSet fetchTherapist = db.executeQuery("Select * from Therapist where IsActive=true");
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
    // This method fetches the Appointment details when Appointment form page is opened in the update or edit mode
    private Appointment getAppointmentDetails(Integer appointmentId) {
        Appointment currentAppointment = null;
        try {
            Database db = new Database();
            ResultSet rs = db.executeQuery("Select * from Appointment where ID=?", appointmentId);
            ResultSet rs1 = db.executeQuery("Select s.ID as serviceId, s.ServiceName as service, t.FirstName as therapist,t.ID as therapistId from Appointment a, Therapist t, Service s where a.ID=? and a.TherapistID=t.ID and a.ServiceID=s.ID;", appointmentId);
            while (rs.next()) {
                currentAppointment = new Appointment();
                currentAppointment.setId(rs.getInt("ID"));
                currentAppointment.setClientName(rs.getString("ClientName"));
                currentAppointment.setClientPhoneNumber(rs.getString("ClientPhoneNumber"));
                currentAppointment.setAppointmentDate(rs.getDate("AppointmentDate"));
                currentAppointment.setAppointmentTime(rs.getTime("AppointmentTime"));
            }
            if (rs1.next()) {
                selectedService = new Service(rs1.getInt("serviceId"), rs1.getString("service"));
                selectedTherapist = new Therapist(rs1.getInt("therapistId"), rs1.getString("therapist"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentAppointment;
    }

}
