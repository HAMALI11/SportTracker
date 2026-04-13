package com.sportflow.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for goals (objectifs)
 */
public class GoalDAO {

    /** Save a new goal to the database */
    public static boolean addGoal(int userId, String title, String duration, String healthContext) {
        String sql = "INSERT INTO goals (user_id, title, duration, health_context) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, duration);
            stmt.setString(4, healthContext);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur addGoal: " + e.getMessage());
            return false;
        }
    }

    /** Get all active (non-achieved) goals for a user */
    public static List<String[]> getActiveGoals(int userId) {
        List<String[]> goals = new ArrayList<>();
        String sql = "SELECT id, title, duration FROM goals WHERE user_id = ? AND is_achieved = FALSE ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                goals.add(new String[]{
                    rs.getString("id"),
                    rs.getString("title"),
                    rs.getString("duration")
                });
            }
        } catch (SQLException e) {
            System.err.println("Erreur getActiveGoals: " + e.getMessage());
        }
        return goals;
    }

    /** Mark a goal as achieved (soft delete) */
    public static boolean markAsAchieved(int goalId) {
        String sql = "UPDATE goals SET is_achieved = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, goalId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur markAsAchieved: " + e.getMessage());
            return false;
        }
    }

    /** Count active goals for a user */
    public static int countActive(int userId) {
        String sql = "SELECT COUNT(*) FROM goals WHERE user_id = ? AND is_achieved = FALSE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur countActive: " + e.getMessage());
        }
        return 0;
    }

    /** Count achieved goals for a user */
    public static int countAchieved(int userId) {
        String sql = "SELECT COUNT(*) FROM goals WHERE user_id = ? AND is_achieved = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Erreur countAchieved: " + e.getMessage());
        }
        return 0;
    }
}
