package com.spa.screens;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonItems extends JPanel {
    private final List<JButton> buttons = new ArrayList<>();

    protected ButtonItems(List<String> items) {
        super();
        setOpaque(true);
        for (String str : items) {
            JButton b = new JButton(str.toUpperCase());
            b.setBackground(new Color(53, 183, 234));
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            buttons.add(b);
            add(b);
        }
    }

    protected List<JButton> getButtons() {
        return buttons;
    }
}