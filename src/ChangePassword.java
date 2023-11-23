import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ChangePassword extends javax.swing.JPanel {

    private final int userId;

    public int getUserId() {
        return userId;
    }

    public ChangePassword(int userId) {
        this.userId = userId;
        initComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        container = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        oldPasswordLabel = new javax.swing.JLabel();
        confirmPasswordLabel = new javax.swing.JLabel();
        newPasswordLabel = new javax.swing.JLabel();
        confirmPasswordTxt = new javax.swing.JPasswordField();
        oldPasswordTxt = new javax.swing.JPasswordField();
        newPasswordTxt = new javax.swing.JPasswordField();
        submit = new javax.swing.JButton();

        setBackground(new java.awt.Color(216, 235, 243));

        container.setBackground(new java.awt.Color(216, 235, 243));

        content.setBackground(new java.awt.Color(216, 235, 243));
        oldPasswordLabel.setText("OLD PASSWORD");
        confirmPasswordLabel.setText("CONFIRM PASSWORD");
        newPasswordLabel.setText("NEW PASSWORD");

        oldPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        newPasswordLabel.setFont(new Font("Play", Font.BOLD, 15));
        confirmPasswordTxt.setToolTipText("");
        submit.setBackground(new java.awt.Color(53, 183, 234));
        submit.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        submit.setLabel("UPDATE");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
                contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addGap(566, 566, 566)
                                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(oldPasswordLabel)
                                                        .addComponent(newPasswordLabel)
                                                        .addComponent(confirmPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                                )
                                                .addGap(87, 87, 87)
                                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(confirmPasswordTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                                        .addComponent(oldPasswordTxt)
                                                        .addComponent(newPasswordTxt)
                                                ))
                                        .addGroup(contentLayout.createSequentialGroup()
                                                .addGap(710, 710, 710)
                                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(590, Short.MAX_VALUE))
        );
        contentLayout.setVerticalGroup(
                contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGap(21, 21, 21)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(oldPasswordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(oldPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(newPasswordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGroup(contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(confirmPasswordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmPasswordLabel))
                                .addGap(27, 27, 27)
                                .addGap(97, 97, 97)
                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(962, Short.MAX_VALUE))
        );
        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );


    }

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {
        if (oldPasswordTxt.getText() != null && !oldPasswordTxt.getText().trim().equals("") && newPasswordTxt.getText() != null && !newPasswordTxt.getText().trim().equals("") && confirmPasswordTxt.getText() != null && !confirmPasswordTxt.getText().trim().equals("")) {
            Database db = new Database();
            boolean userWithPasswordExist = false;
            String oldHashedPassword = HashPassword.hashPassword(oldPasswordTxt.getText());
            ResultSet rs = db.executeQuery("SELECT id FROM UserLogin WHERE ID = ? AND Password = ? AND IsActive = ?", getUserId(), oldHashedPassword, true);
            try {
                if (rs.next()) {
                    userWithPasswordExist = true;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!userWithPasswordExist) {
                JOptionPane.showMessageDialog(this, "Old password did not match with existing password.");
            } else if (!newPasswordTxt.getText().equals(confirmPasswordTxt.getText())) {
                JOptionPane.showMessageDialog(this, "Confirm password should match with new password ");
            } else {
                String newHashedPassword = HashPassword.hashPassword(newPasswordTxt.getText());
                db.executeUpdate("update UserLogin set password=? where ID=? ;", newHashedPassword, getUserId());
                Container container = getParent();
                getParent().remove(1);
                container.add(new AccountsPanel(), BorderLayout.CENTER, 1);
                container.validate();
                container.repaint();
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Required fields can not be empty.");
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JPasswordField confirmPasswordTxt;
    private javax.swing.JPanel container;
    private javax.swing.JPanel content;
    private javax.swing.JLabel oldPasswordLabel;
    private javax.swing.JPasswordField oldPasswordTxt;
    private javax.swing.JLabel newPasswordLabel;
    private javax.swing.JPasswordField newPasswordTxt;
    private javax.swing.JButton submit;
}
