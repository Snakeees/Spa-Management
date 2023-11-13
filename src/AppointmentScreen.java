import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class AppointmentScreen extends JFrame {

    public AppointmentScreen() {
        setTitle("Appointments");
        setSize(940, 529); // Adjusted to match the second image size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(60, 63, 65)); // Dark background

        // Main panel with BoxLayout for vertical alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(60, 63, 65)); // Dark background for the main panel

        // Navigation Bar
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(76, 81, 86)); // Darker grey for the navbar
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.X_AXIS));

        String[] navItems = {"APPOINTMENTS", "RESET PASSWORD", "ATTENDANCE"};
        for (String item : navItems) {
            JButton button = new JButton(item);
            button.setForeground(Color.WHITE); // Text color
            button.setBackground(new Color(76, 81, 86)); // Button color to match navBar
            button.setFocusPainted(false);
            navBar.add(button);
        }

        JLabel userIcon = new JLabel(new ImageIcon("./images/user-icon.png"));
        userIcon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        navBar.add(Box.createHorizontalGlue());
        navBar.add(userIcon);

        // Header Section
        JPanel header = new JPanel();
        header.setBackground(new Color(60, 63, 65));
        header.setLayout(new FlowLayout(FlowLayout.LEADING));

        JLabel lblHeading = new JLabel("TODAY'S APPOINTMENT LIST");
        lblHeading.setForeground(Color.WHITE); // Heading text color
        lblHeading.setFont(new Font("Sans-serif", Font.BOLD, 24));
        header.add(lblHeading);

        JTextField searchField = new JTextField(20);
        header.add(searchField);

        JButton addButton = new JButton(new ImageIcon("./images/plus-icon.png"));
        addButton.setBackground(null);
        addButton.setBorder(null);
        header.add(addButton);

        // Table Section
        String[] columnNames = {"TIME", "SERVICE", "THERAPIST", "ACTIONS"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        populateTableWithAppointments(model);
        // Add rows as needed here
        // model.addRow(new Object[]{"Time", "Service", "Therapist", actionPanel});

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(169, 183, 198)); // Light grey for table background
        table.setForeground(Color.BLACK); // Text color
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(navBar);
        mainPanel.add(header);
        mainPanel.add(scrollPane);

        // Adjust column widths and add custom cell renderer for buttons
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        // Set custom cell renderers if needed
        // columnModel.getColumn(3).setCellRenderer(new ActionButtonRenderer());

        // Add the main panel to the frame
        add(mainPanel);
    }

    private JPanel getActionPanel() {
        // Create a panel to hold the buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
        actionPanel.setBackground(new Color(169, 183, 198)); // Light grey background to match the table

        // Edit button
        JButton editButton = new JButton(new ImageIcon("./images/edit-icon.png"));
        editButton.setBorder(BorderFactory.createEmptyBorder());
        editButton.setContentAreaFilled(false);
        actionPanel.add(editButton);

        // Delete button
        JButton deleteButton = new JButton(new ImageIcon("./images/delete-icon.png"));
        deleteButton.setBorder(BorderFactory.createEmptyBorder());
        deleteButton.setContentAreaFilled(false);
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    // Example of a custom cell renderer class if needed
    /*
    class ActionButtonRenderer extends JButton implements TableCellRenderer {
        public ActionButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    */

    public void populateTableWithAppointments(DefaultTableModel model) {
        Database db = new Database();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // For formatting AppointmentTime

        try {
            String query = "SELECT Appointment.AppointmentTime, Service.ServiceName, " +
                    "Therapist.FirstName, Appointment.ID " +
                    "FROM Appointment " +
                    "JOIN Service ON Appointment.ServiceID = Service.ID " +
                    "JOIN Therapist ON Appointment.TherapistID = Therapist.ID";

            ResultSet rs = db.executeQuery(query);

            while (rs.next()) {
                // Read the values from the ResultSet
                String time = timeFormat.format(rs.getTime("AppointmentTime"));
                String serviceName = rs.getString("ServiceName");
                String therapistName = rs.getString("FirstName");
                int appointmentId = rs.getInt("ID"); // Assuming you want to use this as an identifier for actions

                // Add a row to the table model
                model.addRow(new Object[]{time, serviceName, therapistName, "null"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
