package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.OrderDAO;

import javax.swing.*;
import java.awt.*;

public class OrderTrackingFrame extends JFrame {
    private OrderDAO orderDAO = new OrderDAO();

    public OrderTrackingFrame(int orderId) {
        super("Order tracking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480,300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UIUtils.SURFACE);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new com.onlinefooddelivery.ui.RestaurantListFrame(null).setVisible(true);
            dispose();
        });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Tracking order #" + orderId);
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);
        top.add(title, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        String status = orderDAO.getStatus(orderId);
        JLabel statusLabel = new JLabel(status == null ? "Unknown" : status);
        statusLabel.setFont(UIUtils.BODY);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.add(statusLabel, BorderLayout.CENTER);

        getContentPane().add(root);
    }
}