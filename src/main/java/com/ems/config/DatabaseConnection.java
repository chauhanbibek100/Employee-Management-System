package com.ems.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Bibek@2005"; // Update this locally if your MySQL password is empty or different

    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the JDBC Driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
