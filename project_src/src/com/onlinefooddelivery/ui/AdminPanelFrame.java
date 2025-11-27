package com.onlinefooddelivery.ui;

import javax.swing.*;
import java.awt.*;

public class AdminPanelFrame extends JFrame {

    public AdminPanelFrame() {
        super("Admin Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);

        root.add(title, BorderLayout.NORTH);

        JTextArea ta = new JTextArea("Admin features: manage restaurants, menus, orders...");
        ta.setEditable(false);
        ta.setBackground(UIUtils.SURFACE);
        root.add(new JScrollPane(ta), BorderLayout.CENTER);

        getContentPane().add(root);
    }
}