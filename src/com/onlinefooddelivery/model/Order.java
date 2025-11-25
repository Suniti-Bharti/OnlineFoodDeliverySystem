package com.onlinefooddelivery.model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private int restaurantId;
    private Timestamp orderDate;
    private String status;
    private double totalAmount;

    public Order() {}

    public Order(int orderId, int userId, int restaurantId, Timestamp orderDate, String status, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // getters/setters...
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }
    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
