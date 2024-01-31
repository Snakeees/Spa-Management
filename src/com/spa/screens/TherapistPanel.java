package com.spa.screens;

import com.spa.dto.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class TherapistPanel extends JPanel {

    private Therapist therapist;
    private ArrayList<TherapistAttendance> therapistAttendances;
    private SimpleDateFormat requiredDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    // UI Components
    private ArrayList<JTextField> attendanceList;
    private JTextField addressTxt, phoneNumberTxt, therapistNameTxt;
    private JLabel addTherapistLabel, addressLabel, phoneNumberLabel, therapistNameLabel, currentActiveLabel, resignationDateLabel, attendanceLabel;
    private JCheckBox isActive;
    private JDateChooser resignationDateTxt;
    private JPanel attendanceShowPanel;
    private MyButton submitButton, backButton;

    public TherapistPanel(Integer therapistId, boolean isEditable) {
        this.therapist = getTherapist(therapistId);
        this.therapistAttendances = getTherapistAttendance(therapistId);
        initComponents(isEditable);
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
        setBackground(BACKGROUND_COLOR);
    }

    private void initComponents(boolean isEditable) {
        setupLabelsAndFields();
        setupAttendancePanel();
        setupButtons(isEditable);
        layoutComponents(isEditable);
        setTherapistDetails(isEditable);
    }

    private void setupLabelsAndFields() {
        addTherapistLabel = createLabel("CREATE THERAPIST", new Font("Play", Font.BOLD, 20));
        therapistNameLabel = createLabel("THERAPIST NAME", new Font("Play", Font.BOLD, 15));
        phoneNumberLabel = createLabel("PHONE NUMBER", new Font("Play", Font.BOLD, 15));
        addressLabel = createLabel("ADDRESS", new Font("Play", Font.BOLD, 15));
        currentActiveLabel = createLabel("CURRENTLY ACTIVE", new Font("Play", Font.BOLD, 15));
        resignationDateLabel = createLabel("RESIGNATION DATE  (DD-MM-YYYY)", new Font("Play", Font.BOLD, 15));
        attendanceLabel = createLabel("ATTENDANCE (For past 7 Days)", new Font("Play", Font.BOLD, 15));
        isActive = new JCheckBox();

        therapistNameTxt = new JTextField(20);
        phoneNumberTxt = new JTextField(20);
        addressTxt = new JTextField(20);

        resignationDateTxt = new JDateChooser();
        resignationDateTxt.setDateFormatString("dd-MM-yyyy");
    }

    private void setupAttendancePanel() {
        attendanceShowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        attendanceShowPanel.setBackground(Color.WHITE);
        attendanceShowPanel.setBorder(BorderFactory.createLineBorder(null));
        attendanceShowPanel.setSize((24*7+6*5), 24);

        attendanceList = new ArrayList<>();
        Dimension boxSize = new Dimension(24, 24);
        for (int i = 0; i < 7; i++) {
            JTextField box = new JTextField();
            box.setPreferredSize(boxSize);
            box.setEnabled(false);
            attendanceList.add(box);
            attendanceShowPanel.add(box);
            if (i < 6) {
                attendanceShowPanel.add(Box.createHorizontalStrut(5));
            }
        }
    }

    private void setupButtons(boolean isEditable) {
        submitButton = createButton(isEditable ? "UPDATE" : "CREATE", e -> handleSubmitAction());
        backButton = createButton("BACK", e -> handleBackAction());
    }

    private void layoutComponents(boolean isEditable) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 100, (therapist != null) ? 70 : 50, 100); // Set padding around components
        gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor components to the northwest (top-left)

        // BACK button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        gbc.fill = GridBagConstraints.NONE; // Do not stretch the component
        add(backButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER; // Center components
        add(addTherapistLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch components horizontally
        gbc.gridwidth = 1; // Set grid width back to 1 for labels
        gbc.weightx = 0; // Set weightx back to 0 for labels
        gbc.insets = new Insets(10, 100, 10, 100); // Set padding around components

        // Add labels and fields
        addLabelAndComponent(therapistNameLabel, therapistNameTxt, gbc);
        addLabelAndComponent(phoneNumberLabel, phoneNumberTxt, gbc);
        addLabelAndComponent(addressLabel, addressTxt, gbc);

        if (therapist != null) {
            updateAttendanceDisplay();
            addLabelAndComponent(currentActiveLabel, isActive, gbc);
            addLabelAndComponent(resignationDateLabel, resignationDateTxt, gbc);
            addLabelAndComponent(attendanceLabel, attendanceShowPanel, gbc);
            therapistNameTxt.setText(therapist.getFirstName());
            phoneNumberTxt.setText(therapist.getPhoneNumber());
            addressTxt.setText(therapist.getAddress());
            isActive.setSelected(therapist.isActive());
            resignationDateTxt.setDate(therapist.getResignationDate());
            // Depending on whether details are editable or not
            addTherapistLabel.setText(isEditable ? "UPDATE THERAPIST DETAILS" : "VIEW THERAPIST DETAILS");
            submitButton.setText("UPDATE");
        }

        if (isEditable) {
            // Submit or Create button
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2; // Span two columns for the button
            gbc.fill = GridBagConstraints.NONE; // Do not stretch the button
            gbc.anchor = GridBagConstraints.CENTER; // Center the button
            gbc.insets = new Insets(30, 100, 0, 100); // Set padding around components
            add(submitButton, gbc);
        } else {
            therapistNameTxt.setEnabled(false);
            phoneNumberTxt.setEnabled(false);
            addressTxt.setEnabled(false);
            isActive.setEnabled(false);
            resignationDateTxt.setEnabled(false);
        }
    }

    private void setTherapistDetails(boolean isEditable) {
        if (therapist != null) {
            updateAttendanceDisplay();
            therapistNameTxt.setText(therapist.getFirstName());
            phoneNumberTxt.setText(therapist.getPhoneNumber());
            addressTxt.setText(therapist.getAddress());
            isActive.setSelected(therapist.isActive());
            resignationDateTxt.setDate(therapist.getResignationDate());
            addTherapistLabel.setText(isEditable ? "UPDATE THERAPIST DETAILS" : "VIEW THERAPIST DETAILS");
        }

        therapistNameTxt.setEnabled(isEditable);
        phoneNumberTxt.setEnabled(isEditable);
        addressTxt.setEnabled(isEditable);
        isActive.setEnabled(isEditable);
        resignationDateTxt.setEnabled(isEditable);
    }


    // Fetching the therapist attendance and updating it in the UI
    private void updateAttendanceDisplay() {
        String currentDate;
        int x = therapistAttendances.size() - 1;
        for (int i = 6, j = x, k = x; i >= 0; i--, k--) {
            Date d = new Date();
            d.setDate(d.getDate() - (x - k));
            attendanceList.get(i).setToolTipText(dateFormatter.format(d));
            currentDate = requiredDateFormat.format(d);
            Database db = new Database();
            try {
                ResultSet rs = db.executeQuery("SELECT CheckinTime FROM TherapistAttendance WHERE TherapistID = ? AND Date = ? AND CheckinTime IS NOT NULL limit 1", therapist.getId(), currentDate);
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

    //This method navigates to Therapist Table page when back button is clicked
    private void handleBackAction() {
        JViewport container = (JViewport) getParent();
        container.setView(new TherapistsPanel());
        container.validate();
        container.repaint();
    }

    // This method get execute when submit button is clicked while updating and creating Therapist details
    private void handleSubmitAction() {
        if (isInputValid()) {
            performDatabaseOperation();
        } else {
            JOptionPane.showMessageDialog(this, "All fields are required");
        }
    }

    private boolean isInputValid() {
        return !therapistNameTxt.getText().trim().isEmpty() &&
                !phoneNumberTxt.getText().trim().isEmpty() &&
                !addressTxt.getText().trim().isEmpty();
    }

    private void performDatabaseOperation() {
        try {
            if (therapist == null) {
                createNewTherapist();
            } else {
                updateExistingTherapist();
            }
            navigateToTherapistPanel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Operation failed: " + e.getMessage());
        }
    }

    private void createNewTherapist() throws Exception {
        Database db = new Database();
        db.executeUpdate("INSERT INTO Therapist (FirstName, PhoneNumber, Address, IsActive) VALUES(?,?,?,?)",
                therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), true);
        JOptionPane.showMessageDialog(this, "Therapist created successfully!");
    }

    private void updateExistingTherapist() throws Exception {
        Database db = new Database();
        if (askForConfirmation("Do you want to update the Therapist Details?")) {
            String modifiedDate = resignationDateTxt.getDate() == null ? null :
                    requiredDateFormat.format(resignationDateTxt.getDate());
            db.executeUpdate("UPDATE Therapist SET FirstName=?, PhoneNumber=?, Address=?, IsActive=?, ResignationDate=? WHERE ID=?;",
                    therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), isActive.isSelected(), modifiedDate, therapist.getId());
            JOptionPane.showMessageDialog(this, "Therapist details updated successfully!");
        }
    }

    private boolean askForConfirmation(String message) {
        int result = JOptionPane.showOptionDialog(this, message, "UPDATE ALERT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"YES", "NO"}, "YES");
        return result == JOptionPane.OK_OPTION;
    }

    private void navigateToTherapistPanel() {
        JViewport container = (JViewport) getParent();
        container.setView(new TherapistsPanel());
        container.validate();
        container.repaint();
    }

    // Fetching Therapist attendance from DB
    private ArrayList<TherapistAttendance> getTherapistAttendance(Integer therapistId) {
        ArrayList<TherapistAttendance> attendances = new ArrayList<>();
        Database db = new Database();
        try {
            ResultSet rs = db.executeQuery("SELECT * FROM TherapistAttendance WHERE TherapistID=? AND CheckinTime IS NOT NULL ORDER BY Date ASC LIMIT 7", therapistId);
            while (rs.next()) {
                attendances.add(extractTherapistAttendance(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendances;
    }

    private TherapistAttendance extractTherapistAttendance(ResultSet rs) throws SQLException {
        TherapistAttendance attendance = new TherapistAttendance();
        attendance.setTherapistId(rs.getInt("ID"));
        attendance.setCheckinTime(rs.getTime("CheckinTime"));
        attendance.setCheckoutTime(rs.getTime("CheckoutTime"));
        attendance.setDate(rs.getDate("Date"));
        return attendance;
    }

   // Fetching Therapist details from DB
   private Therapist getTherapist(Integer therapistId) {
       Therapist therapist = null;
       Database db = new Database();
       try {
           ResultSet rs = db.executeQuery("SELECT * FROM Therapist WHERE ID=?", therapistId);
           if (rs.next()) {
               therapist = extractTherapist(rs);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return therapist;
   }

    private Therapist extractTherapist(ResultSet rs) throws SQLException {
        Therapist therapist = new Therapist();
        therapist.setId(rs.getInt("ID"));
        therapist.setFirstName(rs.getString("FirstName"));
        therapist.setAddress(rs.getString("Address"));
        therapist.setPhoneNumber(rs.getString("PhoneNumber"));
        therapist.setActive(rs.getBoolean("IsActive"));
        therapist.setResignationDate(rs.getDate("ResignationDate"));
        return therapist;
    }

    private void addLabelAndComponent(JLabel label, JComponent comp, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(label, gbc);
        gbc.gridx = 1;
        add(comp, gbc);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private MyButton createButton(String text, ActionListener listener) {
        MyButton button = new MyButton(text);
        button.addActionListener(listener);
        return button;
    }
}
