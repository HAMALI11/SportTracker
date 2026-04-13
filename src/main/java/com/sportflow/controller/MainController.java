package com.sportflow.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load dashboard by default
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        loadView("/com/sportflow/fxml/dashboard_view.fxml");
    }

    @FXML
    private void showActivities() {
        loadView("/com/sportflow/fxml/activities.fxml");
    }

    @FXML
    private void showNutrition() {
        loadView("/com/sportflow/fxml/nutrition.fxml");
    }

    @FXML
    private void showGoals() {
        loadView("/com/sportflow/fxml/objectifs.fxml");
    }

    @FXML
    private void showCommunity() {
        loadView("/com/sportflow/fxml/community.fxml");
    }

    @FXML
    private void showSettings() {
        System.out.println("Show Settings");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/login.fxml"));
            Parent root = loader.load();
            
            // Récupérer le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.setTitle("SportFlow - Connexion");
            stage.setScene(scene);
            stage.setResizable(false); // Le login est fixe
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load FXML: " + fxmlPath);
        }
    }
}
