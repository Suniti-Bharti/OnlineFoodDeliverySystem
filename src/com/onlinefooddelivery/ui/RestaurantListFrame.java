package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RestaurantListFrame extends JFrame {
    private RestaurantDAO restaurantDAO = new RestaurantDAO();
    private User currentUser;

    public RestaurantListFrame(User user) {
        super("Restaurants - Food Delivery");
        this.currentUser = user;
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Restaurant> list = restaurantDAO.getAllRestaurants();

        DefaultListModel<Restaurant> model = new DefaultListModel<>();
        for (Restaurant r : list) model.addElement(r);

        JList<Restaurant> jList = new JList<>(model);
        jList.setCellRenderer((list1, value, index, isSelected, cellHasFocus) ->
                new JLabel(value.getName() + " - " + value.getLocation() + " (" + value.getRating() + ")"));

        JScrollPane sp = new JScrollPane(jList);
        add(sp, BorderLayout.CENTER);

        JButton openMenuBtn = new JButton("Open Menu");
        openMenuBtn.addActionListener(e -> {
            Restaurant selected = jList.getSelectedValue();
            if (selected != null) {
                new MenuFrame(currentUser, selected).setVisible(true);
                dispose();
            } else JOptionPane.showMessageDialog(this, "Select a restaurant");
        });

        add(openMenuBtn, BorderLayout.SOUTH);
    }
}
