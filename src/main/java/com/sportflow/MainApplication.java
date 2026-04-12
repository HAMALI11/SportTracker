package com.sportflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement de la page de connexion au démarrage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sportflow/fxml/login.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("SportFlow - Connexion");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // La fenêtre de login est fixe
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}