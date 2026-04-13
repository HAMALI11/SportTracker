package com.sportflow.controller;

import com.sportflow.db.ImcDAO;
import com.sportflow.db.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    
    // Activity Recommendation Labels
    @FXML private Label exerciseTypeLabel;
    @FXML private Label exerciseDescLabel;
    @FXML private Label recommendedTimeLabel;
    @FXML private Label intensityLabel;
    @FXML private Label mainGoalLabel;
    @FXML private Label expertAdviceLabel;
    
    // IMC Fields
    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private Label imcResultLabel;
    
    // Nutrition Bars & Labels
    @FXML private ProgressBar proteinBar;
    @FXML private ProgressBar carbBar;
    @FXML private ProgressBar lipidBar;
    @FXML private Label proteinGramsLabel;
    @FXML private Label carbGramsLabel;
    @FXML private Label lipidGramsLabel;
    @FXML private Label totalKcalLabel;
    
    @FXML private LineChart<String, Number> performanceChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SessionManager.isLoggedIn()) {
            welcomeLabel.setText("Bonjour " + SessionManager.getCurrentUsername() + " ! 👋");
            loadLastImc();
        }
    }

    private void loadLastImc() {
        if (SessionManager.isLoggedIn()) {
            String[] last = ImcDAO.getLastRecord(SessionManager.getCurrentUserId());
            if (last != null) {
                weightField.setText(last[0]);
                heightField.setText(last[1]);
                double imc = Double.parseDouble(last[2]);
                updateDashboardWithGranularIntelligence(imc);
            }
        }
    }

    private void updateDashboardWithGranularIntelligence(double imc) {
        String category = getImcCategory(imc);
        imcResultLabel.setText(String.format("IMC: %.1f (%s)", imc, category));

        // Precision Coaching based on IMC
        if (imc < 18.5) {
            setProgram("🏋️ Musculation Intensive", "Focus sur les exercices de base.", "45 min", "Modérée", "Prise de Masse");
            expertAdviceLabel.setText("✨ CONSEIL : Votre métabolisme nécessite un surplus calorique important. Priorisez le repos.");
        } else if (imc < 25) {
            setProgram("🏃 Athlétisme & HIIT", "Zone de santé optimale. Travaillez votre souffle.", "50 min", "Élevée", "Performance");
            expertAdviceLabel.setText("✨ CONSEIL : Maintenez votre routine actuelle mais intégrez du renforcement postural.");
        } else {
            setProgram("🚶 Cardio Modéré (LISS)", "Marche rapide pour brûler les graisses sans impact.", "60 min", "Zone 2", "Réduction du Gras");
            expertAdviceLabel.setText("✨ CONSEIL : La régularité est plus importante que l'intensité pour votre profil.");
        }

        adjustNutritionGranular(imc);
        setupProjectionChart(imc);
    }

    private void setProgram(String type, String desc, String time, String intens, String goal) {
        exerciseTypeLabel.setText(type);
        exerciseDescLabel.setText(desc);
        recommendedTimeLabel.setText(time);
        intensityLabel.setText("Intensité : " + intens);
        mainGoalLabel.setText(goal);
    }

    private void adjustNutritionGranular(double imc) {
        if (imc < 18.5) {
            updateNutrition(0.8, "130g / 160g", 0.9, "400g / 500g", 0.7, "80g / 100g", "2 900 kcal");
        } else if (imc < 25) {
            updateNutrition(0.7, "100g / 140g", 0.6, "250g / 400g", 0.5, "60g / 85g", "2 300 kcal");
        } else {
            updateNutrition(0.6, "90g / 150g", 0.4, "150g / 220g", 0.4, "50g / 75g", "1 850 kcal");
        }
    }

    private void updateNutrition(double p, String pg, double c, String cg, double l, String lg, String total) {
        proteinBar.setProgress(p); proteinGramsLabel.setText(pg);
        carbBar.setProgress(c); carbGramsLabel.setText(cg);
        lipidBar.setProgress(l); lipidGramsLabel.setText(lg);
        totalKcalLabel.setText("Objectif: " + total);
    }

    private void setupProjectionChart(double imc) {
        performanceChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tendance Santé (%)");
        double step = imc > 25 ? 2.8 : 1.8;
        series.getData().add(new XYChart.Data<>("S1", 0));
        series.getData().add(new XYChart.Data<>("S2", step));
        series.getData().add(new XYChart.Data<>("S3", step * 2.1));
        series.getData().add(new XYChart.Data<>("S4", step * 3.4));
        performanceChart.getData().add(series);
    }

    @FXML
    private void calculateIMC() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            if (height <= 0 || weight <= 0) return;
            double imc = weight / (Math.pow(height / 100, 2));
            String category = getImcCategory(imc);
            if (SessionManager.isLoggedIn()) {
                ImcDAO.saveImcRecord(SessionManager.getCurrentUserId(), weight, height, imc, category);
                updateDashboardWithGranularIntelligence(imc);
            }
        } catch (NumberFormatException e) {
            imcResultLabel.setText("Invalide");
        }
    }

    @FXML
    private void handleGenerateProgram() {
        // Enforce IMC calculation before switching
        calculateIMC();
        
        try {
            // Switch to the detailed AI Activity Program page
            Parent programView = FXMLLoader.load(getClass().getResource("/com/sportflow/fxml/activities_program.fxml"));
            StackPane contentArea = (StackPane) weightField.getScene().lookup("#contentArea");
            if (contentArea != null) {
                contentArea.getChildren().clear();
                contentArea.getChildren().add(programView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImcCategory(double imc) {
        if (imc < 18.5) return "Insuffisance";
        if (imc < 25) return "Normal";
        if (imc < 30) return "Surpoids";
        return "Obésité";
    }
}
