/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author satyanarayana.y
 */
public class Reports extends javax.swing.JPanel {

    /**
     * Creates new form Reports
     */
    public Reports() {
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

        incomePanel = new javax.swing.JPanel();
        incomeValue = new javax.swing.JLabel();
        visitPanel = new javax.swing.JPanel();
        visitValue = new javax.swing.JLabel();
        incomeLabel = new javax.swing.JLabel();
        visitLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(216, 235, 243));

        incomeValue.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        incomeValue.setText("₹ 153.00");

        javax.swing.GroupLayout incomePanelLayout = new javax.swing.GroupLayout(incomePanel);
        incomePanel.setLayout(incomePanelLayout);
        incomePanelLayout.setHorizontalGroup(
            incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup( incomePanelLayout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(incomeValue)
                .addGap(128, 128, 128))
        );
        incomePanelLayout.setVerticalGroup(
            incomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(incomePanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(incomeValue)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        visitValue.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        visitValue.setText("200");

        javax.swing.GroupLayout visitPanelLayout = new javax.swing.GroupLayout(visitPanel);
        visitPanel.setLayout(visitPanelLayout);
        visitPanelLayout.setHorizontalGroup(
            visitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup( visitPanelLayout.createSequentialGroup()
                .addContainerGap(158, Short.MAX_VALUE)
                .addComponent(visitValue)
                .addGap(137, 137, 137))
        );
        visitPanelLayout.setVerticalGroup(
            visitPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visitPanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(visitValue)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        incomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        incomeLabel.setText("Current Month Income");

        visitLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        visitLabel.setText("Current month Visits");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(incomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(incomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup( layout.createSequentialGroup()
                        .addComponent(visitLabel)
                        .addGap(333, 333, 333))
                    .addGroup( layout.createSequentialGroup()
                        .addComponent(visitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(259, 259, 259))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(incomeLabel)
                    .addComponent(visitLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(incomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(visitPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(183, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel incomeLabel;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JLabel incomeValue;
    private javax.swing.JLabel visitLabel;
    private javax.swing.JPanel visitPanel;
    private javax.swing.JLabel visitValue;
    // End of variables declaration//GEN-END:variables
}
