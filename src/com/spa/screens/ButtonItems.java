package com.spa.screens;

import com.spa.dto.MyButton;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonItems extends JPanel {
    private final List<MyButton> buttons = new ArrayList<>();

    protected ButtonItems(List<String> items) {
        super();
        setOpaque(true);
        for (String str : items) {
            MyButton b = new MyButton(str.toUpperCase());
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            buttons.add(b);
            add(b);
        }
    }

    protected List<MyButton> getButtons() {
        return buttons;
    }
}