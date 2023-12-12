package com.spa.screens;

import com.spa.dto.Appointment;
import com.spa.dto.Service;
import com.spa.dto.Therapist;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentPanel extends JPanel {

        Appointment appointment;
    Service selectedService;
    Therapist selectedTherapist;
    int selectedServiceIndex = 0, selectedTherapistIndex = 0;
    ArrayList<Service> allServices;
    ArrayList<Therapist> allTherapist;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private JLabel addAppointmentLabel;
    private JLabel appointmentDateLabel;
    private com.toedter.calendar.JDateChooser appointmentDateTxt;
    private JLabel appointmentTimeLabel;
    private JSpinner appointmentTimeTxt;
    private JButton backLabel;
    private JLabel clientNameLabel;
    private JTextField clientNameTxt;
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberTxt;
    private JLabel serviceLabel;
    private JComboBox<Service> serviceListSelector;
    private JButton submitLabel;
    private JLabel therapistLabel;
    private JComboBox<Therapist> therapistListSelector;

    public AppointmentPanel(Integer appointmentId, boolean isEditable) {
        appointment = getAppointmentDetails(appointmentId);
        updateDropdownDetails();
        initComponents(isEditable);
    }


        @SuppressWarnings("unchecked")
    private void initComponents(boolean isEditable) {
        backLabel = new JButton();
        addAppointmentLabel = new JLabel();
        submitLabel = new JButton();
        clientNameLabel = new JLabel();
        phoneNumberLabel = new JLabel();
        serviceLabel = new JLabel();
        therapistLabel = new JLabel();
        appointmentDateLabel = new JLabel();
        appointmentTimeLabel = new JLabel();
        clientNameTxt = new JTextField();
        phoneNumberTxt = new JTextField();
        serviceListSelector = new JComboBox<>();
        therapistListSelector = new JComboBox<>();
        appointmentDateTxt = new JDateChooser();
        appointmentDateTxt.setDateFormatString("dd-MM-yyyy");
        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        appointmentTimeTxt = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(appointmentTimeTxt, "HH:mm");
        appointmentTimeTxt.setEditor(de);

        appointmentTimeTxt.setFont(new Font("Play", 0, 12));
        therapistListSelector.setFont(new Font("Play", 0, 12));
        therapistListSelector.setBackground(Color.WHITE);
        serviceListSelector.setBackground(Color.WHITE);
        serviceListSelector.setFont(new Font("Play", 0, 12));
        setBackground(new Color(216, 235, 243));
        backLabel.setBackground(new Color(53, 183, 234));
        backLabel.setFont(new Font("Play", 1, 12));
        backLabel.setText("BACK");
        backLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backLabelActionPerformed(evt);
            }
        });

        addAppointmentLabel.setBackground(new Color(216, 235, 243));
        addAppointmentLabel.setFont(new Font("Play", 1, 24));
        addAppointmentLabel.setText("CREATE APPOINTMENT");
        submitLabel.setBackground(new Color(53, 183, 234));
        submitLabel.setFont(new Font("Play", 1, 12));
        submitLabel.setText("CREATE");
        submitLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitLabelActionPerformed(evt);
            }
        });

        clientNameLabel.setBackground(new Color(216, 235, 243));
        clientNameLabel.setFont(new Font("Play", 1, 12));
        clientNameLabel.setText("CLIENT NAME");

        phoneNumberLabel.setBackground(new Color(216, 235, 243));
        phoneNumberLabel.setFont(new Font("Play", 1, 12));
        phoneNumberLabel.setText("CLIENT PHONE NUMBER");

        serviceLabel.setBackground(new Color(216, 235, 243));
        serviceLabel.setFont(new Font("Play", 1, 12));
        serviceLabel.setText("SERVICE");

        therapistLabel.setBackground(new Color(216, 235, 243));
        therapistLabel.setFont(new Font("Play", 1, 12));
        therapistLabel.setText("THERAPIST NAME");

        appointmentDateLabel.setBackground(new Color(216, 235, 243));
        appointmentDateLabel.setFont(new Font("Play", 1, 12));
        appointmentDateLabel.setText("APPOINTMENT DATE (DD-MM-YYYY)");

        appointmentTimeLabel.setBackground(new Color(216, 235, 243));
        appointmentTimeLabel.setFont(new Font("Play", 1, 12));
        appointmentTimeLabel.setText("APPOINTMENT TIME (HH:MM) (24 Hours Format)");
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

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        // Viewing and Updating the Appointments
        if (appointment != null) {
            appointmentTimeTxt.setValue(appointment.getAppointmentTime());
            appointmentDateTxt.setDate(appointment.getAppointmentDate());
            clientNameTxt.setText(appointment.getClientName());
            phoneNumberTxt.setText(appointment.getClientPhoneNumber());
            serviceListSelector.setSelectedIndex(selectedServiceIndex);
            therapistListSelector.setSelectedIndex(selectedTherapistIndex);
            // Updating the Appointments
            if (isEditable) {
                submitLabel.setText("UPDATE");
                addAppointmentLabel.setText("UPDATE APPOINTMENT DETAILS");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(29, 29, 29)
                                                        .addComponent(backLabel)
                                                        .addGap(370, 370, 370)
                                                        .addComponent(addAppointmentLabel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 28, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(414, 414, 414)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(clientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(therapistLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(appointmentDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(appointmentTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGap(105, 105, 105)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addComponent(appointmentDateTxt)
                                                                .addComponent(appointmentTimeTxt)
                                                                .addComponent(serviceListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(therapistListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(clientNameTxt)
                                                                .addComponent(phoneNumberTxt))))
                                        .addGap(300, 300, 300))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(600, 600, 600)
                                        .addComponent(submitLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                )
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(backLabel))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addAppointmentLabel))
                                        .addGap(44,44,44)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(clientNameLabel)
                                                .addComponent(clientNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceLabel)
                                                .addComponent(serviceListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistLabel)
                                                .addComponent(therapistListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(appointmentDateLabel)
                                                .addComponent(appointmentDateTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(appointmentTimeLabel)
                                                .addComponent(appointmentTimeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(40, 40, 40)
                                        .addComponent(submitLabel)
                                )
                );
            }
            // Viewing the Appointments
            else {
                appointmentDateTxt.setEnabled(false);
                appointmentTimeTxt.setEnabled(false);
                clientNameTxt.setEnabled(false);
                phoneNumberTxt.setEnabled(false);
                serviceListSelector.setEditable(false);
                serviceListSelector.setEnabled(false);
                therapistListSelector.setEnabled(false);
                therapistListSelector.setEditable(false);
                addAppointmentLabel.setText("VIEW APPOINTMENT DETAILS");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(29, 29, 29)
                                                        .addComponent(backLabel)
                                                        .addGap(370, 370, 370)
                                                        .addComponent(addAppointmentLabel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 28, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(414, 414, 414)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(clientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(therapistLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(appointmentDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(appointmentTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGap(105, 105, 105)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addComponent(appointmentDateTxt)
                                                                .addComponent(appointmentTimeTxt)
                                                                .addComponent(serviceListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(therapistListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(clientNameTxt)
                                                                .addComponent(phoneNumberTxt))))
                                        .addGap(435, 435, 435))
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(backLabel))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addAppointmentLabel))
                                        .addGap(44,44,44)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(clientNameLabel)
                                                .addComponent(clientNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceLabel)
                                                .addComponent(serviceListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistLabel)
                                                .addComponent(therapistListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(appointmentDateLabel)
                                                .addComponent(appointmentDateTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(30,30,30)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(appointmentTimeLabel)
                                                .addComponent(appointmentTimeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(163, Short.MAX_VALUE))
                );

            }
        }
        // Creating Appointments
        else {
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(29, 29, 29)
                                                    .addComponent(backLabel)
                                                    .addGap(451, 451, 451)
                                                    .addComponent(addAppointmentLabel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 28, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(414, 414, 414)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(serviceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(clientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(therapistLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(appointmentDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(appointmentTimeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(105, 105, 105)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                            .addComponent(appointmentDateTxt)
                                                            .addComponent(appointmentTimeTxt)
                                                            .addComponent(serviceListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(therapistListSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(clientNameTxt)
                                                            .addComponent(phoneNumberTxt))))
                                    .addGap(300, 300, 300))
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(600, 600, 600)
                                    .addComponent(submitLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(15, 15, 15)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(backLabel))
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(addAppointmentLabel))
                                    .addGap(44,44,44)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(clientNameLabel)
                                            .addComponent(clientNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(30,30,30)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(phoneNumberLabel)
                                            .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(30,30,30)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(serviceLabel)
                                            .addComponent(serviceListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(30,30,30)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(therapistLabel)
                                            .addComponent(therapistListSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(30,30,30)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(appointmentDateLabel)
                                            .addComponent(appointmentDateTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(30,30,30)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(appointmentTimeLabel)
                                            .addComponent(appointmentTimeTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(40,40,40)
                                    .addComponent(submitLabel))
            );
        }


    }

    //This method navigates to Appointment Table page when back button is clicked
    private void backLabelActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new AppointmentsPanel());
        container.validate();
        container.repaint();
    }

    // This method get execute when submit button is clicked while updating and creating appointment details
    private void submitLabelActionPerformed(ActionEvent evt) {
        Database db = new Database();
        if (appointment == null) {
            if (clientNameTxt.getText() != null && !clientNameTxt.getText().trim().equals("") && phoneNumberTxt.getText() != null && appointmentDateTxt.getDate() != null && !phoneNumberTxt.getText().trim().equals("") && serviceListSelector.getSelectedItem() != null && therapistListSelector.getSelectedItem() != null) {
                try {
                    String modifiedDate = dateFormat.format(appointmentDateTxt.getDate().getTime());
                    db.executeUpdate("INSERT INTO Appointment ( ClientName, ClientPhoneNumber, AppointmentDate, AppointmentTime,TherapistID, ServiceID, IsActive) VALUES(?,?,?,?,?,?,?)", clientNameTxt.getText(), phoneNumberTxt.getText(), modifiedDate, appointmentTimeTxt.getValue(), ((Therapist) therapistListSelector.getSelectedItem()).getId(), ((Service) serviceListSelector.getSelectedItem()).getId(), true);
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
                currentAppointment.setActive(rs.getBoolean("IsActive"));
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
