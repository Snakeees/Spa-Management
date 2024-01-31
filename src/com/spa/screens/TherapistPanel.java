package com.spa.screens;

import com.spa.dto.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class TherapistPanel extends JPanel {

    private Therapist therapist;
    private final SimpleDateFormat requiredDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // UI Components
    private MyTextField addressTxt, phoneNumberTxt, therapistNameTxt;
    private JLabel TitleLabel, addressLabel, phoneNumberLabel, therapistNameLabel, currentActiveLabel, resignationDateLabel, attendanceLabel;
    private JCheckBox isActive;
    private JDateChooser resignationDateTxt;
    private JPanel attendanceShowPanel;

    public TherapistPanel(Integer therapistId, boolean isEditable) {
        this.therapist = getTherapist(therapistId);
        initComponents(isEditable);
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
        setBackground(BACKGROUND_COLOR);
    }

    private void initComponents(boolean isEditable) {
        setupLabelsAndFields(isEditable);
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(therapistNameLabel, therapistNameTxt)));
        items.add(new ArrayList<>(Arrays.asList(phoneNumberLabel, phoneNumberTxt)));
        items.add(new ArrayList<>(Arrays.asList(addressLabel, addressTxt)));

        if (therapist != null) {
            items.add(new ArrayList<>(Arrays.asList(currentActiveLabel, isActive)));
            items.add(new ArrayList<>(Arrays.asList(resignationDateLabel, resignationDateTxt)));
            items.add(new ArrayList<>(Arrays.asList(attendanceLabel, attendanceShowPanel)));
        }

        String submitButtonText = (therapist != null) ? "UPDATE" : "CREATE";

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> handleBackAction();
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();


        InfoPanel infoPanel = new InfoPanel(TitleLabel, true, isEditable, submitButtonText, Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }

    private void setupLabelsAndFields(Boolean isEditable) {
        TitleLabel = InfoPanel.createLabel("CREATE THERAPIST", 20);
        therapistNameLabel = InfoPanel.createLabel("THERAPIST NAME", 15);
        phoneNumberLabel = InfoPanel.createLabel("PHONE NUMBER", 15);
        addressLabel = InfoPanel.createLabel("ADDRESS", 15);
        currentActiveLabel = InfoPanel.createLabel("CURRENTLY ACTIVE", 15);
        resignationDateLabel = InfoPanel.createLabel("RESIGNATION DATE  (DD-MM-YYYY)", 15);
        attendanceLabel = InfoPanel.createLabel("ATTENDANCE (For past 7 Days)", 15);
        isActive = new JCheckBox();
        isActive.setBackground(BACKGROUND_COLOR);

        therapistNameTxt = new MyTextField(20);
        phoneNumberTxt = new MyTextField(20);
        addressTxt = new MyTextField(20);

        resignationDateTxt = new JDateChooser();
        resignationDateTxt.setDateFormatString("dd-MM-yyyy");

        attendanceShowPanel = therapist != null ? new AttendancePanel(therapist.getId()) : null;

        setDetails(isEditable);
    }

    private void setDetails(boolean isEditable) {
        if (therapist != null) {
            therapistNameTxt.setText(therapist.getFirstName());
            phoneNumberTxt.setText(therapist.getPhoneNumber());
            addressTxt.setText(therapist.getAddress());
            isActive.setSelected(therapist.isActive());
            resignationDateTxt.setDate(therapist.getResignationDate());
            TitleLabel.setText(isEditable ? "UPDATE THERAPIST DETAILS" : "VIEW THERAPIST DETAILS");
        }

        therapistNameTxt.setEnabled(isEditable);
        phoneNumberTxt.setEnabled(isEditable);
        addressTxt.setEnabled(isEditable);
        isActive.setEnabled(isEditable);
        resignationDateTxt.setEnabled(isEditable);
    }

    //This method navigates to Therapist Table page when back button is clicked
    private void handleBackAction() {
        JViewport container = (JViewport) getParent();
        container.setView(new TherapistsPanel());
        container.validate();
        container.repaint();
    }

    // This method get execute when submit button is clicked while updating and creating Therapist details
    private void handleSubmitAction() {
        Database db = new Database();
        if (therapist == null) {
            if (therapistNameTxt.getText() != null && !therapistNameTxt.getText().trim().isEmpty() && phoneNumberTxt.getText() != null && addressTxt.getText() != null && !phoneNumberTxt.getText().trim().isEmpty() && !addressTxt.getText().trim().isEmpty()) {
                try {
                    db.executeUpdate("INSERT INTO Therapist ( FirstName, PhoneNumber, Address, IsActive) VALUES(?,?,?,?)", therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), true);
                    JViewport container = (JViewport) getParent();
                    container.setView(new TherapistsPanel());
                    container.validate();
                    container.repaint();
                    JOptionPane.showMessageDialog(this, "Therapist created successfully!");
                }catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to create therapist");
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "All fields are required");
            }
        }
        else {
            if (therapistNameTxt.getText() != null && !therapistNameTxt.getText().trim().isEmpty() && phoneNumberTxt.getText() != null && addressTxt.getText() != null && !phoneNumberTxt.getText().trim().isEmpty() && !addressTxt.getText().trim().isEmpty()) {
                int result = JOptionPane.showOptionDialog(
                        getParent(),
                        "Do you want to update the Therapist Details?",
                        "UPDATE ALERT",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"YES", "NO"},
                        "YES");

                // Check which button was clicked
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        if (resignationDateTxt.getDate() == null) {
                            db.executeUpdate("update Therapist set FirstName=?, PhoneNumber=?, Address=?, IsActive=?, ResignationDate=? where ID=? ;", therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), isActive.isSelected(), null, therapist.getId());
                        }
                        else {
                            String modifiedDate = requiredDateFormat.format(resignationDateTxt.getDate().getTime());
                            db.executeUpdate("update Therapist set FirstName=?, PhoneNumber=?, Address=?, IsActive=?, ResignationDate=? where ID=? ;", therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), isActive.isSelected(), modifiedDate, therapist.getId());
                        }
                        JViewport container = (JViewport) getParent();
                        container.setView(new TherapistsPanel());
                        container.validate();
                        container.repaint();
                        JOptionPane.showMessageDialog(this, "Therapist details updated successfully!");
                    }catch (Exception e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to update therapist details");
                    }
                }
                else if (result == JOptionPane.CANCEL_OPTION) {
                    JViewport container = (JViewport) getParent();
                    container.setView(new TherapistsPanel());
                    container.validate();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Required details can not be empty.");
            }
        }
    }

    // Fetching Therapist details from DB
    private Therapist getTherapist(Integer therapistId) {
        Therapist selectedTherapist = null;
        try {
            Database db = new Database();
            ResultSet rs = db.executeQuery("Select * from Therapist where ID=?", therapistId);
            while (rs.next()) {
                selectedTherapist = new Therapist();
                selectedTherapist.setActive(rs.getBoolean("IsActive"));
                selectedTherapist.setId(rs.getInt("ID"));
                selectedTherapist.setFirstName(rs.getString("FirstName"));
                selectedTherapist.setAddress(rs.getString("Address"));
                selectedTherapist.setPhoneNumber(rs.getString("PhoneNumber"));
                selectedTherapist.setResignationDate(rs.getDate("ResignationDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedTherapist;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }


    public class AttendancePanel extends JPanel {

        private ArrayList<TherapistAttendance> Attendances;
        private ArrayList<JTextField> attendanceList;
        private SimpleDateFormat requiredDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        public AttendancePanel(Integer therapistId) {
            this.Attendances = getAttendance(therapistId);
            setupAttendancePanel();
            updateAttendanceDisplay(therapistId);
        }

        private void setupAttendancePanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(null));
            setSize((24*7+6*5), 24);

            attendanceList = new ArrayList<>();
            Dimension boxSize = new Dimension(24, 24);
            for (int i = 0; i < 7; i++) {
                JTextField box = new JTextField();
                box.setPreferredSize(boxSize);
                box.setEnabled(false);
                attendanceList.add(box);
                add(box);
                if (i < 6) {
                    add(Box.createHorizontalStrut(5));
                }
            }
        }

        private ArrayList<TherapistAttendance> getAttendance(Integer therapistId) {
            ArrayList<TherapistAttendance> attendancesList = new ArrayList<>();
            TherapistAttendance therapistAttendance;
            try {
                Database db = new Database();
                ResultSet rs = db.executeQuery("Select * from TherapistAttendance where TherapistID=? and CheckinTime IS NOT NULL order by Date asc limit 7", therapistId);
                while (rs.next()) {
                    therapistAttendance = new TherapistAttendance();
                    therapistAttendance.setTherapistId(rs.getInt("ID"));
                    therapistAttendance.setCheckinTime(rs.getTime("CheckinTime"));
                    therapistAttendance.setCheckoutTime(rs.getTime("CheckoutTime"));
                    therapistAttendance.setDate(rs.getDate("Date"));
                    attendancesList.add(therapistAttendance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return attendancesList;
        }

        public void updateAttendanceDisplay(Integer therapistId) {
            String currentDate;
            int x = Attendances.size() - 1;
            for (int i = 6, j = x, k = x; i >= 0; i--, k--) {
                Date d = new Date();
                d.setDate(d.getDate() - (x - k));
                attendanceList.get(i).setToolTipText(dateFormatter.format(d));
                currentDate = requiredDateFormat.format(d);
                Database db = new Database();
                try {
                    ResultSet rs = db.executeQuery("SELECT CheckinTime FROM TherapistAttendance WHERE TherapistID = ? AND Date = ? AND CheckinTime IS NOT NULL limit 1", therapistId, currentDate);
                    if (rs.next()) {
                        attendanceList.get(i).setBackground(new Color(27, 112, 38));
                    } else {
                        attendanceList.get(i).setBackground(new Color(166, 7, 7));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
