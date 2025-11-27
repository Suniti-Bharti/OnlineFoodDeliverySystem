package com.onlinefooddelivery.ui;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    public static final Color PRIMARY = new Color(33, 150, 243);
    public static final Color PRIMARY_DARK = new Color(25, 118, 210);
    public static final Color SURFACE = new Color(250, 250, 250);
    public static final Font HEADLINE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);

    public static void applyMaterialTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("Button.font", BODY);
        UIManager.put("Label.font", BODY);
        UIManager.put("TextField.font", BODY);
        UIManager.put("PasswordField.font", BODY);
        UIManager.put("Table.font", BODY);
    }

    public static JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(BODY.deriveFont(Font.BOLD));
        b.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        b.setOpaque(true);
        return b;
    }
}