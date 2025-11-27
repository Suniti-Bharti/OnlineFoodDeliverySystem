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
    private java.util.List<MenuItem> cart = new ArrayList<>();

    public MenuFrame(User user, Restaurant restaurant) {
        super(restaurant.getName() + " — Menu");
        this.currentUser = user;
        this.restaurant = restaurant;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIUtils.SURFACE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.SURFACE);
        root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(UIUtils.SURFACE);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            new RestaurantListFrame(currentUser).setVisible(true);
            dispose();
        });
        top.add(back, BorderLayout.WEST);

        JLabel title = new JLabel(restaurant.getName());
        title.setFont(UIUtils.HEADLINE);
        title.setForeground(UIUtils.PRIMARY_DARK);
        top.add(title, BorderLayout.CENTER);

        root.add(top, BorderLayout.NORTH);

        String[] cols = {"ID","Item","Description","Price"};
        DefaultTableModel model = new DefaultTableModel(cols,0){
            public boolean isCellEditable(int r,int c){ return false;}
        };
        JTable table = new JTable(model);
        table.setRowHeight(36);
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(UIUtils.SURFACE);

        java.util.List<MenuItem> items = menuItemDAO.getByRestaurantId(restaurant.getRestaurantId());
        for (MenuItem m : items) {
            model.addRow(new Object[]{m.getItemId(), m.getItemName(), m.getDescription(), m.getPrice()});
        }

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addToCart = UIUtils.createPrimaryButton("Add to cart");
        addToCart.addActionListener(e -> {
            int rsel = table.getSelectedRow();
            if (rsel >= 0) {
                int id = (int) model.getValueAt(rsel,0);
                for (MenuItem mi : items) {
                    if (mi.getItemId() == id) {
                        cart.add(mi);
                        JOptionPane.showMessageDialog(this, "Added to cart: " + mi.getItemName());
                        break;
                    }
                }
            } else JOptionPane.showMessageDialog(this, "Select an item.");
        });

        JButton viewCart = new JButton("View cart");
        viewCart.addActionListener(e -> {
            new CartFrame(currentUser, restaurant, new ArrayList<>(cart)).setVisible(true);
            dispose();
        });

        bottom.add(addToCart); bottom.add(viewCart);

        root.add(sp, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        getContentPane().add(root);
    }
}