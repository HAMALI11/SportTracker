package com.sportflow.db;

/**
 * Holds the current logged-in user's session data.
 * Set once at login and accessible from any controller.
 */
public class SessionManager {

    private static int currentUserId   = -1;
    private static String currentUsername = "";

    public static void login(int userId, String username) {
        currentUserId   = userId;
        currentUsername = username;
        System.out.println("✅ Session ouverte pour: " + username + " (id=" + userId + ")");
    }

    public static void logout() {
        currentUserId   = -1;
        currentUsername = "";
        System.out.println("🔌 Session fermée.");
    }

    public static int getCurrentUserId()   { return currentUserId; }
    public static String getCurrentUsername() { return currentUsername; }
    public static boolean isLoggedIn()     { return currentUserId != -1; }
}
