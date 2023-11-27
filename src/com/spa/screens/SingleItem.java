package com.spa.screens;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SingleItem extends JPanel {
    private final List<JButton> buttons = new ArrayList<>();

    protected SingleItem(List<String> items) {
        super();
        setOpaque(true);
        for (String str : items) {
            JButton b = new JButton(str.toUpperCase());
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