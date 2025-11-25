package com.onlinefooddelivery.dao;

import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> list = new ArrayList<>();
        System.out.println("DEBUG: RestaurantDAO returned size=" + list.size());
        String sql = "SELECT * FROM restaurants";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Restaurant r = new Restaurant(
                        rs.getInt("restaurant_id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getDouble("rating")
                );
                list.add(r);

                System.out.println("Loaded restaurant: " + r.getName() + " id=" + r.getRestaurantId());

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Restaurant getById(int id) {
        String sql = "SELECT * FROM restaurants WHERE restaurant_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Restaurant(rs.getInt("restaurant_id"), rs.getString("name"),
                        rs.getString("location"), rs.getDouble("rating"));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }
}
