package com.sportflow.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for user activities
 */
public class ActivityDAO {

    /** Save a new activity */
    public static boolean addActivity(int userId, String name, int duration, int calories, double distance) {
        String sql = "INSERT INTO activities (user_id, name, duration_minutes, calories_burned, distance_km, activity_date) VALUES (?, ?, ?, ?, ?, CURDATE())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.setInt(3, duration);
            stmt.setInt(4, calories);
            stmt.setDouble(5, distance);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur addActivity: " + e.getMessage());
            return false;
        }
    }

    /** Sum of calories for today */
    public static int getTodayCalories(int userId) {
        String sql = "SELECT SUM(calories_burned) FROM activities WHERE user_id = ? AND activity_date = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur getTodayCalories: " + e.getMessage());
        }
        return 0;
    }

    /** Sum of distance for today */
    public static double getTodayDistance(int userId) {
        String sql = "SELECT SUM(distance_km) FROM activities WHERE user_id = ? AND activity_date = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Erreur getTodayDistance: " + e.getMessage());
        }
        return 0.0;
    }

    /** Sum of duration for today */
    public static int getTodayDuration(int userId) {
        String sql = "SELECT SUM(duration_minutes) FROM activities WHERE user_id = ? AND activity_date = CURDATE()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur getTodayDuration: " + e.getMessage());
        }
        return 0;
    }
}
