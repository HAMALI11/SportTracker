package com.sportflow.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for IMC records
 */
public class ImcDAO {

    /** Save a new IMC calculation */
    public static boolean saveImcRecord(int userId, double weight, double height, double imcValue, String category) {
        String sql = "INSERT INTO imc_records (user_id, weight, height, imc_value, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, weight);
            stmt.setDouble(3, height);
            stmt.setDouble(4, imcValue);
            stmt.setString(5, category);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur saveImcRecord: " + e.getMessage());
            return false;
        }
    }

    /** Get last IMC record for display on dashboard */
    public static String[] getLastRecord(int userId) {
        String sql = "SELECT weight, height, imc_value, category FROM imc_records WHERE user_id = ? ORDER BY recorded_at DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("weight"),
                    rs.getString("height"),
                    rs.getString("imc_value"),
                    rs.getString("category")
                };
            }
        } catch (SQLException e) {
            System.err.println("Erreur getLastRecord: " + e.getMessage());
        }
        return null;
    }

    /** Get all IMC history for a user */
    public static List<String[]> getHistory(int userId) {
        List<String[]> records = new ArrayList<>();
        String sql = "SELECT weight, height, imc_value, category, recorded_at FROM imc_records WHERE user_id = ? ORDER BY recorded_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                records.add(new String[]{
                    rs.getString("weight"),
                    rs.getString("height"),
                    rs.getString("imc_value"),
                    rs.getString("category"),
                    rs.getString("recorded_at")
                });
            }
        } catch (SQLException e) {
            System.err.println("Erreur getHistory: " + e.getMessage());
        }
        return records;
    }
}
