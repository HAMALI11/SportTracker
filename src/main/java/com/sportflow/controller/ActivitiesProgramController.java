package com.sportflow.controller;

import com.sportflow.db.ImcDAO;
import com.sportflow.db.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivitiesProgramController implements Initializable {

    @FXML private VBox rootPane;
    @FXML private Label profileSubtitle;
    @FXML private Label aiAnalysisLabel;
    
    @FXML private Label session1Title, session1Desc, session1Exercises;
    @FXML private Label session2Title, session2Desc, session2Exercises;
    @FXML private Label session3Title, session3Desc, session3Exercises;
    
    @FXML private Label altCardioLabel;
    @FXML private Label altStrengthLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SessionManager.isLoggedIn()) {
            String[] lastImc = ImcDAO.getLastRecord(SessionManager.getCurrentUserId());
            if (lastImc != null) {
                double imc = Double.parseDouble(lastImc[2]);
                generateAiProgram(imc);
            }
        }
    }

    private void generateAiProgram(double imc) {
        if (imc < 18.5) {
            setupGainProgram();
        } else if (imc < 25) {
            setupAthleticProgram();
        } else {
            setupLossProgram();
        }
    }

    private void setupGainProgram() {
        profileSubtitle.setText("Profil : Prise de Masse Musculaire Optimisée");
        aiAnalysisLabel.setText("L'IA détecte une faible densité corporelle. Priorité : Stimuler la croissance fibreuse et augmenter la force globale.");
        
        session1Title.setText("Poussée Explosive");
        session1Desc.setText("Sollicite les muscles du haut du corps pour une base solide.");
        session1Exercises.setText("- Développé couché (3x10)\n- Pompes lestées (3x12)\n- Barre front (3x10)");
        
        session2Title.setText("Fondations Jambes");
        session2Desc.setText("Boost hormonal via les exercices polyarticulaires du bas.");
        session2Exercises.setText("- Squat barre (4x8)\n- Presse à cuisses (3x12)\n- Mollets debout (4x15)");
        
        session3Title.setText("Tirage & Dos");
        session3Desc.setText("Largeur et épaisseur du dos pour une posture athlétique.");
        session3Exercises.setText("- Tractions larges (3x Max)\n- Rowing barre (3x10)\n- Curl haltères (3x12)");
        
        altCardioLabel.setText("Remplacez par : 20 min de marche rapide (Pas de HIIT)");
        altStrengthLabel.setText("Remplacez par : Circuit au poids de corps si pas de matériel.");
    }

    private void setupAthleticProgram() {
        profileSubtitle.setText("Profil : Performance & Condition Physique");
        aiAnalysisLabel.setText("L'IA détecte un IMC sain. Priorité : Améliorer l'endurance cardiovasculaire et la tonicité musculaire.");
        
        session1Title.setText("HIIT Brûleur");
        session1Desc.setText("Intervalles haute intensité pour booster le métabolisme.");
        session1Exercises.setText("- 30s Sprint / 30s Repos (x10)\n- Burpees (3x15)\n- Mountain Climbers");
        
        session2Title.setText("Renforcement Total");
        session2Desc.setText("Circuit full-body pour la coordination et la puissance.");
        session2Exercises.setText("- Kettlebell Swings (3x20)\n- Fentes marchées (3x20)\n- Gainage (3x1 min)");
        
        session3Title.setText("Endurance Fondamentale");
        session3Desc.setText("Améliorer la capacité pulmonaire sur le long terme.");
        session3Exercises.setText("- Footing modéré (45 min)\n- Ou Natation (30 min)\n- Étirements profonds");

        altCardioLabel.setText("Remplacez par : Match de Tennis ou Football (Sport co)");
        altStrengthLabel.setText("Remplacez par : Yoga Dynamique ou Pilates.");
    }

    private void setupLossProgram() {
        profileSubtitle.setText("Profil : Perte de Gras & Métabolisme");
        aiAnalysisLabel.setText("L'IA détecte un surplus de réserves adipeuses. Priorité : Mobilité articulaire et lipolyse prolongée.");
        
        session1Title.setText("Cardio LISS");
        session1Desc.setText("Maintien d'un rythme constant pour brûler les graisses sans s'épuiser.");
        session1Exercises.setText("- Marche en inclinaison (45 min)\n- Vélo d'appartement (Zone 2)\n- Marche nordique");
        
        session2Title.setText("Circuit Tonification");
        session2Desc.setText("Maintenir le muscle tout en brûlant des calories.");
        session2Exercises.setText("- Air Squats (3x20)\n- Banc de step (3x15 par jambe)\n- Gainage planche (3x45s)");
        
        session3Title.setText("Mobilité & Aqua");
        session3Desc.setText("Soulager les articulations et améliorer la flexibilité.");
        session3Exercises.setText("- Aquagym tonique\n- Aqua-biking\n- Gym douce ou étirements");

        altCardioLabel.setText("Remplacez par : Danse de salon ou Randonnée en forêt.");
        altStrengthLabel.setText("Remplacez par : Travail de posture et d'équilibre.");
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

