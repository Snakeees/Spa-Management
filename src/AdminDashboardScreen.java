import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PasswordAuthentication;

public class AdminDashboardScreen extends JFrame implements ActionListener {
    JButton reports;
    JButton therapists;
    JButton services;
    JButton accounts;
    JButton changePassword;
    JPanel content;
    int userId;
    public int getUserId() {
        return userId;
    }

    public AdminDashboardScreen(int userId)  {
        this.userId = userId;
        setTitle("Serenity SPA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode("#d8ebf3"));
        createHeaderPanel().setLocation(0,0);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        content= new Reports();
        content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
        getContentPane().add(content,BorderLayout.CENTER, 1);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
//        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setLayout(new GridLayout(1,4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);

        reports = new JButton("Reports");
        reports.setFont(new Font("Play", Font.BOLD, 20));
        reports.setBackground(new Color(53, 183, 234));
        reports.addActionListener(this);
        headerPanel.add(reports);
        therapists = new JButton("Therapists");
        therapists.setFont(new Font("Play", Font.BOLD, 20));
        therapists.setBackground(new Color(53, 183, 234));
        therapists.addActionListener(this);
        headerPanel.add(therapists);
        services = new JButton("Services");
        services.setFont(new Font("Play", Font.BOLD, 20));
        services.setBackground(new Color(53, 183, 234));
        services.addActionListener(this);
        headerPanel.add(services);
        accounts = new JButton("Accounts");
        accounts.setFont(new Font("Play", Font.BOLD, 20));
        accounts.setBackground(new Color(53, 183, 234));
        accounts.addActionListener(this);
        headerPanel.add(accounts);

        changePassword = new JButton("Change Password");
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
        if(e.getSource()==reports){
            System.out.println("call the reports");
            content.invalidate();
            getContentPane().remove(1);
            content=new Reports();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
        }
        if(e.getSource()==therapists){
            System.out.println("call the therapists");
            content.invalidate();
            getContentPane().remove(1);
            content=new TherapistsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
        }
        else if(e.getSource()==accounts){
            System.out.println("call the accounts");
            content.invalidate();
            getContentPane().remove(1);
            content=new AccountsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
//            repaint();
        }
        else if(e.getSource()==services){
            System.out.println("call the services");
            getContentPane().remove(1);
            setLayout(new BorderLayout());
            content=new ServicesPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
//            repaint();
        }
        else if(e.getSource()==changePassword){
            content.invalidate();
            getContentPane().remove(1);
            content=new ChangePassword(getUserId());
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            getContentPane().add(content,BorderLayout.CENTER, 1);
            validate();
        }
        else {

        }
    }
    public JPanel getContentPanel(String subPage){
        JPanel content=new JPanel();
        content.setBounds(10,10,MAXIMIZED_HORIZ,MAXIMIZED_VERT);



        return content;
    }

}
