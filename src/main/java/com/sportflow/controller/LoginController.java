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
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private static int attempts = 0;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        // --- Special Case: ADMIN ---
        if ("admin".equals(username) && "admin123".equals(password)) {
            SessionManager.login(0, "ADMIN");
            switchToAdmin(event);
            return;
        }

        // Authenticate against MySQL database
        int userId = UserDAO.authenticate(username, password);

        if (userId != -1) {
            attempts = 0;
            SessionManager.login(userId, username);
            switchToDashboard(event);
        } else {
            attempts++;
            if (attempts >= 3) {
                showLockedPage(event);
            } else {
                errorLabel.setText("Identifiants incorrects (" + attempts + "/3). Veuillez réessayer.");
            }
        }
    }

    private void showLockedPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/account_locked.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("SportFlow - Sécurité");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToLoginFromLocked(ActionEvent event) {
        attempts = 0;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("SportFlow - Inscription");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement de la page d'inscription.");
            e.printStackTrace();
        }
    }

    private void switchToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("SportFlow - Bienvenue " + SessionManager.getCurrentUsername());
            stage.setScene(scene);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement du tableau de bord.");
            e.printStackTrace();
        }
    }

    private void switchToAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/sportflow/fxml/admin_users.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("SportFlow - Panneau d'administration");
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement de la page admin.");
            e.printStackTrace();
        }
    }
}