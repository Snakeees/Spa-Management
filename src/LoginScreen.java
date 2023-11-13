import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class LoginScreen extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginScreen() {
        createFrame();
        JPanel mainPanel = createMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER); // Use BorderLayout for JFrame
    }

    private void createFrame() {
        setTitle("Serenity Spa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout for auto-spacing
        panel.setBackground(Color.decode("#d8ebf3"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        panel.add(createHeader(), gbc);
        panel.add(createUsernamePanel(), gbc);
        panel.add(createPasswordPanel(), gbc);
        panel.add(createSubmitButtonPanel(), gbc);
        setSize(400, 300);

        return panel;
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        JLabel heading = new JLabel("Serenity Spa");
        heading.setFont(new Font("Play", Font.BOLD, 40));
        headerPanel.add(heading);

        return headerPanel;
    }

    private JPanel createUsernamePanel() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("USERNAME ");
        userLabel.setFont(new Font("Play", Font.PLAIN, 20));
        userField = new JTextField(20);
        userPanel.add(userLabel);
        userPanel.add(userField);

        return userPanel;
    }

    private JPanel createPasswordPanel() {
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        passPanel.setOpaque(false);

        JLabel passLabel = new JLabel("PASSWORD ");
        passLabel.setFont(new Font("Play", Font.PLAIN, 20));
        passField = new JPasswordField(20);
        passPanel.add(passLabel);
        passPanel.add(passField);

        return passPanel;
    }

    private JPanel createSubmitButtonPanel() {
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setOpaque(false);
        submitPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Play", Font.BOLD, 20));
        submitButton.setBackground(Color.decode("#1e8ab8"));
        submitButton.setForeground(Color.WHITE);

        submitButton.addActionListener(action -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if(username != "" && password != "") {
                String role = validateCredentials(username, password);

                if ("admin".equals(role)) {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Logged in as admin", "ADMIN",
                            JOptionPane.PLAIN_MESSAGE);
                } else if ("nonadmin".equals(role)) {
                    AppointmentScreen appointmentScreen = new AppointmentScreen();
                    appointmentScreen.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Invalid username or password", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(LoginScreen.this,
                        "Empty username or password field", "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        submitPanel.add(submitButton);
        getRootPane().setDefaultButton(submitButton);
        return submitPanel;
    }

    private String validateCredentials(String username, String password) {
        Database db = new Database();
        try {
            String hashedPassword = new HashPassword().hashPassword(password);
            ResultSet rs = db.executeQuery("SELECT IsAdmin, IsActive FROM UserLogin WHERE LoginName = ? AND Password = ?", username, hashedPassword);

            if (rs.next() && rs.getBoolean("IsActive")) {
                boolean isAdmin = rs.getBoolean("IsAdmin");
                db.disconnect();
                return isAdmin ? "admin" : "nonadmin";
            } else {
                db.disconnect();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

