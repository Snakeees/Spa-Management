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
        // Adding the Label
        JLabel welcomeLabel = new JLabel("Welcome to My App");
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        labelPanel.add(welcomeLabel);
        boxPanel.add(labelPanel);

        // Adding the textbox
        JTextArea textArea = new JTextArea("Nice to see you");
        textArea.setEditable(false);
        JPanel textAreaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textAreaPanel.setBorder(new EmptyBorder(10, 0, 30, 0));
        textAreaPanel.add(textArea);
        boxPanel.add(textAreaPanel);


        // Adding the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton continueButton = new JButton("Continue!");
        continueButton.addActionListener(action -> {
            // Logic when button is clicked
            LoginScreen loginScreen = new LoginScreen(null);
            loginScreen.setVisible(true);
            dispose();
        });
        buttonPanel.add(continueButton);
        boxPanel.add(buttonPanel);
        add(boxPanel);
    }
}
