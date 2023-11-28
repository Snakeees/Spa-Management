package com.spa.screens;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import com.spa.dto.Therapist;
import com.spa.dto.TherapistAttendance;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

/**
 * @author satyanarayana.y
 */
public class TherapistsAttendance extends JPanel {

    private List<Therapist> therapists = new ArrayList<>();

    private Integer selectedTherapistAttendanceId = null;
    private JLabel checkInLabel;
    private JCheckBox checkInTime;
    private JCheckBox checkOutTime;
    private JLabel jLabel2;
    private JButton submit;
    private JComboBox<Therapist> therapistNameList;
    private JLabel therpaistNameLabel;


    /**
     * Creates new form com.spa.screens.TherapistsAttendance
     */
    public TherapistsAttendance() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        therpaistNameLabel = new JLabel();
        therapistNameList = new JComboBox<>();
        checkInLabel = new JLabel();
        jLabel2 = new JLabel();
        checkInTime = new JCheckBox();
        checkOutTime = new JCheckBox();
        submit = new JButton();

        setBackground(new Color(216, 235, 243));

        therpaistNameLabel.setFont(new Font("Play", 1, 14));
        therpaistNameLabel.setText("THERAPIST NAME");


        therapistNameList.setFont(new Font("Play", 0, 12));
        List<Therapist> therapistList = getTherapists(true);
        this.therapists = therapistList;
        for (Therapist therapist : therapistList) {
            therapistNameList.addItem(therapist);
        }

        updateTherapistAttendanceForm(null);

        therapistNameList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                therapistNameListActionPerformed(evt);
            }
        });
        checkInLabel.setFont(new Font("Play", 1, 14));
        checkInLabel.setText("CHECKIN TIME");
        jLabel2.setFont(new Font("Play", 1, 14));
        jLabel2.setText("CHECKOUT TIME");
        submit.setBackground(new Color(53, 183, 234));
        submit.setFont(new Font("Play", 1, 12));
        submit.setText("SUBMIT");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(4, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(therpaistNameLabel, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
                                                .addGap(149, 149, 149)
                                                .addComponent(therapistNameList, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(submit, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel2)
                                                                .addComponent(checkInLabel))
                                                        .addGap(177, 177, 177)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addComponent(checkInTime)
                                                                .addComponent(checkOutTime)))))
                                .addContainerGap(4, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(therpaistNameLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(therapistNameList, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(40,40,40)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(checkInLabel)
                                        .addComponent(checkInTime))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(checkOutTime, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(74, 74, 74)
                                .addComponent(submit, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(207, Short.MAX_VALUE))
        );
    }
    private void therapistNameListActionPerformed(ActionEvent evt) {//GEN-FIRST:event_therapistNameListActionPerformed
        Therapist therapist = (Therapist) therapistNameList.getSelectedItem();
        if (therapist != null) {
            updateTherapistAttendanceForm(therapist.getId());
        }
    }

    private void updateTherapistAttendanceForm(Integer therapistId) {
        Therapist therapist = null;
        if (this.therapists != null && !this.therapists.isEmpty()) {
            if (therapistId != null) {
                for (Therapist therapistLoc : therapists) {
                    if (therapistLoc.getId() == therapistId) {
                        therapist = therapistLoc;
                    }
                }
            }
            else {
                therapist = (Therapist) therapistNameList.getSelectedItem();
                if (therapist != null) {
                    therapistId = therapist.getId();
                }
            }
        }
        if (therapist != null) {
            TherapistAttendance therapistAttendance = getTherapistAttendanceByCurrentDate(therapistId);
            if (therapistAttendance != null) {
                this.selectedTherapistAttendanceId = therapistAttendance.getId();
                checkInTime.setSelected(therapistAttendance.getCheckinTime() != null);
                checkOutTime.setSelected(therapistAttendance.getCheckoutTime() != null);
                therapistNameList.setSelectedItem(therapist);
            }
            else {
                this.selectedTherapistAttendanceId = null;
                checkInTime.setSelected(false);
                checkOutTime.setSelected(false);
                therapistNameList.setSelectedItem(therapist);
            }
        }
    }

    private void submitActionPerformed(ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        Therapist therapist = (Therapist) therapistNameList.getSelectedItem();
        LocalTime now = LocalTime.now();
        Time currentTime = Time.valueOf(now);
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.valueOf(localDate);
        try {
            Database database = new Database();
            if (this.selectedTherapistAttendanceId != null) {
                TherapistAttendance therapistsAttendance = getTherapistAttendanceByCurrentDate(therapist.getId());
                if (therapistsAttendance != null) {
                    if (checkInTime.isSelected() && therapistsAttendance.getCheckinTime() == null) {
                        therapistsAttendance.setCheckinTime(currentTime);
                    }
                    else if (!checkInTime.isSelected()) {
                        therapistsAttendance.setCheckinTime(null);
                    }
                    if (checkOutTime.isSelected() && therapistsAttendance.getCheckoutTime() == null) {
                        therapistsAttendance.setCheckoutTime(currentTime);
                    }
                    else if (!checkOutTime.isSelected()) {
                        therapistsAttendance.setCheckoutTime(null);
                    }
                    database.executeUpdate("update TherapistAttendance set checkIntime=? , checkouttime=?, date=? where ID=? ;", therapistsAttendance.getCheckinTime(), therapistsAttendance.getCheckoutTime(), therapistsAttendance.getDate(), this.selectedTherapistAttendanceId);
                    JOptionPane.showMessageDialog(this, "Updated attendance details successfully");
                }
            }
            else {
                TherapistAttendance therapistAttendance = new TherapistAttendance();
                therapistAttendance.setTherapistId(therapist.getId());
                if (checkInTime.isSelected()) {
                    therapistAttendance.setCheckinTime(currentTime);
                }
                else {
                    therapistAttendance.setCheckinTime(null);
                }
                if (checkOutTime.isSelected()) {
                    therapistAttendance.setCheckoutTime(currentTime);
                }
                else {
                    therapistAttendance.setCheckoutTime(null);
                }
                therapistAttendance.setDate(currentDate);
                database.executeUpdate("INSERT INTO TherapistAttendance ( therapistid, checkintime, checkouttime, date) VALUES(?,?,?,?)", therapistAttendance.getTherapistId(), therapistAttendance.getCheckinTime(), therapistAttendance.getCheckoutTime(), therapistAttendance.getDate());
                TherapistAttendance insertedAttendance= getTherapistAttendanceByCurrentDate(therapist.getId());
                this.selectedTherapistAttendanceId=(insertedAttendance!=null)?insertedAttendance.getId():null;
                JOptionPane.showMessageDialog(this, "Added attendance details successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TherapistAttendance getTherapistAttendanceByCurrentDate(int therapistId) {
        Database db = new Database();
        try {
            ResultSet rs = db.executeQuery("select * from TherapistAttendance where therapistID= ? and date = CURDATE() limit 1", therapistId);
            while (rs.next()) {
                return new TherapistAttendance(rs.getInt("ID"),
                        rs.getInt("TherapistID"),
                        rs.getDate("date"),
                        rs.getTime("CheckinTime"),
                        rs.getTime("CheckoutTime")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Therapist> getTherapists(boolean isActive) {
        Database db = new Database();
        List<Therapist> therapistsList = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Therapist where isActive = ?", isActive);
            while (rs.next()) {
                therapistsList.add(new Therapist(rs.getInt("ID"), rs.getString("firstName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return therapistsList;
    }
}
