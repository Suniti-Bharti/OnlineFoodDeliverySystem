package com.onlinefooddelivery.dao;

import com.onlinefooddelivery.model.User;
import com.onlinefooddelivery.utils.DBConnection;

import java.sql.*;

public class UserDAO {

    public boolean register(User user) {
        String sql = "INSERT INTO users (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getPassword());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    public User login(String email, String password) {
//        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, email);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                User u = new User();
//                u.setUserId(rs.getInt("user_id"));
//                u.setName(rs.getString("name"));
//                u.setEmail(rs.getString("email"));
//                u.setPhone(rs.getString("phone"));
//                u.setAddress(rs.getString("address"));
//                u.setPassword(rs.getString("password"));
//                return u;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//}

    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setPassword(rs.getString("password"));
                return u;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Login failed for email=" + email + " - SQL error: " + ex.getMessage());
        }
        return null;
    }

}

