package com.onlinefooddelivery.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection helper. Ensure MySQL Connector/J is on classpath.
 * Update URL/USERNAME/PASSWORD for your environment.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fooddeliverydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root@12345678";

    private static Connection connection = null;

    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
}