import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PasswordAuthentication;

public class NonAdminDashboardScreen extends JFrame implements ActionListener {
    JButton appointments;
    JButton changePassword;
    JButton attendance;
    JPanel content;
    int userId;
    public int getUserId() {
        return userId;
    }

    public NonAdminDashboardScreen(int userId)  {
        this.userId = userId;
        setTitle("Serenity SPA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode("#d8ebf3"));
        createHeaderPanel().setLocation(0,0);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        content= new AppointmentsPanel();
        content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
        getContentPane().add(content,BorderLayout.CENTER, 1);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
//        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setLayout(new GridLayout(1,4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        appointments = new JButton("Appointments");
        appointments.setFont(new Font("Play", Font.BOLD, 20));
        appointments.setBackground(new Color(53, 183, 234));
        appointments.addActionListener(this);
        headerPanel.add(appointments);
        attendance = new JButton("Attendance");
        attendance.setFont(new Font("Play", Font.BOLD, 20));
        attendance.setBackground(new Color(53, 183, 234));
        attendance.addActionListener(this);
        headerPanel.add(attendance);
        changePassword = new JButton("Reset Password");
        changePassword.setFont(new Font("Play", Font.BOLD, 20));
        changePassword.setBackground(new Color(53, 183, 234));
        changePassword.addActionListener(this);
        headerPanel.add(changePassword);
        headerPanel.setLocation(0,0);
        headerPanel.setSize(MAXIMIZED_HORIZ,40);
        headerPanel.setBackground(new Color(53, 183, 234));
        return headerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==appointments){
            System.out.println("call the appoints");
            content.invalidate();
            getContentPane().remove(1);
            content=new AppointmentsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
        }
        if(e.getSource()==attendance){
            System.out.println("call the attendance");
            content.invalidate();
            getContentPane().remove(1);
            content=new TherapistsAttendance();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
        }
        else if(e.getSource()==changePassword){
            System.out.println("call the change password");
            content.invalidate();
            getContentPane().remove(1);
            content=new ChangePassword(userId);
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
//            repaint();
        }

    }
    public JPanel getContentPanel(String subPage){
        JPanel content=new JPanel();
        content.setBounds(10,10,MAXIMIZED_HORIZ,MAXIMIZED_VERT);



        return content;
    }

}
