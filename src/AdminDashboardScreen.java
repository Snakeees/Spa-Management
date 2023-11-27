import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminDashboardScreen extends JFrame implements ActionListener {
    JButton reports;
    JButton therapists;
    JButton services;
    JButton accounts;
    JButton changePassword;
    JPanel content;
    ArrayList<JButton> navButtons;
    int userId;
    public int getUserId() {
        return userId;
    }

    public AdminDashboardScreen(int userId,String userName)  {
        this.userId = userId;
        setTitle("Serenity SPA: "+userName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.decode("#d8ebf3"));
        createHeaderPanel().setLocation(0,0);
        setSize(500, 400);
        getContentPane().add(createHeaderPanel(), BorderLayout.NORTH, 0);
        content= new Reports();
        makeActive(reports);
        content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
        JScrollPane body=new JScrollPane();
        body.setViewportView(content);
        getContentPane().add(body,BorderLayout.CENTER, 1);
    }
    public void makeActive(JButton button){
        button.setFont(new Font("Play", Font.BOLD, 25));
    }
    public void makeInActive(JButton button){

        button.setFont(new Font("Play", Font.PLAIN, 20));
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1,4));
        headerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.setOpaque(false);
        navButtons=new ArrayList<>();
        if(reports==null) {
            reports = getButton("Reports");
        }
        if(therapists==null) {
            therapists = getButton("Therapist");
        }
        if(services==null) {
            services = getButton("Service");
        }
        if(accounts==null) {
            accounts = getButton("Account");
        }
        if(changePassword==null) {
            changePassword = getButton("Reset Password");
        }
        headerPanel.add(reports);
        headerPanel.add(therapists);
        headerPanel.add(services);
        headerPanel.add(accounts);
        headerPanel.add(changePassword);

        navButtons.add(reports);
        navButtons.add(therapists);
        navButtons.add(services);
        navButtons.add(accounts);
        navButtons.add(changePassword);

        headerPanel.setLocation(0,0);
        headerPanel.setSize(MAXIMIZED_HORIZ,40);
        headerPanel.setBackground(new Color(53, 183, 234));

        return headerPanel;
    }
    public JButton getButton(String label){
        JButton button;
        button = new JButton(label);
        button.setFont(new Font("Play", Font.PLAIN, 20));
        button.setBackground(new Color(53, 183, 234));
        button.addActionListener(this);
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JScrollPane body=new JScrollPane();
        if(e.getSource()==reports){
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content=new Reports();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body,BorderLayout.CENTER, 1);
            makeActive(reports);
        }
        else if(e.getSource()==therapists){
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content=new TherapistsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body,BorderLayout.CENTER, 1);
            makeActive(therapists);
        }
        else if(e.getSource()==accounts){
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content=new AccountsPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body,BorderLayout.CENTER, 1);
            makeActive(accounts);
        }
        else if(e.getSource()==services){
            updateOtherButtons();
            getContentPane().remove(1);
            setLayout(new BorderLayout());
            content=new ServicesPanel();
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body,BorderLayout.CENTER, 1);
            makeActive(services);
        }
        else if(e.getSource()==changePassword){
            updateOtherButtons();
            content.invalidate();
            getContentPane().remove(1);
            content=new ChangePassword(getUserId());
            content.setSize(JFrame.MAXIMIZED_HORIZ,JFrame.MAXIMIZED_VERT);
            getContentPane().add(createHeaderPanel(), BorderLayout.PAGE_START, 0);
            body.setViewportView(content);
            getContentPane().add(body,BorderLayout.CENTER, 1);
            makeActive(changePassword);
        }
        validate();
    }

    private void updateOtherButtons() {
        for(JButton button:navButtons){
            makeInActive(button);
        }
    }

    public JPanel getContentPanel(String subPage){
        JPanel content=new JPanel();
        content.setBounds(10,10,MAXIMIZED_HORIZ,MAXIMIZED_VERT);
        return content;
    }

}
