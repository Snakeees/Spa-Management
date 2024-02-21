package com.spa.dto;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.Color;



import static com.spa.SpaManagement.TEXTFIELD_BORDER_COLOR;

public class MyTextField extends JTextField {

    public MyTextField(int columns) {
        super(columns);
        setCustomBorder();
    }
    private void setCustomBorder() {
        this.setBorder(BorderFactory.createLineBorder(TEXTFIELD_BORDER_COLOR, 3));
    }
}
