//package com.onlinefooddelivery.ui;
//
//import com.onlinefooddelivery.dao.RestaurantDAO;
//import com.onlinefooddelivery.model.Restaurant;
//import com.onlinefooddelivery.model.User;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
//public class RestaurantListFrame extends JFrame {
//    private RestaurantDAO restaurantDAO = new RestaurantDAO();
//    private User currentUser;
//
//    public RestaurantListFrame(User user) {
//        super("Restaurants - Food Delivery");
//        this.currentUser = user;
//        setSize(600, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Fetch restaurants (quick synchronous fetch; if heavy use SwingWorker)
//        List<Restaurant> list = restaurantDAO.getAllRestaurants();
//
//        System.out.println("DEBUG: restaurants loaded = " + (list == null ? "null" : list.size()));
//
//        if (list == null || list.isEmpty()) {
//            // show a message if there are no restaurants
//            JLabel empty = new JLabel("No restaurants available. Please check database.", SwingConstants.CENTER);
//            empty.setFont(empty.getFont().deriveFont(Font.PLAIN, 16f));
//            add(empty, BorderLayout.CENTER);
//
//        } else {
//            DefaultListModel<Restaurant> model = new DefaultListModel<>();
//            for (Restaurant r : list) model.addElement(r);
//
//            JList<Restaurant> jList = new JList<>(model);
//            // Proper cell renderer that respects selection colors
//            jList.setCellRenderer(new ListCellRenderer<Restaurant>() {
//                private final JPanel panel = new JPanel(new BorderLayout());
//                private final JLabel label = new JLabel();
//                {
//                    panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
//                    panel.add(label, BorderLayout.CENTER);
//                }
//
//                @Override
//                public Component getListCellRendererComponent(JList<? extends Restaurant> list,
//                                                              Restaurant value, int index,
//                                                              boolean isSelected, boolean cellHasFocus) {
//                    String text = (value == null) ? "Unknown" :
//                            value.getName() + " - " + value.getLocation() + " (" + value.getRating() + ")";
//                    label.setText(text);
//
//                    if (isSelected) {
//                        panel.setBackground(list.getSelectionBackground());
//                        label.setForeground(list.getSelectionForeground());
//                    } else {
//                        panel.setBackground(list.getBackground());
//                        label.setForeground(list.getForeground());
//                    }
//                    return panel;
//                }
//            });
//
//            JScrollPane sp = new JScrollPane(jList);
//            add(sp, BorderLayout.CENTER);
//
//            JButton openMenuBtn = new JButton("Open Menu");
//            openMenuBtn.addActionListener(e -> {
//                Restaurant selected = jList.getSelectedValue();
//                if (selected != null) {
//                    new MenuFrame(currentUser, selected).setVisible(true);
//                    dispose();
//                } else JOptionPane.showMessageDialog(this, "Select a restaurant");
//            });
//            add(openMenuBtn, BorderLayout.SOUTH);
//        }
//    }
//}

package com.onlinefooddelivery.ui;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantListFrame
 * - Load restaurants from DB and show them in a JList
 * - If DB returns nothing or an error occurs, show 3 example restaurant buttons (fallback)
 */
public class RestaurantListFrame extends JFrame {
    private RestaurantDAO restaurantDAO = new RestaurantDAO();
    private User currentUser;

