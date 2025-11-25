package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuFrame extends JFrame {
    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private User currentUser;
    private Restaurant restaurant;
    private List<MenuItem> cart = new ArrayList<>();

    public MenuFrame(User user, Restaurant restaurant) {
        super("Menu - " + restaurant.getName());
        this.currentUser = user;
        this.restaurant = restaurant;

        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<MenuItem> items = menuItemDAO.getItemsByRestaurant(restaurant.getRestaurantId());

        String[] columns = {"ID","Name","Desc","Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (MenuItem m : items) {
            model.addRow(new Object[]{m.getItemId(), m.getItemName(), m.getDescription(), m.getPrice()});
        }

        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JButton addToCart = new JButton("Add to Cart");
        JButton viewCart = new JButton("View Cart");

        addToCart.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel >= 0) {
                int id = (int) model.getValueAt(sel, 0);
                String name = (String) model.getValueAt(sel, 1);
                String desc = (String) model.getValueAt(sel, 2);
                double price = Double.parseDouble(model.getValueAt(sel, 3).toString());
                MenuItem m = new MenuItem(id, restaurant.getRestaurantId(), name, desc, price);
                cart.add(m);
                JOptionPane.showMessageDialog(this, name + " added to cart.");
            } else JOptionPane.showMessageDialog(this, "Select an item.");
        });

        viewCart.addActionListener(e -> {
            new CartFrame(currentUser, restaurant, new ArrayList<>(cart)).setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(addToCart); bottom.add(viewCart);
        add(bottom, BorderLayout.SOUTH);
    }
}

