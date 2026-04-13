package com.sportflow.controller;

import com.sportflow.db.SessionManager;
import com.sportflow.db.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private VBox rootPane;
    @FXML private TableView<String[]> usersTable;
    @FXML private TableColumn<String[], String> colId;
    @FXML private TableColumn<String[], String> colUsername;
    @FXML private TableColumn<String[], String> colEmail;
    @FXML private TableColumn<String[], String> colDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        refreshTable();
    }

    private void setupTable() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colUsername.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        
        usersTable.setStyle("-fx-selection-bar: #00A3FF;");
    }

    @FXML
    private void refreshTable() {
        List<String[]> users = UserDAO.getAllUsers();
        ObservableList<String[]> observableList = FXCollections.observableArrayList(users);
        usersTable.setItems(observableList);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("SportFlow - Connexion");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
