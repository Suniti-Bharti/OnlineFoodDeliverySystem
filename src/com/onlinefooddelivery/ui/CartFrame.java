package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CartFrame extends JFrame {

    private User currentUser;
    private Restaurant restaurant;
    private List<MenuItem> cartItems;
    private OrderDAO orderDAO = new OrderDAO();

    public CartFrame(User user, Restaurant restaurant, List<MenuItem> cart) {
        super("Your Cart");
        this.currentUser = user;
        this.restaurant = restaurant;
        this.cartItems = cart;

        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Item","Price","Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Convert to aggregated rows
        cart.forEach(m -> model.addRow(new Object[]{m.getItemName(), m.getPrice(), 1}));

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton placeOrder = new JButton("Place Order");
        placeOrder.addActionListener(e -> placeOrder());

        add(placeOrder, BorderLayout.SOUTH);
    }

    private void placeOrder() {
        if (cartItems == null || cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        // compute total and prepare order items
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (MenuItem m : cartItems) {
            total += m.getPrice();
            OrderItem oi = new OrderItem();
            oi.setItemId(m.getItemId());
            oi.setQuantity(1);
            oi.setPrice(m.getPrice());
            orderItems.add(oi);
        }

        Order order = new Order();
        order.setUserId(currentUser.getUserId());
        order.setRestaurantId(restaurant.getRestaurantId());
        order.setStatus("PLACED");
        order.setTotalAmount(total);

        int orderId = orderDAO.createOrder(order, orderItems);
        if (orderId > 0) {
            JOptionPane.showMessageDialog(this, "Order placed. Order ID: " + orderId);
            new OrderTrackingFrame(orderId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order.");
        }
    }
}
