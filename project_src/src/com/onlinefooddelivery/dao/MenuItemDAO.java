package com.onlinefooddelivery.dao;

import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public List<MenuItem> getItemsByRestaurant(int restaurantId) {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE restaurant_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MenuItem m = new MenuItem(
                        rs.getInt("item_id"),
                        rs.getInt("restaurant_id"),
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                list.add(m);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public MenuItem getItemById(int id) {
        String sql = "SELECT * FROM menu_items WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MenuItem(rs.getInt("item_id"), rs.getInt("restaurant_id"),
                        rs.getString("item_name"), rs.getString("description"), rs.getDouble("price"));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }
}
