package com.sportflow.controller;

import com.sportflow.db.SessionManager;
import com.sportflow.db.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.io.IOException;

public class RegisterController {

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String fullName         = fullNameField.getText().trim();
        String username         = usernameField.getText().trim();
        String email            = emailField.getText().trim();
        String password         = passwordField.getText();
        String confirmPassword  = confirmPasswordField.getText();

        // --- Validation ---
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("Tous les champs obligatoires doivent être remplis.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showError("Les mots de passe ne correspondent pas.");
            return;
        }
        if (password.length() < 6) {
            showError("Le mot de passe doit contenir au moins 6 caractères.");
            return;
        }

        // --- Check if username already exists in DB ---
        if (UserDAO.usernameExists(username)) {
            showError("Ce nom d'utilisateur est déjà pris. Choisissez-en un autre.");
            return;
        }

        // --- Save to database ---
        boolean success = UserDAO.register(username, password, email);
        if (success) {
            // Auto-login after registration
            int userId = UserDAO.authenticate(username, password);
            if (userId != -1) {
                SessionManager.login(userId, username);
            }
            // Navigate to dashboard
            navigateToDashboard(event);
        } else {
            showError("Erreur lors de la création du compte. Réessayez.");
        }
    }

    private void showError(String message) {
        errorLabel.setStyle("-fx-text-fill: #FF4B2B; -fx-font-size: 12px;");
        errorLabel.setText(message);
    }

    private void showSuccess(String message) {
        errorLabel.setStyle("-fx-text-fill: #00FF94; -fx-font-size: 12px;");
        errorLabel.setText(message);
    }

    private void navigateToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("SportFlow - Dashboard");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            showError("Erreur lors du chargement du tableau de bord.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("SportFlow - Connexion");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
