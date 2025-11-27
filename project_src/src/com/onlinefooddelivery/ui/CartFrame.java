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
import java.util.List;
import java.util.ArrayList;

public class CartFrame extends JFrame {
    private OrderDAO orderDAO = new OrderDAO();
    private User currentUser;
    private Restaurant restaurant;
    private List<MenuItem> cart;

    public CartFrame(User user, Restaurant restaurant, List<MenuItem> cart) {
        super("Cart — " + restaurant.getName());
        this.currentUser = user; this.restaurant = restaurant; this.cart = cart;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700,500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UIUtils.SURFACE);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new MenuFrame(currentUser, restaurant).setVisible(true);
            dispose();
        });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Your cart");
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);
        top.add(title, BorderLayout.CENTER);
        root.add(top, BorderLayout.NORTH);

        String[] cols = {"Item","Price"};
        DefaultTableModel model = new DefaultTableModel(cols,0){
            public boolean isCellEditable(int r,int c){ return false;}
        };
        JTable table = new JTable(model);
        for (MenuItem m : cart) {
            model.addRow(new Object[]{m.getItemName(), m.getPrice()});
        }
        JScrollPane sp = new JScrollPane(table);
        root.add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton place = UIUtils.createPrimaryButton("Place order");
        place.addActionListener(e -> placeOrder());

        bottom.add(place);
        root.add(bottom, BorderLayout.SOUTH);

        getContentPane().add(root);
    }

    private void placeOrder() {
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (MenuItem m : cart) {
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