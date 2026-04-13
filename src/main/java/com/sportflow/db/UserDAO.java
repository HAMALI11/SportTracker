package com.sportflow.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for users: login + register
 */
public class UserDAO {

    /** Returns user_id if credentials are valid, -1 otherwise */
    public static int authenticate(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.err.println("Erreur authenticate: " + e.getMessage());
        }
        return -1;
    }

    /** Returns true if registration succeeded */
    public static boolean register(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur register: " + e.getMessage());
            return false;
        }
    }

    /** Returns true if username already exists */
    public static boolean usernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erreur usernameExists: " + e.getMessage());
        }
        return false;
    }

    /** Returns all users for admin view */
    public static List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();
        String sql = "SELECT id, username, email, created_at FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new String[]{
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("created_at")
                });
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAllUsers: " + e.getMessage());
        }
        return users;
    }
}
