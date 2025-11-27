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
        super("Restaurants — Food Delivery");
        this.currentUser = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel header = new JLabel("Nearby restaurants");
        header.setFont(UIUtils.HEADLINE);
        header.setForeground(UIUtils.PRIMARY_DARK);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UIUtils.SURFACE);
        top.add(header, BorderLayout.WEST);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        top.add(logout, BorderLayout.EAST);

        root.add(top, BorderLayout.NORTH);

        JPanel cards = new JPanel();
        cards.setBackground(UIUtils.SURFACE);
        cards.setLayout(new GridLayout(0,3,12,12));
        JScrollPane sp = new JScrollPane(cards);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIUtils.SURFACE);
        root.add(sp, BorderLayout.CENTER);

        List<Restaurant> list = restaurantDAO.getAllRestaurants();
        if (list == null || list.isEmpty()) {
            JLabel note = new JLabel("No restaurants available.");
            note.setForeground(Color.GRAY);
            note.setFont(UIUtils.BODY);
            root.add(note, BorderLayout.CENTER);
        } else {
            for (Restaurant r : list) {
                JPanel card = createCard(r);
                cards.add(card);
            }
        }

        getContentPane().add(root);
    }

    private JPanel createCard(Restaurant r) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        JLabel name = new JLabel(r.getName());
        name.setFont(UIUtils.BODY.deriveFont(Font.BOLD));
        JLabel loc = new JLabel(r.getLocation() + " • Rating: " + r.getRating());
        loc.setFont(UIUtils.BODY);

        JButton open = UIUtils.createPrimaryButton("Open menu");
        open.addActionListener(e -> {
            new MenuFrame(currentUser, r).setVisible(true);
            dispose();
        });

        p.add(name, BorderLayout.NORTH);
        p.add(loc, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setBackground(Color.WHITE);
        south.add(open);
        p.add(south, BorderLayout.SOUTH);

        return p;
    }
}