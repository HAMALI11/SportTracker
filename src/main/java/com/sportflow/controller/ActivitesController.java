package com.sportflow.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class ActivitesController implements Initializable {

    @FXML private BarChart<String, Number> activitesChart;
    @FXML private TableView<String[]> activitesTable;
    @FXML private TableColumn<String[], String> colType;
    @FXML private TableColumn<String[], String> colDate;
    @FXML private TableColumn<String[], String> colDuree;
    @FXML private TableColumn<String[], String> colDistance;
    @FXML private TableColumn<String[], String> colCalories;
    @FXML private TableColumn<String[], String> colIntensite;
    @FXML private ComboBox<String> periodeCombo;
    @FXML private Label seancesLabel;
    @FXML private Label distanceTotaleLabel;
    @FXML private Label tempsTotalLabel;
    @FXML private Label caloriesTotalesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupBarChart();
        setupTable();
        setupCombo();
    }

    private void setupBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Séances");
        series.getData().add(new XYChart.Data<>("Course", 7));
        series.getData().add(new XYChart.Data<>("Cyclisme", 4));
        series.getData().add(new XYChart.Data<>("Natation", 3));
        series.getData().add(new XYChart.Data<>("Muscu", 3));
        series.getData().add(new XYChart.Data<>("Yoga", 1));
        activitesChart.getData().add(series);
    }

    private void setupTable() {
        colType.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[0]));
        colDate.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        colDuree.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[2]));
        colDistance.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[3]));
        colCalories.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[4]));
        colIntensite.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[5]));

        ObservableList<String[]> data = FXCollections.observableArrayList(
            new String[]{"🏃 Course à pied",  "12 Avr. 08:30", "45",  "8,2",  "520",  "Haute"},
            new String[]{"🚴 Cyclisme",        "11 Avr. 07:00", "60",  "22,0", "380",  "Moyenne"},
            new String[]{"🏊 Natation",        "10 Avr. 18:00", "50",  "2,1",  "440",  "Haute"},
            new String[]{"🏋️ Musculation",    "09 Avr. 10:00", "75",  "—",    "310",  "Moyenne"},
            new String[]{"🧘 Yoga",            "08 Avr. 07:30", "40",  "—",    "120",  "Faible"},
            new String[]{"🏃 Course à pied",  "07 Avr. 17:00", "55",  "9,5",  "590",  "Haute"},
            new String[]{"🚴 Cyclisme",        "05 Avr. 08:00", "90",  "35,0", "520",  "Haute"},
            new String[]{"🏊 Natation",        "03 Avr. 19:00", "45",  "1,8",  "390",  "Moyenne"}
        );
        activitesTable.setItems(data);
    }

    private void setupCombo() {
        periodeCombo.setItems(FXCollections.observableArrayList(
            "Avril 2026", "Mars 2026", "Février 2026", "Janvier 2026"
        ));
        periodeCombo.setValue("Avril 2026");
    }

    @FXML
    private void handleNewActivity() {
        System.out.println("Ouvrir formulaire nouvelle activité...");
        // TODO: Open activity dialog
    }

    @FXML
    private void handleNavigateDashboard() {
        navigateTo("/com/sportflow/fxml/main.fxml");
    }

    @FXML
    private void handleNavigateNutrition() {
        navigateTo("/com/sportflow/fxml/nutrition.fxml");
    }

    @FXML
    private void handleNavigateObjectifs() {
        navigateTo("/com/sportflow/fxml/objectifs.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) activitesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
