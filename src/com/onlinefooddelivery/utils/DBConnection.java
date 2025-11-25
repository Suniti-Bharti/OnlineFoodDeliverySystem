package com.onlinefooddelivery.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/fooddeliverydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root@12345678";

    private static Connection connection = null;

    public static Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Failed to connect to database:");
                e.printStackTrace();
            }
        }

        return connection;
    }
}