    public RestaurantListFrame(User user) {
        super("Restaurants - Food Delivery");
        this.currentUser = user;
        setSize(640, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Available Restaurants", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Attempt to load from DB
        List<Restaurant> list = null;
        try {
            list = restaurantDAO.getAllRestaurants();
            System.out.println("DEBUG: RestaurantDAO returned size = " + (list == null ? "null" : list.size()));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("DEBUG: RestaurantDAO threw exception: " + ex.getMessage());
        }

        // If list is null or empty -> fallback to example buttons
        if (list == null || list.isEmpty()) {
            // Create fallback sample restaurants so UI is still usable
            List<Restaurant> fallback = createSampleRestaurants();
            add(createFallbackPanel(fallback), BorderLayout.CENTER);
        } else {
            add(createListPanel(list), BorderLayout.CENTER);
        }

        // Bottom: back / exit or other controls if needed
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            // simple logout: show login and dispose
            new LoginFrame().setVisible(true);
            dispose();
        });
        bottom.add(logout);
        add(bottom, BorderLayout.SOUTH);
    }

    // Panel that contains JList of restaurants (from DB)
    private Component createListPanel(List<Restaurant> restaurants) {
        DefaultListModel<Restaurant> model = new DefaultListModel<>();
        for (Restaurant r : restaurants) model.addElement(r);

        JList<Restaurant> jList = new JList<>(model);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setVisibleRowCount(8);

        jList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lbl = new JLabel(value.getName() + " - " + value.getLocation() + " (" + value.getRating() + ")");
            lbl.setOpaque(true);
            if (isSelected) {
                lbl.setBackground(list.getSelectionBackground());
                lbl.setForeground(list.getSelectionForeground());
            } else {
                lbl.setBackground(list.getBackground());
                lbl.setForeground(list.getForeground());
            }
            lbl.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            return lbl;
        });

        JScrollPane sp = new JScrollPane(jList);

        // Right side: open menu button
        JPanel right = new JPanel(new BorderLayout());
        JButton openMenuBtn = new JButton("Open Menu");
        openMenuBtn.addActionListener(e -> {
            Restaurant selected = jList.getSelectedValue();
            if (selected != null) {
                openMenuFor(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Select a restaurant first.");
            }
        });
        right.add(openMenuBtn, BorderLayout.NORTH);

        JPanel container = new JPanel(new BorderLayout());
        container.add(sp, BorderLayout.CENTER);
        container.add(right, BorderLayout.EAST);
        return container;
    }

    // Panel for fallback sample restaurant buttons
    private Component createFallbackPanel(List<Restaurant> sampleRestaurants) {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel info = new JLabel("<html><center>No restaurants were found in the database.<br>"
                + "Using example restaurants so you can continue testing.</center></html>", SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));
        info.setFont(info.getFont().deriveFont(Font.PLAIN, 14f));
        gbc.gridy = 0;
        p.add(info, gbc);

        int i = 1;
        for (Restaurant r : sampleRestaurants) {
            JButton btn = new JButton(r.getName() + " — " + r.getLocation());
            btn.setPreferredSize(new Dimension(420, 40));
            btn.addActionListener(e -> openMenuFor(r));
            gbc.gridy = i++;
            p.add(btn, gbc);
        }

        return p;
    }

    // Opens MenuFrame with the given restaurant and current user
    private void openMenuFor(Restaurant r) {
        // sanity check
        if (r == null) {
            JOptionPane.showMessageDialog(this, "Invalid restaurant selected.");
            return;
        }
        System.out.println("DEBUG: Opening menu for restaurant id=" + r.getRestaurantId() + " name=" + r.getName());
        new MenuFrame(currentUser, r).setVisible(true);
        dispose();
    }

    // sample fallback restaurants
    private List<Restaurant> createSampleRestaurants() {
        List<Restaurant> s = new ArrayList<>();
        Restaurant a = new Restaurant();
        a.setRestaurantId(-1);
        a.setName("Burger Tower (Sample)");
        a.setLocation("Patna");
        a.setRating(4.5);

        Restaurant b = new Restaurant();
        b.setRestaurantId(-2);
        b.setName("Pizza Magic (Sample)");
        b.setLocation("Gaya");
        b.setRating(4.2);

        Restaurant c = new Restaurant();
        c.setRestaurantId(-3);
        c.setName("Noodle House (Sample)");
        c.setLocation("Patna");
        c.setRating(4.0);

        s.add(a);
        s.add(b);
        s.add(c);
        return s;
    }
}

