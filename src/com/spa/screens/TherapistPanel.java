package com.spa.screens;

import com.spa.dto.Therapist;
import com.spa.dto.TherapistAttendance;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TherapistPanel extends JPanel {

        Therapist therapist;
    ArrayList<TherapistAttendance> therapistAttendances;
    SimpleDateFormat requiredDateFormate = new java.text.SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");
    String sqlDateFormate;
    // Variables declaration - do not modify
    private JLabel addTherapistLabel;
    private JLabel addressLabel;
    private JTextField addressTxt;
    private ArrayList<JTextField> attendanceList;
    private JTextField att1;
    private JTextField att2;
    private JTextField att3;
    private JTextField att4;
    private JTextField att5;
    private JTextField att6;
    private JTextField att7;
    private JLabel attendanceLabel;
    private JPanel attendanceShowPanel;
    private JButton backLable;
    private JLabel currentActiveLabel;
    private JCheckBox isActive;
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberTxt;
    private JLabel resignationDateLabel;
    private com.toedter.calendar.JDateChooser resignationDateTxt;
    private JButton submit;
    private JLabel therapistNameLabel;
    private JTextField therapistNameTxt;
    public TherapistPanel(Integer therapistId, boolean isEditable) {
        therapist = getTherapist(therapistId);
        therapistAttendances = getTherapistAttendance(therapistId);
        initComponents(isEditable);
    }

        private void initComponents(boolean isEditable) {
        addTherapistLabel = new JLabel();
        therapistNameLabel = new JLabel();
        phoneNumberLabel = new JLabel();
        addressLabel = new JLabel();
        phoneNumberTxt = new JTextField();
        therapistNameTxt = new JTextField();
        addressTxt = new JTextField();
        submit = new JButton();
        backLable = new JButton();
        currentActiveLabel = new JLabel();
        resignationDateLabel = new JLabel();
        attendanceLabel = new JLabel();
        isActive = new JCheckBox();
        attendanceShowPanel = new JPanel();

        att3 = new JTextField();
        att1 = new JTextField();
        att2 = new JTextField();
        att4 = new JTextField();
        att5 = new JTextField();
        att6 = new JTextField();
        att7 = new JTextField();
        att1.setEnabled(false);
        att2.setEnabled(false);
        att3.setEnabled(false);
        att4.setEnabled(false);
        att5.setEnabled(false);
        att6.setEnabled(false);
        att7.setEnabled(false);

        attendanceList = new ArrayList<>();
        attendanceList.add(att1);
        attendanceList.add(att2);
        attendanceList.add(att3);
        attendanceList.add(att4);
        attendanceList.add(att5);
        attendanceList.add(att6);
        attendanceList.add(att7);

        resignationDateTxt = new JDateChooser();

        setBackground(new Color(216, 235, 243));

        addTherapistLabel.setBackground(new Color(216, 235, 243));
        addTherapistLabel.setText("CREATE THERAPIST");

        therapistNameLabel.setBackground(new Color(216, 235, 243));
        therapistNameLabel.setText("THERAPIST NAME");

        phoneNumberLabel.setBackground(new Color(216, 235, 243));
        phoneNumberLabel.setText("PHONE NUMBER");

        addressLabel.setBackground(new Color(216, 235, 243));
        addressLabel.setText("ADDRESS");

        submit.setBackground(new Color(53, 183, 234));
        submit.setText("CREATE");

        backLable.setBackground(new Color(53, 183, 234));
        backLable.setText("BACK");

        currentActiveLabel.setBackground(new Color(216, 235, 243));
        currentActiveLabel.setText("CURRENTLY ACTIVE");

        resignationDateLabel.setBackground(new Color(216, 235, 243));
        resignationDateLabel.setText("RESIGNATION DATE  (DD-MM-YYYY)");

        attendanceLabel.setBackground(new Color(216, 235, 243));
        attendanceLabel.setText("ATTENDANCE (For past 7 Days)");

        isActive.setBackground(new Color(216, 235, 243));
        isActive.setToolTipText("");

        attendanceShowPanel.setBackground(new Color(255, 255, 255));
        attendanceShowPanel.setBorder(BorderFactory.createLineBorder(null));

        att3.setPreferredSize(new Dimension(24, 24));

        att1.setPreferredSize(new Dimension(24, 24));

        att2.setPreferredSize(new Dimension(24, 24));

        att4.setPreferredSize(new Dimension(24, 24));

        att5.setPreferredSize(new Dimension(24, 24));

        att6.setPreferredSize(new Dimension(24, 24));

        att7.setMinimumSize(new Dimension(24, 24));
        updateAttendanceDisplay();
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        backLable.addActionListener(new ActionListener() {
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

        GroupLayout attendanceShowPanelLayout = new GroupLayout(attendanceShowPanel);
        attendanceShowPanel.setLayout(attendanceShowPanelLayout);
        attendanceShowPanelLayout.setHorizontalGroup(
                attendanceShowPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(attendanceShowPanelLayout.createSequentialGroup()
                                .addComponent(att1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att4, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att5, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att6, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att7, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
        );
        attendanceShowPanelLayout.setVerticalGroup(
                attendanceShowPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(att1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(attendanceShowPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(att3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        // Viewing and Updating the Therapist details
        if (therapist != null) {
            therapistNameTxt.setText(therapist.getFirstName());
            phoneNumberTxt.setText(therapist.getPhoneNumber());
            addressTxt.setText(therapist.getAddress());
            isActive.setSelected(therapist.isActive());
            submit.setText("UPDATE");
            resignationDateTxt.setDate(therapist.getResignationDate());
            // Updating the Therapist details
            if (isEditable) {
                addTherapistLabel.setText("UPDATE THERAPIST DETAILS");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap(4, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(currentActiveLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(attendanceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(therapistNameTxt)
                                                .addComponent(phoneNumberTxt)
                                                .addComponent(addressTxt)
                                                .addComponent(isActive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateTxt)
                                                .addComponent(attendanceShowPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap(4, Short.MAX_VALUE))

                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup()
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(600, 600, 600)
                                                        .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                )

                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(650, 650, 650)
                                                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(backLable))));
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(backLable)
                                        .addGap(15, 15, 15)
                                        .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(therapistNameLabel)
                                                .addComponent(therapistNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addressLabel)
                                                .addComponent(addressTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(currentActiveLabel)
                                                .addComponent(isActive))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(resignationDateLabel)
                                                .addComponent(resignationDateTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(attendanceLabel)
                                                .addComponent(attendanceShowPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(52, 52, 52)
                                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                );
            }
            // Viewing the therapist details
            else {
                addTherapistLabel.setText("VIEW THERAPIST DETAILS");
                resignationDateTxt.setEnabled(false);
                therapistNameTxt.setEnabled(false);
                phoneNumberTxt.setEnabled(false);
                isActive.setEnabled(false);
                addressTxt.setEnabled(false);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap(4, Short.MAX_VALUE)                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(currentActiveLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(attendanceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(therapistNameTxt)
                                                .addComponent(phoneNumberTxt)
                                                .addComponent(addressTxt)
                                                .addComponent(isActive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateTxt)
                                                .addComponent(attendanceShowPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap(4, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(570, 570, 570)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                        ))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(28, 28, 28)
                                                        .addComponent(backLable))))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(backLable)
                                        .addGap(15, 15, 15)
                                        .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(therapistNameLabel)
                                                .addComponent(therapistNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(addressLabel)
                                                .addComponent(addressTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(currentActiveLabel)
                                                .addComponent(isActive))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(resignationDateLabel)
                                                .addComponent(resignationDateTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(attendanceLabel)
                                                .addComponent(attendanceShowPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                );
            }
        }
        // Creating the therapist
        else if (isEditable) {
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap(4, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(therapistNameLabel, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                            .addComponent(addressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(phoneNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(therapistNameTxt, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                            .addComponent(phoneNumberTxt)
                                            .addComponent(addressTxt)
                                    )
                                    .addContainerGap(4, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(28, 28, 28)
                                                    .addComponent(backLable))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(600, 600, 600)
                                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(550, 550, 550)
                                                    .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE))))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(backLable)
                                    .addGap(15, 15, 15)
                                    .addComponent(addTherapistLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                    .addGap(34, 34, 34)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(therapistNameLabel)
                                            .addComponent(therapistNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(20, 20, 20)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(phoneNumberLabel)
                                            .addComponent(phoneNumberTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(20, 20, 20)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(addressLabel)
                                            .addComponent(addressTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(52, 52, 52)
                                    .addComponent(submit, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
            );
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
            attendanceList.get(i).setToolTipText(dateFormater.format(d));
            currentDate = requiredDateFormate.format(d);
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
                            db.executeUpdate("update Therapist set FirstName=?, PhoneNumber=?, Address=?, IsActive=? where ID=? ;", therapistNameTxt.getText(), phoneNumberTxt.getText(), addressTxt.getText(), isActive.isSelected(), therapist.getId());
                        }
                        else {
                            String modifiedDate = requiredDateFormate.format(resignationDateTxt.getDate().getTime());
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

}
