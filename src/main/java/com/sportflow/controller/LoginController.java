package com.sportflow.controller;

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
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private static final String VALID_USERNAME = "hamali";
    private static final String VALID_PASSWORD = "123456";

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            switchToDashboard(event);
        } else {
            errorLabel.setText("Identifiants incorrects. Veuillez réessayer.");
        }
    }

    private void switchToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/main.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.setTitle("SportFlow - Dashboard");
            stage.setScene(scene);
            stage.setResizable(true); // Allow dashboard to be resized
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Erreur lors du chargement du tableau de bord.");
            e.printStackTrace();
        }
    }
}