package com.spa.dto;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public MyButton() {
        setForeground(Color.WHITE);
        setBackground(new Color(145, 73, 116));
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize()));
    }

    public MyButton(String text) {
        setText(text);
        setForeground(Color.WHITE);
        setBackground(new Color(145, 73, 116));
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize()));
    }

    public MyButton(Icon icon) {
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(new Color(145, 73, 116));
        setFont(new Font("Play", Font.BOLD, this.getFont().getSize()));
    }


}
