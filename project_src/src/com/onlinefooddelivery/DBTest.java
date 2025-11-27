package com.onlinefooddelivery;

import com.onlinefooddelivery.utils.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
    }
}
