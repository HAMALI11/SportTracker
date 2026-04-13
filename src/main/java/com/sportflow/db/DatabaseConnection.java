package com.sportflow.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pattern — one connection shared across the application.
 */
public class DatabaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/sportflow?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "";  // XAMPP default has no password

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion MySQL établie.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL introuvable: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion MySQL: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Connexion MySQL fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur fermeture: " + e.getMessage());
        }
    }
}
