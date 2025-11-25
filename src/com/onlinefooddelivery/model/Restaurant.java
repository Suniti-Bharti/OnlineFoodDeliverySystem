package com.onlinefooddelivery.model;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String location;
    private double rating;

    public Restaurant() {}

    public Restaurant(int restaurantId, String name, String location, double rating) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
        this.rating = rating;
    }

    // getters/setters
    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
}
