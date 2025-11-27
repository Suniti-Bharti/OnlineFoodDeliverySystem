package com.onlinefooddelivery;

import com.onlinefooddelivery.utils.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection successful!");
            } else {
                System.out.println("❌ Connection failed!");
            }

        } catch (Exception e) {
            System.out.println("❌ Error connecting to database:");
            e.printStackTrace();
        }
    }
}
