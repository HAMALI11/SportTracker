package com.sportflow.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sportflow";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Update with your actual password

    private static Connection connection;

    private DatabaseConnection() {
        // Private constructor for Singleton
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Register MySQL Driver Explicitly if needed (for older versions)
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection to MySQL established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("MySQL connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
