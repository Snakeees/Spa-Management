package com.spa.screens;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import java.awt.*;

public class Reports extends JPanel {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel incomeLabel;
    private JPanel incomePanel;
    private JLabel incomeValue;
    private JLabel visitLabel;
    private JPanel visitPanel;
    private JLabel visitValue;
    public Reports() {
        initComponents();
    }

    
    private void initComponents() {

        incomePanel = new JPanel();
        incomeValue = new JLabel();
        visitPanel = new JPanel();
        visitValue = new JLabel();
        incomeLabel = new JLabel();
        visitLabel = new JLabel();

        setBackground(new Color(216, 235, 243));

        incomeValue.setFont(new Font("Play", 1, 24));
        incomeValue.setText("₹ " + getIncome());

        GroupLayout incomePanelLayout = new GroupLayout(incomePanel);
        incomePanel.setLayout(incomePanelLayout);
        incomePanelLayout.setHorizontalGroup(
                incomePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(incomePanelLayout.createSequentialGroup()
                                .addContainerGap(134, Short.MAX_VALUE)
                                .addComponent(incomeValue)
                                .addGap(128, 128, 128))
        );
        incomePanelLayout.setVerticalGroup(
                incomePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(incomePanelLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(incomeValue)
                                .addContainerGap(90, Short.MAX_VALUE))
        );

        visitValue.setFont(new Font("Play", 1, 24));
        visitValue.setText(Integer.toString(getVisits()));

        GroupLayout visitPanelLayout = new GroupLayout(visitPanel);
        visitPanel.setLayout(visitPanelLayout);
        visitPanelLayout.setHorizontalGroup(
                visitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(visitPanelLayout.createSequentialGroup()
                                .addContainerGap(158, Short.MAX_VALUE)
                                .addComponent(visitValue)
                                .addGap(140, 140, 140))
        );
        visitPanelLayout.setVerticalGroup(
                visitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(visitPanelLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(visitValue)
                                .addContainerGap(90, Short.MAX_VALUE))
        );

        incomeLabel.setFont(new Font("Play", 1, 18));
        incomeLabel.setText("Current Month Income");

        visitLabel.setFont(new Font("Play", 1, 18));
        visitLabel.setText("Current Month Visits");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(incomePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(incomeLabel, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addComponent(visitLabel)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(visitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(130, 130, 130)
                                        )))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(168, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(incomeLabel)
                                        .addComponent(visitLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(incomePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(visitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(183, Short.MAX_VALUE))
        );
    }
    // fetching total income from 1st of the month from DB which is need to be displayed in UI
    public int getIncome() {
        Database db = new Database();
        LocalDate date = LocalDate.now();
        try {
            Date startDate = dateFormat.parse(date.getYear() + "-" + date.getMonthValue() + "-1");
            ResultSet rs = db.executeQuery("select sum(s.Cost) as monthIncome from Service s, Appointment a where a.ServiceID=s.ID and a.AppointmentDate>=? and a.IsActive=true and a.AppointmentDate<=?", dateFormat.format(startDate), dateFormat.format(new Date()));
            while (rs.next()) {
                return rs.getInt("monthIncome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    // fetching no of visits from 1st of the month from DB which is need to be displayed in UI
    public int getVisits() {
        Database db = new Database();
        LocalDate date = LocalDate.now();
        try {
            Date startDate = dateFormat.parse(date.getYear() + "-" + date.getMonthValue() + "-1");
            ResultSet rs = db.executeQuery("select count(*) as visits from Appointment where AppointmentDate>=? and IsActive=true and AppointmentDate<=?", dateFormat.format(startDate), dateFormat.format(new Date()));
            while (rs.next()) {
                return rs.getInt("visits");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
