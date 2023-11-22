import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TherapistsPanel extends javax.swing.JPanel {
    /**
     * Creates new form therapistsPanel
     */
    public TherapistsPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        addTherapist = new javax.swing.JButton();
        therapistListScrollPane = new javax.swing.JScrollPane();
        therapistListPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(216, 235, 243));

        addTherapist.setBackground(new java.awt.Color(53, 183, 234));
        addTherapist.setText("ADD");
        addTherapist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addtherapistActionPerformed(evt);
            }
        });

        therapistListPanel.setBackground(new java.awt.Color(216, 235, 243));

        javax.swing.GroupLayout therapistListPanelLayout = new javax.swing.GroupLayout(therapistListPanel);
        therapistListPanel.setLayout(therapistListPanelLayout);
        therapistListPanelLayout.setHorizontalGroup(
                therapistListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 748, Short.MAX_VALUE)
        );
        therapistListPanelLayout.setVerticalGroup(
                therapistListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 518, Short.MAX_VALUE)
        );

        therapistListScrollPane.setViewportView(therapistListPanel);
        therapistListScrollPane.setBorder(null);
        therapistListPanel.setLayout(new BoxLayout(therapistListPanel, BoxLayout.Y_AXIS));
        List<JPanel> therapists=getUsertherapists();
        for(int i=0;i<therapists.size();i++){
            therapistListPanel.add(therapists.get(i));
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup( layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addTherapist)
                                .addGap(45, 45, 45))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(therapistListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(459, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(addTherapist)
                                .addGap(58, 58, 58)
                                .addComponent(therapistListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>


    private ArrayList<JPanel> getUsertherapists() {
        Database db=new Database();
        ArrayList<JPanel> therapists=new ArrayList<>();
        try {
            ResultSet rs = db.executeQuery("select * from Therapist");
            while (rs.next()) {
                String loginName=rs.getString("FirstName");
                //therapists.add(new SingleItem());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return therapists;

    }
    private void addtherapistActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        Container container=getParent();
        getParent().remove(1);
        container.add(new TherapistPanel(),BorderLayout.CENTER,1);
        container.validate();
        container.repaint();
    }


    // Variables declaration - do not modify
    private javax.swing.JPanel therapistListPanel;
    private javax.swing.JScrollPane therapistListScrollPane;
    private javax.swing.JButton addTherapist;
    // End of variables declaration
}
