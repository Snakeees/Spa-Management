package com.spa.screens;

import com.spa.dto.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.TEXTFIELD_BORDER_COLOR;

public class TherapistsAttendance extends JPanel {

    private List<Therapist> therapists = new ArrayList<>();

    private Integer selectedTherapistAttendanceId = null;
    private JLabel TitleLabel, therpaistNameLabel, checkInLabel, checkOutLabel;
    private JCheckBox checkInTime, checkOutTime;
    private MyButton submit;
    private JComboBox<Therapist> therapistNameList;

    public TherapistsAttendance() {
        initComponents();
        UIManager.put("Button.select", new Color(250, 105, 192));
    }

    private void initComponents() {
        setupLabelsAndFields();
        ArrayList<ArrayList<Object>> items = new ArrayList<>();
        items.add(new ArrayList<>(Arrays.asList(therpaistNameLabel, therapistNameList)));
        items.add(new ArrayList<>(Arrays.asList(checkInLabel, checkInTime)));
        items.add(new ArrayList<>(Arrays.asList(checkOutLabel, checkOutTime)));

        Object[][] Items = items.stream().map(l -> l.toArray(new Object[0])).toArray(Object[][]::new);

        InfoFunction backAction = (InfoPanel infoPanel) -> {};
        InfoFunction submitAction = (InfoPanel infoPanel) -> handleSubmitAction();

        InfoPanel infoPanel = new InfoPanel(TitleLabel, false, true, "SUBMIT", Items, backAction, submitAction);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
    }


    private void setupLabelsAndFields() {
        TitleLabel = InfoPanel.createLabel("THERAPIST ATTENDANCE", 20);
        therpaistNameLabel = InfoPanel.createLabel("THERAPIST NAME", 15);
        checkInLabel = InfoPanel.createLabel("CHECKIN TIME", 15);
        checkOutLabel = InfoPanel.createLabel("CHECKOUT TIME", 15);
        checkInTime = new JCheckBox();
        checkInTime.setBackground(BACKGROUND_COLOR);
        checkOutTime = new JCheckBox();
        checkOutTime.setBackground(BACKGROUND_COLOR);

        therapistNameList = new JComboBox<>();
        therapistNameList.setFont(new Font("Play", Font.PLAIN, therapistNameList.getFont().getSize()));
        therapistNameList.setPreferredSize(new MyTextField(12).getPreferredSize());

        therapistNameList.setFont(new Font("Play", 0, 12));
        List<Therapist> therapistList = getTherapists(true);
        this.therapists = therapistList;
        for (Therapist therapist : therapistList) {
            therapistNameList.addItem(therapist);
        }

        updateTherapistAttendanceForm(null);

        therapistNameList.addActionListener(this::therapistNameListActionPerformed);
        therapistNameList.setBackground(Color.WHITE);
    }

    private void therapistNameListActionPerformed(ActionEvent evt) {
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

    private void handleSubmitAction() {
        Therapist therapist = (Therapist) therapistNameList.getSelectedItem();
        Time currentTime = Time.valueOf(LocalTime.now());
        Date currentDate = Date.valueOf(LocalDate.now());
        try {
            Database database = new Database();
            if (this.selectedTherapistAttendanceId != null) {
                TherapistAttendance therapistsAttendance = getTherapistAttendanceByCurrentDate(therapist.getId());
                if (therapistsAttendance != null) {
                    if (checkInTime.isSelected() && therapistsAttendance.getCheckinTime() == null) {
                        therapistsAttendance.setCheckinTime(currentTime);
                    }
                    if (checkOutTime.isSelected() && therapistsAttendance.getCheckoutTime() == null) {
                        therapistsAttendance.setCheckoutTime(currentTime);
                    }
                    database.executeUpdate("update TherapistAttendance set checkIntime=? , checkouttime=?, date=? where ID=? ;", therapistsAttendance.getCheckinTime(), therapistsAttendance.getCheckoutTime(), therapistsAttendance.getDate(), this.selectedTherapistAttendanceId);
                    JOptionPane.showMessageDialog(this, "Updated attendance details successfully");
                }
            } else {
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
