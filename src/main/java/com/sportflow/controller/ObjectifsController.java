package com.sportflow.controller;

import com.sportflow.db.GoalDAO;
import com.sportflow.db.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ObjectifsController implements Initializable {

    @FXML private ProgressBar goal1Bar;
    @FXML private ProgressBar goal2Bar;
    @FXML private ProgressBar goal3Bar;
    @FXML private Label activeGoalsLabel;
    @FXML private Label achievedLabel;
    @FXML private Label nextDeadlineLabel;
    @FXML private Label avgProgressLabel;
    @FXML private ChoiceBox<String> durationChoice;
    @FXML private TextArea healthTextArea;
    @FXML private TextField manualGoalInput;
    @FXML private ListView<String> goalsListView;
    @FXML private VBox rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupChoices();
        refreshGoals();
    }

    private void refreshGoals() {
        if (!SessionManager.isLoggedIn()) return;

        int userId = SessionManager.getCurrentUserId();
        
        // Load counts
        activeGoalsLabel.setText(String.valueOf(GoalDAO.countActive(userId)));
        achievedLabel.setText(String.valueOf(GoalDAO.countAchieved(userId)));
        
        // Load list
        goalsListView.getItems().clear();
        List<String[]> activeGoals = GoalDAO.getActiveGoals(userId);
        for (String[] g : activeGoals) {
            // Store as "ID: Title [Duration]"
            goalsListView.getItems().add(g[0] + ": " + g[1] + " [" + g[2] + "]");
        }
        
        goalsListView.setStyle("-fx-control-inner-background: #1A1A1A; -fx-text-fill: white;");
        
        // Progression bars (static for now, just to avoid nulls)
        if (goal1Bar != null) goal1Bar.setProgress(0.72);
        if (goal2Bar != null) goal2Bar.setProgress(0.40);
        if (goal3Bar != null) goal3Bar.setProgress(0.60);
    }

    @FXML
    private void addManualGoal() {
        if (!SessionManager.isLoggedIn()) return;

        String goal = manualGoalInput.getText().trim();
        String duration = durationChoice.getValue();
        String context = healthTextArea.getText();

        if (!goal.isEmpty()) {
            boolean success = GoalDAO.addGoal(SessionManager.getCurrentUserId(), goal, duration, context);
            if (success) {
                manualGoalInput.clear();
                refreshGoals();
            }
        }
    }

    @FXML
    private void removeSelectedGoal() {
        String selected = goalsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Extract ID from string "ID: Title..."
            try {
                int id = Integer.parseInt(selected.split(":")[0]);
                boolean success = GoalDAO.markAsAchieved(id);
                if (success) {
                    refreshGoals();
                }
            } catch (Exception e) {
                System.err.println("Erreur parsing ID: " + e.getMessage());
            }
        }
    }

    private void setupChoices() {
        if (durationChoice != null) {
            durationChoice.getItems().addAll("Une semaine", "Un mois");
            durationChoice.setValue("Une semaine");
        }
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
