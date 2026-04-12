package com.sportflow;

/**
 * Main entry point for the application to avoid "JavaFX runtime components are missing" error.
 * This class does NOT extend javafx.application.Application.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApplication.main(args);
    }
}
