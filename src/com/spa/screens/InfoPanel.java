package com.spa.screens;

import com.spa.dto.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.spa.SpaManagement.BACKGROUND_COLOR;
import static com.spa.SpaManagement.SELECTED_BUTTON_COLOR;

public class InfoPanel extends JPanel {

    private JLabel TitleLabel;
    private Boolean ShowBackButton, ShowSubmitButton;
    public Object[][] Items;
    private InfoFunction BackAction, SubmitAction;
    private String SubmitButtonText;
    private MyButton SubmitButton, BackButton;


    public InfoPanel(JLabel titleLabel, Boolean showBackButton, Boolean showSubmitButton, String submitButtonText, Object[][] items, InfoFunction backAction, InfoFunction submitAction) {
        this.TitleLabel = titleLabel;
        this.ShowBackButton = showBackButton;
        this.ShowSubmitButton = showSubmitButton;
        this.SubmitButtonText = submitButtonText;
        this.Items = items;
        this.BackAction = backAction;
        this.SubmitAction = submitAction;

        initComponents();
        setBackground(BACKGROUND_COLOR);
        UIManager.put("Button.select", SELECTED_BUTTON_COLOR);
    }

    private void initComponents() {
        setupButtons();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(-100, 100, -70, 100); // Set padding around components
        // BACK button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span two columns
        gbc.fill = GridBagConstraints.NONE; // Do not stretch the component
        if(ShowBackButton) {
            gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor components to the northwest (top-left)
            add(BackButton, gbc);
        }
        gbc.anchor = GridBagConstraints.NORTH; // Center components
        add(TitleLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch components horizontally
        gbc.gridwidth = 1; // Set grid width back to 1 for labels
        gbc.weightx = 0; // Set weightx back to 0 for labels
        gbc.insets = new Insets(0, 100, 10, 100); // Set padding around components

        for(Object[] InputPair : Items) {
            addLabelAndComponent((JLabel) InputPair[0], (JComponent) InputPair[1], gbc);
        }

        if (ShowSubmitButton) {
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(30, 100, 0, 100); // Set padding around components
            add(SubmitButton, gbc);
        }
    }

    private void setupButtons() {
        BackButton = createButton("BACK", e -> BackAction.call(this));
        SubmitButton = createButton(SubmitButtonText, e -> SubmitAction.call(this));
    }

    private MyButton createButton(String text, ActionListener listener) {
        MyButton button = new MyButton(text);
        button.addActionListener(listener);
        return button;
    }

    private void addLabelAndComponent(JLabel label, JComponent comp, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(label, gbc);
        gbc.gridx = 1;
        add(comp, gbc);
    }

    public static JLabel createLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Play", Font.BOLD, size));
        return label;
    }

}
