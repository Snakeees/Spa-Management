package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class ServicePanel extends JPanel {

    Service service;
    private JLabel activeLabel, TitleLabel, costLabel, lastServiceDateLabel, serviceDurationLabel, serviceName;
    private MyTextField costPerClientTxt, serviceNameTxt;
    private JCheckBox isActive;
    private JFormattedTextField lastServiceDateTxt;
    private JSpinner serviceDurationTxt;
    private SimpleDateFormat requiredDateFormate = new SimpleDateFormat("dd-MM-yyyy");


    public ServicePanel(Integer serviceId, boolean isEditable) {
        this.service = getService(serviceId);
        initComponents(isEditable);
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
        setBackground(BACKGROUND_COLOR);
    }

    private void initComponents(boolean isEditable) {
        setupLabelsAndFields(isEditable);
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(serviceName, serviceNameTxt)));
        items.add(new ArrayList<>(Arrays.asList(serviceDurationLabel, serviceDurationTxt)));
        items.add(new ArrayList<>(Arrays.asList(costLabel, costPerClientTxt)));

        if (service != null) {
            items.add(new ArrayList<>(Arrays.asList(activeLabel, isActive)));
            items.add(new ArrayList<>(Arrays.asList(lastServiceDateLabel, lastServiceDateTxt)));
        }

        String submitButtonText = (service != null) ? "UPDATE" : "CREATE";

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> handleBackAction();
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();

        InfoPanel infoPanel = new InfoPanel(TitleLabel, true, isEditable, submitButtonText, Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }


    private void setupLabelsAndFields(Boolean isEditable) {
        TitleLabel = InfoPanel.createLabel("CREATE SERVICE", 20);
        serviceName = InfoPanel.createLabel("SERVICE NAME", 15);
        serviceDurationLabel = InfoPanel.createLabel("SERVICE DURATION (HH:MM)", 15);
        costLabel = InfoPanel.createLabel("ADDRESS", 15);
        activeLabel = InfoPanel.createLabel("CURRENTLY ACTIVE", 15);
        lastServiceDateLabel = InfoPanel.createLabel("RESIGNATION DATE  (DD-MM-YYYY)", 15);
        isActive = new JCheckBox();
        isActive.setBackground(BACKGROUND_COLOR);

        costPerClientTxt = new MyTextField(20);
        serviceNameTxt = new MyTextField(20);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        Date initialTime = calendar.getTime();

        SpinnerDateModel sm = new SpinnerDateModel(initialTime, null, null, Calendar.HOUR_OF_DAY);
        serviceDurationTxt = new  JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(serviceDurationTxt, "HH:mm");
        serviceDurationTxt.setEditor(de);

        lastServiceDateTxt = new JFormattedTextField(new DefaultFormatterFactory(new DateFormatter(requiredDateFormate)));
        lastServiceDateTxt.setEnabled(false);

        setDetails(isEditable);
    }

    private void setDetails(boolean isEditable) {
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
            TitleLabel.setText(isEditable ? "UPDATE SERVICE" : "VIEW SERVICE");
        }

        serviceNameTxt.setEnabled(isEditable);
        serviceDurationTxt.setEnabled(isEditable);
        costPerClientTxt.setEnabled(isEditable);
        isActive.setEnabled(isEditable);
    }

    //This method navigates to Service Table page when back button is clicked
    private void handleBackAction() {
        JViewport container = (JViewport) getParent();
        container.setView(new ServicesPanel());
        container.validate();
        container.repaint();
    }
    // This method get execute when submit button is clicked while updating and creating Service details
    private void handleSubmitAction() {
        Database db = new Database();
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}
