package com.sportflow.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunityController implements Initializable {

    @FXML private ScrollPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
