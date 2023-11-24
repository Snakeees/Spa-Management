import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

public class TherapistPanel extends javax.swing.JPanel {

    /**
     * Creates new form TherapistPanel
     */
    Therapist therapist;
    ArrayList<TherapistAttendance> therapistAttendances;
    SimpleDateFormat requiredDateFormate=new java.text.SimpleDateFormat("yyyy-MM-dd");
    public TherapistPanel(Integer therapistId,boolean isEditable) {
        therapist=getTherapist(therapistId);
        therapistAttendances=getTherapistAttendance(therapistId);
        initComponents(isEditable);
    }

    private ArrayList<TherapistAttendance> getTherapistAttendance(Integer therapistId) {
        ArrayList<TherapistAttendance> attendancesList=new ArrayList<>();
        TherapistAttendance therapistAttendance;
        Database db=new Database();
        ResultSet rs=db.executeQuery("Select * from TherapistAttendance where TherapistID=? order by Date asc limit 7",therapistId);
        try{
            while (rs.next()){
                therapistAttendance=new TherapistAttendance();
                therapistAttendance.setTherapistId(rs.getInt("ID"));
                therapistAttendance.setCheckinTime(rs.getTime("CheckinTime"));
                therapistAttendance.setCheckoutTime(rs.getTime("CheckoutTime"));
                therapistAttendance.setDate(rs.getDate("Date"));
                attendancesList.add(therapistAttendance);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return attendancesList;
    }

    private Therapist getTherapist(Integer therapistId) {
        Therapist selectedTherapist=null;
        Database db=new Database();
        ResultSet rs=db.executeQuery("Select * from Therapist where ID=?",therapistId);
        try{
            while (rs.next()){
                selectedTherapist=new Therapist();
                selectedTherapist.setActive(rs.getBoolean("IsActive"));
                selectedTherapist.setId(rs.getInt("ID"));
                selectedTherapist.setFirstName(rs.getString("FirstName"));
                selectedTherapist.setAddress(rs.getString("Address"));
                selectedTherapist.setPhoneNumber(rs.getString("PhoneNumber"));
                selectedTherapist.setResignationDate(rs.getDate("ResignationDate"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return selectedTherapist;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(boolean isEditable) {

        addTherapistLabel = new javax.swing.JLabel();
        therapistNameLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        phoneNumberTxt = new javax.swing.JTextField();
        therapistNameTxt = new javax.swing.JTextField();
        addressTxt = new javax.swing.JTextField();
        submit = new javax.swing.JButton();
        backLable = new javax.swing.JButton();
        currentActiveLabel = new javax.swing.JLabel();
        resignationDateLabel = new javax.swing.JLabel();
        attendanceLabel = new javax.swing.JLabel();
        isActive = new javax.swing.JCheckBox();
        attendanceShowPanel = new javax.swing.JPanel();

        att3 = new javax.swing.JTextField();
        att1 = new javax.swing.JTextField();
        att2 = new javax.swing.JTextField();
        att4 = new javax.swing.JTextField();
        att5 = new javax.swing.JTextField();
        att6 = new javax.swing.JTextField();
        att7 = new javax.swing.JTextField();
        att1.setEnabled(false);
        att2.setEnabled(false);
        att3.setEnabled(false);
        att4.setEnabled(false);
        att5.setEnabled(false);
        att6.setEnabled(false);
        att7.setEnabled(false);

        attendanceList=new ArrayList<>();
        attendanceList.add(att1);
        attendanceList.add(att2);
        attendanceList.add(att3);
        attendanceList.add(att4);
        attendanceList.add(att5);
        attendanceList.add(att6);
        attendanceList.add(att7);

        resignationDateTxt = new javax.swing.JFormattedTextField();

        setBackground(new java.awt.Color(216, 235, 243));

        addTherapistLabel.setBackground(new java.awt.Color(216, 235, 243));
        addTherapistLabel.setText("ADD THERAPIST");

        therapistNameLabel.setBackground(new java.awt.Color(216, 235, 243));
        therapistNameLabel.setText("THERAPIST NAME");

        phoneNumberLabel.setBackground(new java.awt.Color(216, 235, 243));
        phoneNumberLabel.setText("PHONE NUMBER");

        addressLabel.setBackground(new java.awt.Color(216, 235, 243));
        addressLabel.setText("ADDRESS");

        submit.setBackground(new java.awt.Color(53, 183, 234));
        submit.setText("CREATE");

        backLable.setBackground(new java.awt.Color(53, 183, 234));
        backLable.setText("BACK");

        currentActiveLabel.setBackground(new java.awt.Color(216, 235, 243));
        currentActiveLabel.setText("CURRENTLY ACTIVE");

        resignationDateLabel.setBackground(new java.awt.Color(216, 235, 243));
        resignationDateLabel.setText("RESIGNATION DATE");

        attendanceLabel.setBackground(new java.awt.Color(216, 235, 243));
        attendanceLabel.setText("ATTENDANCE( For past 7 days)");

        isActive.setBackground(new java.awt.Color(216, 235, 243));
        isActive.setToolTipText("");

        attendanceShowPanel.setBackground(new java.awt.Color(255, 255, 255));
        attendanceShowPanel.setBorder(javax.swing.BorderFactory.createLineBorder(null));

        att3.setPreferredSize(new java.awt.Dimension(24, 22));

        att1.setPreferredSize(new java.awt.Dimension(24, 22));

        att2.setPreferredSize(new java.awt.Dimension(24, 22));

        att4.setPreferredSize(new java.awt.Dimension(24, 22));

        att5.setPreferredSize(new java.awt.Dimension(24, 22));

        att6.setPreferredSize(new java.awt.Dimension(24, 22));

        att7.setMinimumSize(new java.awt.Dimension(24, 22));
        updateAttendanceDisplay();
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        backLable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        javax.swing.GroupLayout attendanceShowPanelLayout = new javax.swing.GroupLayout(attendanceShowPanel);
        attendanceShowPanel.setLayout(attendanceShowPanelLayout);
        attendanceShowPanelLayout.setHorizontalGroup(
                attendanceShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(attendanceShowPanelLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(att1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(att3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(att7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        attendanceShowPanelLayout.setVerticalGroup(
                attendanceShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(att1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup( attendanceShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(att3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(att7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resignationDateTxt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        if(therapist!=null){
            therapistNameTxt.setText(therapist.getFirstName());
            phoneNumberTxt.setText(therapist.getPhoneNumber());
            addressTxt.setText(therapist.getAddress());
            isActive.setSelected(therapist.isActive());
            submit.setText("UPDATE");
            //should check resignationDate
//            resignationDateTxt.
            if(isEditable){
                addTherapistLabel.setText("UPDATE THERAPIST DETAILS");
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(434, 434, 434)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(phoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(currentActiveLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(attendanceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(275, 275, 275)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(therapistNameTxt)
                                                .addComponent(phoneNumberTxt)
                                                .addComponent(addressTxt)
                                                .addComponent(isActive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateTxt)
                                                .addComponent(attendanceShowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(576, 576, 576))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(650, 650, 650)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(28, 28, 28)
                                                        .addComponent(backLable)))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(backLable)
                                        .addGap(18, 18, 18)
                                        .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(therapistNameLabel)
                                                .addComponent(therapistNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addressLabel)
                                                .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(currentActiveLabel)
                                                .addComponent(isActive))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(resignationDateLabel)
                                                .addComponent(resignationDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(attendanceLabel)
                                                .addComponent(attendanceShowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(186, 186, 186))
                );
            }
            else{
                addTherapistLabel.setText("VIEW THERAPIST DETAILS");
                resignationDateTxt.setEnabled(false);
                therapistNameTxt.setEnabled(false);
                phoneNumberTxt.setEnabled(false);
                isActive.setEnabled(false);
                addressTxt.setEnabled(false);

                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(434, 434, 434)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(therapistNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(phoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(currentActiveLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(attendanceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(275, 275, 275)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(therapistNameTxt)
                                                .addComponent(phoneNumberTxt)
                                                .addComponent(addressTxt)
                                                .addComponent(isActive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resignationDateTxt)
                                                .addComponent(attendanceShowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(576, 576, 576))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(546, 546, 546)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                ))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGap(28, 28, 28)
                                                        .addComponent(backLable)))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(backLable)
                                        .addGap(18, 18, 18)
                                        .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(therapistNameLabel)
                                                .addComponent(therapistNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addressLabel)
                                                .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(currentActiveLabel)
                                                .addComponent(isActive))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(resignationDateLabel)
                                                .addComponent(resignationDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(attendanceLabel)
                                                .addComponent(attendanceShowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
//                                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(186, 186, 186))

                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(backLable)
                                        .addGap(18, 18, 18)
                                        .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(therapistNameLabel)
                                                .addComponent(therapistNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(phoneNumberLabel)
                                                .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addressLabel)
                                                .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(currentActiveLabel)
                                                .addComponent(isActive))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(resignationDateLabel)
                                                .addComponent(resignationDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(attendanceLabel)
                                                .addComponent(attendanceShowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
//                                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(186, 186, 186))
                );
            }

        }
        else if(isEditable){
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(434, 434, 434)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(therapistNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                            .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(phoneNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(275, 275, 275)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(therapistNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                            .addComponent(phoneNumberTxt)
                                            .addComponent(addressTxt))
                                    .addGap(576, 576, 576))
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(28, 28, 28)
                                                    .addComponent(backLable))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(600, 600, 600)
                                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(571, 571, 571)
                                                    .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(20, 20, 20)
                                    .addComponent(backLable)
                                    .addGap(11, 11, 11)
                                    .addComponent(addTherapistLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(34, 34, 34)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(therapistNameLabel)
                                            .addComponent(therapistNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(27, 27, 27)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(phoneNumberLabel)
                                            .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(31, 31, 31)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(addressLabel)
                                            .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(52, 52, 52)
                                    .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(349, Short.MAX_VALUE))
            );
        }

    }// </editor-fold>

    private void updateAttendanceDisplay() {
        String currentDate,listDate;
        int x=therapistAttendances.size()-1;

        for(int i=6,j=x,k=x;i>=0;i--,k--){
            Date d=new Date();
            d.setDate(d.getDate()-(x-k));
            currentDate=requiredDateFormate.format(d);
            if(j>=0) {
                listDate = requiredDateFormate.format(therapistAttendances.get(j).getDate());
                if (currentDate.equals(listDate)) {
                    attendanceList.get(i).setBackground(new java.awt.Color(27, 112, 38));
                    j--;
                }
                else {
                    attendanceList.get(i).setBackground(new java.awt.Color(166, 7, 7));
                }
            }
            else{
                attendanceList.get(i).setBackground(new java.awt.Color(166, 7, 7));
            }
        }

    }

    private void backLabelActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Container container = getParent();
        getParent().remove(1);
        container.add(new TherapistsPanel(), BorderLayout.CENTER, 1);
        container.validate();
        container.repaint();
    }

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        Database db=new Database();
        if(therapist==null){
            if(therapistNameTxt.getText()!=null && !therapistNameTxt.getText().trim().equals("") && phoneNumberTxt.getText()!=null && addressTxt.getText()!=null &&  !phoneNumberTxt.getText().trim().equals("") && !addressTxt.getText().trim().equals("")) {
                db.executeUpdate("INSERT INTO Therapist ( FirstName, PhoneNumber, Address, IsActive) VALUES(?,?,?,?)", therapistNameTxt.getText(), phoneNumberTxt.getText(),addressTxt.getText(), true);
                Container container = getParent();
                getParent().remove(1);
                container.add(new TherapistsPanel(), BorderLayout.CENTER, 1);
                container.validate();
                container.repaint();
                JOptionPane.showMessageDialog(this, "Therapist created successfully!" );
            }
            else{
                JOptionPane.showMessageDialog(this, "Required fields can not be empty. " );
            }
        }
        else{
            if(therapistNameTxt.getText()!=null && !therapistNameTxt.getText().trim().equals("") && phoneNumberTxt.getText()!=null && addressTxt.getText()!=null &&  !phoneNumberTxt.getText().trim().equals("") && !addressTxt.getText().trim().equals("")) {
                if(resignationDateTxt.getText().trim().equals("")){
                    db.executeUpdate("update Therapist set FirstName=?, PhoneNumber=?, Address=?, IsActive=? where ID=? ;", therapistNameTxt.getText(), phoneNumberTxt.getText(),addressTxt.getText(),isActive.isSelected() , therapist.getId());
                }
                else{
                    db.executeUpdate("update Therapist set FirstName=?, PhoneNumber=?, Address=?, IsActive=?, ResignationDate=? where ID=? ;", therapistNameTxt.getText(), phoneNumberTxt.getText(),addressTxt.getText(),isActive.isSelected() , resignationDateTxt.getText(), therapist.getId());
                }
                Container container = getParent();
                getParent().remove(1);
                container.add(new TherapistsPanel(), BorderLayout.CENTER, 1);
                container.validate();
                container.repaint();
                JOptionPane.showMessageDialog(this, "Therapist details updated successfully!" );
            }
            else{
                JOptionPane.showMessageDialog(this, "Required details can not be empty.");
            }
        }

    }


    // Variables declaration - do not modify
    private javax.swing.JLabel addTherapistLabel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTxt;
    private ArrayList<javax.swing.JTextField> attendanceList;
    private javax.swing.JTextField att1;
    private javax.swing.JTextField att2;
    private javax.swing.JTextField att3;
    private javax.swing.JTextField att4;
    private javax.swing.JTextField att5;
    private javax.swing.JTextField att6;
    private javax.swing.JTextField att7;
    private javax.swing.JLabel attendanceLabel;
    private javax.swing.JPanel attendanceShowPanel;
    private javax.swing.JButton backLable;
    private javax.swing.JLabel currentActiveLabel;
    private javax.swing.JCheckBox isActive;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneNumberTxt;
    private javax.swing.JLabel resignationDateLabel;
    private javax.swing.JFormattedTextField resignationDateTxt;
    private javax.swing.JButton submit;
    private javax.swing.JLabel therapistNameLabel;
    private javax.swing.JTextField therapistNameTxt;
    String sqlDateFormate;
    // End of variables declaration
}
