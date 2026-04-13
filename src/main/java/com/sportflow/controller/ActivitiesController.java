package com.sportflow.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivitiesController implements Initializable {

    @FXML private VBox rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation de la table des activités si nécessaire
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource("/com/sportflow/fxml/dashboard_view.fxml"));
            StackPane contentArea = (StackPane) rootPane.getScene().lookup("#contentArea");
            if (contentArea != null) {
                contentArea.getChildren().clear();
                contentArea.getChildren().add(dashboard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
