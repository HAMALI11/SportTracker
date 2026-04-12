package com.sportflow.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private LineChart<String, Number> performanceChart;
    @FXML private Label caloriesLabel;
    @FXML private Label distanceLabel;
    @FXML private Label timeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupPerformanceChart();
    }

    private void setupPerformanceChart() {
        XYChart.Series<String, Number> vitesseSeries = new XYChart.Series<>();
        vitesseSeries.setName("Vitesse moy. (km/h)");
        vitesseSeries.getData().add(new XYChart.Data<>("Lun", 8.5));
        vitesseSeries.getData().add(new XYChart.Data<>("Mar", 10.2));
        vitesseSeries.getData().add(new XYChart.Data<>("Mer", 9.8));
        vitesseSeries.getData().add(new XYChart.Data<>("Jeu", 11.5));
        vitesseSeries.getData().add(new XYChart.Data<>("Ven", 12.1));
        vitesseSeries.getData().add(new XYChart.Data<>("Sam", 14.0));
        vitesseSeries.getData().add(new XYChart.Data<>("Dim", 13.2));

        performanceChart.getData().add(vitesseSeries);
    }

    @FXML
    private void handleNewActivity() {
        System.out.println("Ouvrir formulaire d'activité...");
        // TODO: Open activity dialog
    }
}
