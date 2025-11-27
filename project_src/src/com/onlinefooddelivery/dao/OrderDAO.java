package com.onlinefooddelivery.dao;

import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.utils.DBConnection;

import java.sql.*;
import java.util.List;

public class OrderDAO {

    public int createOrder(Order order, List<OrderItem> items) {
        String insertOrderSQL = "INSERT INTO orders (user_id, restaurant_id, status, total_amount) VALUES (?, ?, ?, ?)";
        String insertItemSQL = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psItem = null;
        ResultSet rsKey = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            psOrder = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getUserId());
            psOrder.setInt(2, order.getRestaurantId());
            psOrder.setString(3, order.getStatus());
            psOrder.setDouble(4, order.getTotalAmount());
            psOrder.executeUpdate();

            rsKey = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (rsKey.next()) orderId = rsKey.getInt(1);

            psItem = conn.prepareStatement(insertItemSQL);
            for (OrderItem it : items) {
                psItem.setInt(1, orderId);
                psItem.setInt(2, it.getItemId());
                psItem.setInt(3, it.getQuantity());
                psItem.setDouble(4, it.getPrice());
                psItem.addBatch();
            }
            psItem.executeBatch();

            conn.commit();
            return orderId;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
        } finally {
            try { if (rsKey != null) rsKey.close(); } catch (Exception ignored) {}
            try { if (psItem != null) psItem.close(); } catch (Exception ignored) {}
            try { if (psOrder != null) psOrder.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
        return -1;
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
