package com.spa.screens;

import com.spa.dto.Therapist;
import com.spa.dto.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TherapistPanel extends JPanel {

    // Existing variables
    Therapist therapist;
    ArrayList<TherapistAttendance> therapistAttendances;
    SimpleDateFormat requiredDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    String sqlDateFormat;

    // UI Components
    private ArrayList<JTextField> attendanceList;
    private JTextField box1, box2, box3, box4, box5, box6, box7;
    private JLabel addTherapistLabel;
    private JLabel addressLabel;
    private MyButton backButton;
    private JTextField addressTxt;
    private JLabel attendanceLabel;
    private JPanel attendanceShowPanel;
    private JLabel currentActiveLabel;
    private JCheckBox isActive;
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberTxt;
    private JLabel resignationDateLabel;
    private JDateChooser resignationDateTxt;
    private MyButton submit;
    private JLabel therapistNameLabel;
    private JTextField therapistNameTxt;

    public TherapistPanel(Integer therapistId, boolean isEditable) {
        therapist = getTherapist(therapistId);
        therapistAttendances = getTherapistAttendance(therapistId);
        initComponents(isEditable);
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    private void initComponents(boolean isEditable) {
        addTherapistLabel = new JLabel();
        therapistNameLabel = new JLabel();
        phoneNumberLabel = new JLabel();
        addressLabel = new JLabel();
        phoneNumberTxt = new MyTextField(20);
        therapistNameTxt = new MyTextField(20);
        addressTxt = new MyTextField(20);
        submit = new MyButton();
        backButton = new MyButton();
        currentActiveLabel = new JLabel();
        resignationDateLabel = new JLabel();
        attendanceLabel = new JLabel();
        isActive = new JCheckBox();
        attendanceShowPanel = new JPanel();

        box3 = new MyTextField();
        box1 = new MyTextField();
        box2 = new MyTextField();
        box4 = new MyTextField();
        box5 = new MyTextField();
        box6 = new MyTextField();
        box7 = new MyTextField();
        box1.setEnabled(false);
        box2.setEnabled(false);
        box3.setEnabled(false);
        box4.setEnabled(false);
        box5.setEnabled(false);
        box6.setEnabled(false);
        box7.setEnabled(false);

        attendanceList = new ArrayList<>();
        attendanceList.add(box1);
        attendanceList.add(box2);
        attendanceList.add(box3);
        attendanceList.add(box4);
        attendanceList.add(box5);
        attendanceList.add(box6);
        attendanceList.add(box7);

        resignationDateTxt = new JDateChooser();

        setBackground(new Color(255, 220, 241));

        addTherapistLabel.setBackground(new Color(255, 220, 241));
        addTherapistLabel.setText("CREATE THERAPIST");

        therapistNameLabel.setBackground(new Color(255, 220, 241));
        therapistNameLabel.setText("THERAPIST NAME");

        phoneNumberLabel.setBackground(new Color(255, 220, 241));
        phoneNumberLabel.setText("PHONE NUMBER");

        addressLabel.setBackground(new Color(255, 220, 241));
        addressLabel.setText("ADDRESS");

        submit.setBackground(new Color(145, 73, 116));
        submit.setText("CREATE");

        backButton.setBackground(new Color(145, 73, 116));
        backButton.setText("BACK");

        currentActiveLabel.setBackground(new Color(255, 220, 241));
        currentActiveLabel.setText("CURRENTLY ACTIVE");

        resignationDateLabel.setBackground(new Color(255, 220, 241));
        resignationDateLabel.setText("RESIGNATION DATE  (DD-MM-YYYY)");

        attendanceLabel.setBackground(new Color(255, 220, 241));
        attendanceLabel.setText("ATTENDANCE (For past 7 Days)");

        isActive.setBackground(new Color(255, 220, 241));
        isActive.setToolTipText("");

        attendanceShowPanel.setBackground(new Color(255, 255, 255));
        attendanceShowPanel.setBorder(BorderFactory.createLineBorder(null));
        attendanceShowPanel.setSize((24*7+6*5), 24);

        Dimension boxSize = new Dimension(24, 24);

        box1.setPreferredSize(boxSize);
        box2.setPreferredSize(boxSize);
        box3.setPreferredSize(boxSize);
        box4.setPreferredSize(boxSize);
        box5.setPreferredSize(boxSize);
        box6.setPreferredSize(boxSize);
        box7.setPreferredSize(boxSize);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backLabelActionPerformed(evt);
            }
        });
        addTherapistLabel.setFont(new Font("Play", Font.BOLD, 20));
        phoneNumberLabel.setFont(new Font("Play", Font.BOLD, 15));
        therapistNameLabel.setFont(new Font("Play", Font.BOLD, 15));
        addressLabel.setFont(new Font("Play", Font.BOLD, 15));
        currentActiveLabel.setFont(new Font("Play", Font.BOLD, 15));
        attendanceLabel.setFont(new Font("Play", Font.BOLD, 15));
        resignationDateLabel.setFont(new Font("Play", Font.BOLD, 15));
        resignationDateTxt.setDateFormatString("dd-MM-yyyy");

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
        attendanceShowPanel.setLayout(flowLayout);

        attendanceShowPanel.add(box1);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box2);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box3);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box4);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box5);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box6);
        attendanceShowPanel.add(Box.createHorizontalStrut(5));
        attendanceShowPanel.add(box7);

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

        // Set the therapist details if not null
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
            submit.setText("UPDATE");
        }

        if (isEditable) {
            // Submit or Create button
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2; // Span two columns for the button
            gbc.fill = GridBagConstraints.NONE; // Do not stretch the button
            gbc.anchor = GridBagConstraints.CENTER; // Center the button
            gbc.insets = new Insets(30, 100, 0, 100); // Set padding around components
            add(submit, gbc);
        } else {
            therapistNameTxt.setEnabled(false);
            phoneNumberTxt.setEnabled(false);
            addressTxt.setEnabled(false);
            isActive.setEnabled(false);
            resignationDateTxt.setEnabled(false);
        }
    }

    // Fetching the therapist attendance and updating it in the UI
    private void updateAttendanceDisplay() {
        String currentDate;
        String listDate;
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

/*
            if (j >= 0) {
                listDate = requiredDateFormate.format(therapistAttendances.get(j).getDate());
                if (currentDate.equals(listDate)) {
                    attendanceList.get(i).setBackground(new Color(27, 112, 38));
                    j--;
                }
                else {
                    attendanceList.get(i).setBackground(new Color(166, 7, 7));
                }
            }
            else {
                attendanceList.get(i).setBackground(new Color(166, 7, 7));
            }
*/
        }
    }
    //This method navigates to Therapist Table page when back button is clicked
    private void backLabelActionPerformed(ActionEvent evt) {
        JViewport container = (JViewport) getParent();
        container.setView(new TherapistsPanel());
        container.validate();
        container.repaint();
    }
    // This method get execute when submit button is clicked while updating and creating Therapist details
    private void submitActionPerformed(ActionEvent evt) {
        Database db = new Database();
        if (therapist == null) {
            if (therapistNameTxt.getText() != null && !therapistNameTxt.getText().trim().equals("") && phoneNumberTxt.getText() != null && addressTxt.getText() != null && !phoneNumberTxt.getText().trim().equals("") && !addressTxt.getText().trim().equals("")) {
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
            if (therapistNameTxt.getText() != null && !therapistNameTxt.getText().trim().equals("") && phoneNumberTxt.getText() != null && addressTxt.getText() != null && !phoneNumberTxt.getText().trim().equals("") && !addressTxt.getText().trim().equals("")) {
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
    // Fetching Therapist attendance from DB
    private ArrayList<TherapistAttendance> getTherapistAttendance(Integer therapistId) {
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

    private void addLabelAndComponent(JLabel label, JComponent comp, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(label, gbc);
        gbc.gridx = 1;
        //gbc.weightx = .1;  // This gives extra space to the input fields
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comp, gbc);
    }
}
