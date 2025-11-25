package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.OrderDAO;

import javax.swing.*;
import java.awt.*;

public class OrderTrackingFrame extends JFrame {

    private JLabel statusLabel = new JLabel("PLACED");
    private int orderId;
    private OrderDAO orderDAO = new OrderDAO();

    public OrderTrackingFrame(int orderId) {
        super("Order Tracking - #" + orderId);
        this.orderId = orderId;
        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(statusLabel, BorderLayout.CENTER);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));

        simulateStatus();
    }

    private void simulateStatus() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                updateStatus("PREPARING");
                Thread.sleep(3000);
                updateStatus("OUT FOR DELIVERY");
                Thread.sleep(3000);
                updateStatus("DELIVERED");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> statusLabel.setText(status));
        orderDAO.updateOrderStatus(orderId, status);
    }
}
