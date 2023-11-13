import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setSize(700, 400);
        setLocation(600, 200);
        setTitle("Welcome Screen");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));


        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        boxPanel.setBorder(new EmptyBorder(30, 10, 30, 10));
//        boxPanel.setBackground(Color.GREEN);

        // Adding the Label
        JLabel welcomeLabel = new JLabel("Welcome to My App");
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
//        labelPanel.setBackground(Color.PINK);
        labelPanel.add(welcomeLabel);
        boxPanel.add(labelPanel);

        // Adding the textbox
        JTextArea textArea = new JTextArea("Nice to see you");
        textArea.setEditable(false);
        JPanel textAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        textAreaPanel.setBackground(Color.ORANGE);
        textAreaPanel.setBorder(new EmptyBorder(10, 0, 30, 0));
        textAreaPanel.add(textArea);
        boxPanel.add(textAreaPanel);


        // Adding the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel.setBackground(Color.CYAN);
        JButton continueButton = new JButton("Continue!");
        continueButton.addActionListener(action -> {
            // Logic when button is clicked
            System.out.println("Continue Button Clicked");
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
            dispose();
        });
        buttonPanel.add(continueButton);
        boxPanel.add(buttonPanel);



        add(boxPanel);

    }

}
