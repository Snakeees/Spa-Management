package com.spa.screens;

import com.spa.dto.Service;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServicePanel extends JPanel {

    Service service;
    private JLabel activeLable;
    private JLabel addServiceLabel;
    private JLabel costLabel;
    private JTextField costPerClientTxt;
    private JCheckBox isActive;
    private JLabel lastServiceDateLabel;
    private JFormattedTextField lastServiceDateTxt;
    private JLabel serviceDurationLabel;
    private JSpinner serviceDurationTxt;
    private JLabel serviceName;
    private JTextField serviceNameTxt;
    private JButton submitLabel;
    private JButton backLabel;
    private  SimpleDateFormat timeFormater=new SimpleDateFormat("HH:mm");
    public ServicePanel(Integer serviceId, boolean isEditable) {
        this.service = getService(serviceId);
        initComponents(service, isEditable);
    }



        private void initComponents(Service service, boolean isEditable) {

        addServiceLabel = new  JLabel();
        serviceName = new  JLabel();
        serviceDurationLabel = new  JLabel();
        costLabel = new  JLabel();
        activeLable = new  JLabel();
        lastServiceDateLabel = new  JLabel();
        serviceNameTxt = new  JTextField();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        Date initialTime = calendar.getTime();

        SpinnerDateModel sm = new SpinnerDateModel(initialTime, null, null, Calendar.HOUR_OF_DAY);
        serviceDurationTxt = new  JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(serviceDurationTxt, "HH:mm");
        serviceDurationTxt.setEditor(de);
        costPerClientTxt = new JTextField();
        lastServiceDateTxt = new JFormattedTextField();
        isActive = new JCheckBox();
        submitLabel = new JButton();
        backLabel = new JButton();
        SimpleDateFormat requiredDateFormate = new SimpleDateFormat("dd-MM-yyyy");
        lastServiceDateTxt.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(requiredDateFormate)));
        setBackground(new Color(216, 235, 243));
        addServiceLabel.setText("CREATE SERVICE");
        serviceName.setBackground(new Color(216, 235, 243));
        serviceName.setText("SERVICE NAME");
        serviceDurationLabel.setBackground(new Color(216, 235, 243));
        serviceDurationLabel.setText("SERVICE DURATION (HH:MM)");
        costLabel.setBackground(new Color(216, 235, 243));
        costLabel.setText("COST PER CLIENT");
        activeLable.setBackground(new Color(216, 235, 243));
        activeLable.setText("CURRENTLY ACTIVE");
        lastServiceDateLabel.setBackground(new Color(216, 235, 243));
        lastServiceDateLabel.setText("LAST SERVICE DATE");
        isActive.setBackground(new Color(216, 235, 243));
        submitLabel.setBackground(new Color(53, 183, 234));
        submitLabel.setText("CREATE");
        backLabel.setBackground(new Color(53, 183, 234));
        backLabel.setText("BACK");
        addServiceLabel.setFont(new Font("Play", Font.BOLD, 20));
        serviceDurationLabel.setFont(new Font("Play", Font.BOLD, 15));
        serviceName.setFont(new Font("Play", Font.BOLD, 15));
        costLabel.setFont(new Font("Play", Font.BOLD, 15));
        activeLable.setFont(new Font("Play", Font.BOLD, 15));
        lastServiceDateLabel.setFont(new Font("Play", Font.BOLD, 15));
        serviceDurationTxt.setFont(new Font("Play", 0, 12));
        submitLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        backLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backLabelActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        lastServiceDateTxt.setEnabled(false);

        // Viewing and Updating the Services
        if (service != null) {
            serviceNameTxt.setText(service.getServiceName());
            serviceDurationTxt.setValue(service.getDuration());
            costPerClientTxt.setText(Integer.toString(service.getCost()));
            isActive.setSelected(service.isActive());
            if (service.getServiceLastDate() == null)
                lastServiceDateTxt.setText("");
            else {
                Date lastServiceDate = service.getServiceLastDate();
                lastServiceDateTxt.setText(requiredDateFormate.format(lastServiceDate));
            }
            // Updating the Services
            if (isEditable) {
                addServiceLabel.setText("UPDATE SERVICE");
                submitLabel.setText("UPDATE");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addContainerGap(4, Short.MAX_VALUE)                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addComponent(serviceDurationLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(costLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(activeLable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lastServiceDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(isActive,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceDurationTxt,GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                                                .addComponent(costPerClientTxt)
                                                                .addComponent(lastServiceDateTxt)
                                                                .addComponent(serviceNameTxt))
                                                        .addContainerGap(4, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(59, 59, 59)
                                                        .addComponent(backLabel)
                                                        .addGap(440, 440, 440)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(20, 20, 20)
                                                                                .addComponent(addServiceLabel,GroupLayout.PREFERRED_SIZE, 223,GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(35, 35, 35)
                                                                                .addComponent(submitLabel,GroupLayout.PREFERRED_SIZE, 102,GroupLayout.PREFERRED_SIZE)
                                                                        )
                                                                ))))));
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(15, 15, 15)
                                                        .addComponent(backLabel))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(35, 35, 35)
                                                        .addComponent(addServiceLabel,GroupLayout.PREFERRED_SIZE, 35,GroupLayout.PREFERRED_SIZE)))
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceName)
                                                .addComponent(serviceNameTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceDurationLabel)
                                                .addComponent(serviceDurationTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(costLabel)
                                                .addComponent(costPerClientTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(activeLable)
                                                .addComponent(isActive))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lastServiceDateLabel)
                                                .addComponent(lastServiceDateTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addComponent(submitLabel,GroupLayout.PREFERRED_SIZE, 33,GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))

                );
            }
            // Viewing the Service Details
            else {
                serviceNameTxt.setEnabled(false);
                serviceDurationTxt.setEnabled(false);
                isActive.setEnabled(false);
                lastServiceDateTxt.setEnabled(false);
                costPerClientTxt.setEnabled(false);
                addServiceLabel.setText("VIEW SERVICE");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addContainerGap(4, Short.MAX_VALUE)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(serviceDurationLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(costLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(activeLable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lastServiceDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(isActive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(serviceDurationTxt, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                                                .addComponent(costPerClientTxt)
                                                                .addComponent(lastServiceDateTxt)
                                                                .addComponent(serviceNameTxt))
                                                        .addContainerGap(4, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(59, 59, 59)
                                                        .addComponent(backLabel)
                                                        .addGap(440, 440, 440)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(20, 20, 20)
                                                                        .addComponent(addServiceLabel, GroupLayout.PREFERRED_SIZE, 223,GroupLayout.PREFERRED_SIZE))
                                                        ))))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(15, 15, 15)
                                                        .addComponent(backLabel))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(35, 35, 35)
                                                        .addComponent(addServiceLabel, GroupLayout.PREFERRED_SIZE, 35,GroupLayout.PREFERRED_SIZE)))
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceName)
                                                .addComponent(serviceNameTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(serviceDurationLabel)
                                                .addComponent(serviceDurationTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(costLabel)
                                                .addComponent(costPerClientTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(activeLable)
                                                .addComponent(isActive))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lastServiceDateLabel)
                                                .addComponent(lastServiceDateTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)))
                );

            }
        }
        // Creating Service Details
        else if (isEditable) {
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addContainerGap(4, Short.MAX_VALUE)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(serviceDurationLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(serviceName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(costLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(serviceDurationTxt,GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                            .addComponent(costPerClientTxt)
                                                            .addComponent(serviceNameTxt))
                                                    .addContainerGap(4, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(59, 59, 59)
                                                    .addComponent(backLabel)
                                                    .addGap(440, 440, 440)
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGap(20, 20, 20)
                                                                    .addComponent(addServiceLabel,GroupLayout.PREFERRED_SIZE, 223,GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGap(35, 35, 35)
                                                                    .addComponent(submitLabel,GroupLayout.PREFERRED_SIZE, 102,GroupLayout.PREFERRED_SIZE)
                                                            )
                                                    ))))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(15, 15, 15)
                                                    .addComponent(backLabel))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(35, 35, 35)
                                                    .addComponent(addServiceLabel,GroupLayout.PREFERRED_SIZE, 35,GroupLayout.PREFERRED_SIZE)))
                                    .addGap(45, 45, 45)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(serviceName)
                                            .addComponent(serviceNameTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                    .addGap(20, 20, 20)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(serviceDurationLabel)
                                            .addComponent(serviceDurationTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                    .addGap(20, 20, 20)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(costLabel)
                                            .addComponent(costPerClientTxt,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
                                    .addGap(30, 30, 30)
                                    .addComponent(submitLabel,GroupLayout.PREFERRED_SIZE, 33,GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
            );
        }


    }
    //This method navigates to Service Table page when back button is clicked
    private void backLabelActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new ServicesPanel());
        container.validate();
        container.repaint();
    }
    // This method get execute when submit button is clicked while updating and creating Service details
    private void submitActionPerformed(ActionEvent evt) {
        Database db = new Database();
        if (service == null) {
            if (serviceNameTxt.getText() != null && !serviceNameTxt.getText().trim().equals("") && serviceDurationTxt.getValue() != null && costPerClientTxt.getText() != null && !costPerClientTxt.getText().trim().equals("") && !serviceDurationTxt.getValue().toString().equals("00:00")) {
                try {
                    db.executeUpdate("INSERT INTO Service ( ServiceName, Duration, Cost,IsActive) VALUES(?,?,?,?)", serviceNameTxt.getText(), serviceDurationTxt.getValue(), costPerClientTxt.getText(), true);
                    JViewport container = (JViewport) getParent();
                    container.setView(new ServicesPanel());
                    container.validate();
                    container.repaint();
                    JOptionPane.showMessageDialog(this, "Service created successfully!");
                }catch (Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed create service");
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "All fields are required");
            }
        }
        else {
            if (serviceNameTxt.getText() != null && !serviceNameTxt.getText().trim().equals("") && serviceDurationTxt.getValue() != null && costPerClientTxt.getText() != null && !costPerClientTxt.getText().trim().equals("") && !serviceDurationTxt.getValue().toString().equals("00:00")) {
                int result = JOptionPane.showOptionDialog(
                        getParent(),
                        "Do you want to update the Service Details?",
                        "UPDATE ALERT",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"YES", "NO"},
                        "YES");

                // Check which button was clicked
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        db.executeUpdate("update Service set ServiceName=?, Duration=?, Cost=?, IsActive=? where ID=? ;", serviceNameTxt.getText(), serviceDurationTxt.getValue(), costPerClientTxt.getText(), isActive.isSelected(), service.getId());
                        JViewport container = (JViewport) getParent();
                        container.setView(new ServicesPanel());
                        container.validate();
                        container.repaint();
                        JOptionPane.showMessageDialog(this, "Service details updated successfully!");
                    }catch (Exception exception){
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to update service details");
                    }
                }
                else if (result == JOptionPane.CANCEL_OPTION) {
                    JViewport container = (JViewport) getParent();
                    container.setView(new ServicesPanel());
                    container.validate();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Required details can not be empty.");
            }
        }
    }
    // This method fetches the Service details when Service form page is opened in the update or edit mode
    private Service getService(Integer serviceId) {
        Service currentService = null;
        try {
            Database db = new Database();
            ResultSet rs = db.executeQuery("Select * from Service where ID=?", serviceId);
            ResultSet rs1 = db.executeQuery("Select AppointmentDate from Appointment where ServiceID=? and IsActive=true order by AppointmentDate desc limit 1;", serviceId);
            while (rs.next()) {
                currentService = new Service();
                currentService.setActive(rs.getBoolean("IsActive"));
                currentService.setId(rs.getInt("ID"));
                currentService.setServiceName(rs.getString("ServiceName"));
                currentService.setCost(rs.getInt("Cost"));
                currentService.setDuration(rs.getTime("Duration"));
            }
            if (rs1.next() && currentService != null) {
                currentService.setServiceLastDate(rs1.getDate("AppointmentDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentService;
    }
}
