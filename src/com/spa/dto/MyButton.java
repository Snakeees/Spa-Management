package com.spa.dto;

import javax.swing.*;
import java.awt.*;

import static com.spa.SpaManagement.BUTTON_COLOR;

public class MyButton extends JButton {

    public MyButton() {
        setForeground(Color.WHITE);
        setBackground(BUTTON_COLOR);
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize() + 1));
        setFocusPainted(false);

    }

    public MyButton(String text) {
        setText(text);
        setForeground(Color.WHITE);
        setBackground(BUTTON_COLOR);
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize() + 1));
        setFocusPainted(false);
    }

    public MyButton(Icon icon) {
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(BUTTON_COLOR);
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize() + 1));
        setFocusPainted(false);
    }


}
