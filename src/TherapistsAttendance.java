/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import javax.swing.*;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author satyanarayana.y
 */
public class TherapistsAttendance extends javax.swing.JPanel {

    /**
     * Creates new form TherapistsAttendance
     */
    public TherapistsAttendance() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        therpaistNameLabel = new javax.swing.JLabel();
        therapistNameList = new javax.swing.JComboBox<>();
        checkInLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        checkInTime = new javax.swing.JCheckBox();
        checkOutTime = new javax.swing.JCheckBox();
        submit = new javax.swing.JButton();

        setBackground(new java.awt.Color(216, 235, 243));

        therpaistNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        therpaistNameLabel.setText("THERAPIST NAME");

        therapistNameList.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
//        Therapist therapist = new Therapist();
//        therapist.setId(1);
//        therapist.setFirstName("satya");
//        Therapist therapist1 = new Therapist();
//        therapist1.setId(2);
//        therapist1.setFirstName("pavani");
//
////        list.add(therapist);
////        list.add(therapist1);
////
////
////        for (each item in the ArrayList)
////        comboBox.addItem( theItem );
//
//        therapistNameList.addItem(therapist);
//        therapistNameList.addItem(therapist1);

//        therapistNameList.setModel(new javax.swing.DefaultComboBoxModel<>(Therapist[]);
        getTherapists(true, therapistNameList);
        therapistNameList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                therapistNameListActionPerformed(evt);
            }
        });

        checkInLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkInLabel.setText("CHECKIN TIME");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("CHECKOUT TIME");

        checkInTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInTimeActionPerformed(evt);
            }
        });

        checkOutTime.setActionCommand("");
        checkOutTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutTimeActionPerformed(evt);
            }
        });

        submit.setBackground(new java.awt.Color(53, 183, 234));
        submit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        submit.setText("SUBMIT");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(419, 419, 419)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(therpaistNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149)
                        .addComponent(therapistNameList, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(checkInLabel))
                            .addGap(177, 177, 177)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(checkInTime)
                                .addComponent(checkOutTime)))))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(therpaistNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(therapistNameList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkInLabel)
                    .addComponent(checkInTime))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(checkOutTime, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void therapistNameListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_therapistNameListActionPerformed
        System.out.println("selected value");
    }//GEN-LAST:event_therapistNameListActionPerformed

    private void checkInTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkInTimeActionPerformed

    private void checkOutTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkOutTimeActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        System.out.println("selected value = " + therapistNameList.getSelectedItem());
        Therapist therapist = (Therapist) therapistNameList.getSelectedItem();
        System.out.println("selected value = " + therapist.getId());
    }//GEN-LAST:event_submitActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel checkInLabel;
    private javax.swing.JCheckBox checkInTime;
    private javax.swing.JCheckBox checkOutTime;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton submit;
    private javax.swing.JComboBox<Therapist> therapistNameList;
    private javax.swing.JLabel therpaistNameLabel;
    // End of variables declaration//GEN-END:variables

    private List<Therapist> getTherapists(boolean isActive, JComboBox<Therapist> therapistNameList) {
        Database db = new Database();
        List<Therapist> therapists = new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Therapist where isActive = ?", isActive);
            while (rs.next()) {
                therapistNameList.addItem(new Therapist(rs.getInt("ID"),rs.getString("firstName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return therapists;

    }
}