import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class NonAdminDashboardScreen extends JFrame implements ActionListener {
    JButton appointments;
    JButton changePassword;
    JButton attendance;
    JPanel content;
    int userId;
    ArrayList<JButton> navButtons;

    public NonAdminDashboardScreen(int userId, String userName) {
        navButtons = new ArrayList<>();
        this.userId = userId;
        setTitle("Serenity SPA: " + userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode("#d8ebf3"));
        createHeaderPanel().setLocation(0, 0);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        JScrollPane body = new JScrollPane();
        content = new AppointmentsPanel();
        makeActive(appointments);
        content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
        body.setViewportView(content);
        getContentPane().add(body, BorderLayout.CENTER, 1);
    }

    public void makeActive(JButton button) {
        button.setFont(new Font("Play", Font.BOLD, 25));
    }

    public void makeInActive(JButton button) {
        button.setFont(new Font("Play", Font.PLAIN, 20));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        appointments = getButton("Appointments");
        headerPanel.add(appointments);
        attendance = getButton("Attendance");
        headerPanel.add(attendance);
        changePassword = getButton("Reset Password");
        headerPanel.add(changePassword);
        navButtons.add(appointments);
        navButtons.add(attendance);
        navButtons.add(changePassword);
        headerPanel.setLocation(0, 0);
        headerPanel.setSize(MAXIMIZED_HORIZ, 40);
        headerPanel.setBackground(new Color(53, 183, 234));
        return headerPanel;
    }

    public JButton getButton(String label) {
        JButton button;
        button = new JButton(label);
        button.setFont(new Font("Play", Font.PLAIN, 20));
        button.setBackground(new Color(53, 183, 234));
        button.addActionListener(this);
        return button;
    }

    private void updateOtherButtons() {
        for (JButton button : navButtons) {
            makeInActive(button);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane body = new JScrollPane();
        if (e.getSource() == appointments) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new AppointmentsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(appointments);
        }
        else if (e.getSource() == attendance) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new TherapistsAttendance();
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(attendance);
        }
        else if (e.getSource() == changePassword) {
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content = new ChangePassword(userId);
            content.setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body, BorderLayout.CENTER, 1);
            makeActive(changePassword);
        }
        validate();
    }
}
