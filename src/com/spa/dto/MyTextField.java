package com.spa.dto;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.Color;

public class MyTextField extends JTextField {

    public MyTextField(int columns) {
        super(columns);
        setCustomBorder();
    }
    public MyTextField() {
        super();
        setCustomBorder();
    }
    private void setCustomBorder() {
        this.setBorder(BorderFactory.createLineBorder(new Color(255, 89, 149), 3));
    }
}
