package com.sportflow.controller;

import com.sportflow.db.ImcDAO;
import com.sportflow.db.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class NutritionController implements Initializable {

    @FXML private ChoiceBox<String> dayChoice;
    @FXML private Label recommendationLabel;
    @FXML private Label menuTitleLabel;
    
    @FXML private Label breakfastItems;
    @FXML private Label lunchItems;
    @FXML private Label dinnerItems;
    @FXML private Label snacksItems;

    @FXML private Button btnMassGain, btnMaintain, btnWeightLoss;
    @FXML private VBox rootPane;

    private String currentProfile = "Normal";
    private final Map<String, Map<String, String[]>> mealData = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupDayChoices();
        initMealData();
        
        if (SessionManager.isLoggedIn()) {
            String[] lastImc = ImcDAO.getLastRecord(SessionManager.getCurrentUserId());
            if (lastImc != null) {
                currentProfile = lastImc[3];
                applyRecommendation(currentProfile);
            } else {
                currentProfile = "Normal";
                updateMenus();
            }
        }

        dayChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateMenus());
    }

    private void setupDayChoices() {
        dayChoice.getItems().addAll("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
        dayChoice.setValue("Lundi");
    }

    private void applyRecommendation(String category) {
        currentProfile = category;
        recommendationLabel.setText("🎯 Recommandation SportFlow : Profil " + category + " détecté.");
        if (category.equals("Insuffisance")) setActiveButton(btnMassGain);
        else if (category.equals("Surpoids") || category.equals("Obésité")) setActiveButton(btnWeightLoss);
        else setActiveButton(btnMaintain);
        updateMenus();
    }

    @FXML private void showMassGain() { currentProfile = "Insuffisance"; setActiveButton(btnMassGain); updateMenus(); }
    @FXML private void showMaintain() { currentProfile = "Normal"; setActiveButton(btnMaintain); updateMenus(); }
    @FXML private void showWeightLoss() { currentProfile = "Obésité"; setActiveButton(btnWeightLoss); updateMenus(); }

    private void updateMenus() {
        String day = dayChoice.getValue();
        String profileKey = currentProfile;
        if (profileKey.equals("Surpoids")) profileKey = "Obésité"; // Group same advice

        String[] meals = mealData.getOrDefault(profileKey, new HashMap<>()).get(day);
        
        if (meals != null) {
            menuTitleLabel.setText("📅 Menu du " + day + " (" + currentProfile + ")");
            breakfastItems.setText(meals[0]);
            lunchItems.setText(meals[1]);
            dinnerItems.setText(meals[2]);
            snacksItems.setText(meals[3]);
        }
    }

    private void initMealData() {
        // PERTE DE POIDS (OBESE)
        Map<String, String[]> loss = new HashMap<>();
        loss.put("Lundi", new String[]{"Fromage blanc 0%, 30g avoine, thé vert", "Salade de thon, concombres, 1 pomme", "Poisson blanc vapeur, épinards", "Bâtonnets de céleri"});
        loss.put("Mardi", new String[]{"Omelette 3 blancs d'oeufs, café noir", "Émincé de dinde, courgettes grillées", "Soupe de légumes détox, 1 yaourt nature", "1 poignée de myrtilles"});
        loss.put("Mercredi", new String[]{"Smoothie vert (épinards, pomme, eau)", "Pavé de cabillaud, haricots verts", "Salade de tomates, feta légère, dinde", "1 thé au gingembre"});
        loss.put("Jeudi", new String[]{"2 tranches de pain seigle, 1 oeuf", "Lentilles, carottes, oignons", "Poulet grillé, brocolis vapeur", "Yaourt soja sans sucre"});
        loss.put("Vendredi", new String[]{"Porridge eau et cannelle", "Salade de pois chiches, poivrons", "Colin en papillote, asperges", "Carottes crues"});
        loss.put("Samedi", new String[]{"Salade de fruits frais (sans sucre)", "Steak 5%, salade verte, vinaigrette citron", "Potage courge, 1 tranche jambon", "Amandes (max 10)"});
        loss.put("Dimanche", new String[]{"Thé, 1 pamplemousse, 2 biscottes", "Assiette de légumes rôtis, tofu", "Dîner léger : Bouillon et tisane", "Eau détox citronnée"});
        mealData.put("Obésité", loss);

        // MAINTIEN (NORMAL)
        Map<String, String[]> maintain = new HashMap<>();
        maintain.put("Lundi", new String[]{"Avocat toast, 1 oeuf poché", "Poulet, quinoa, légumes grillés", "Saumon, patates douces, salade", "1 Pomme + 1 carré choco noir"});
        maintain.put("Mardi", new String[]{"Muesli, yaourt grec, baies", "Steak, riz basmati, ratatouille", "Omelette jambon-fromage, crudités", "Amandes et noix"});
        maintain.put("Mercredi", new String[]{"Pancakes avoine, beurre cacahuète", "Salade césar maison (sauce légère)", "Thon grillé, purée de brocolis", "Fromage frais"});
        maintain.put("Jeudi", new String[]{"Oeufs brouillés, pain complet", "Wok de boeuf aux légumes, nouilles", "Soupe minestrone, poulet", "Fruit de saison"});
        maintain.put("Vendredi", new String[]{"Smoothie protéiné, 1 banane", "Burger maison (pain complet, steak 5%)", "Truite aux amandes, riz", "Graines de courge"});
        maintain.put("Samedi", new String[]{"Brunch : Oeufs, bacon, fruits", "Risotto aux champignons", "Salade niçoise complète", "Yaourt et miel"});
        maintain.put("Dimanche", new String[]{"Petit déj anglais (léger)", "Rôti de dinde, pommes de terre", "Dîner buffet de crudités", "Thé et biscuits avoine"});
        mealData.put("Normal", maintain);

        // PRISE DE MASSE (NAIF)
        Map<String, String[]> gain = new HashMap<>();
        gain.put("Lundi", new String[]{"Le Titan : 100g avoine, 4 oeufs, lait entier", "Double burger boeuf, frites maison", "Pâtes au saumon et crème fraîche", "Gainer maison : Banane + Beurre cacahuète"});
        gain.put("Mardi", new String[]{"6 Pancakes, miel, noix de coco", "Lasagnes à la bolognaise (portion XL)", "Poulet coco-curry, double riz", "Fromage blanc entier + fruits secs"});
        gain.put("Mercredi", new String[]{"Bowl cake protéiné, chocolat noir", "Chili con carne, maïs, riz", "Entrecôte 250g, frites de patate douce", "Sandwich thon mayo"});
        gain.put("Jeudi", new String[]{"Oeufs sur le plat (4), jambon, pain", "Spaghetti carbonara (la vraie)", "Poisson gras, pois chiches, huile olive", "Barre protéinée"});
        gain.put("Vendredi", new String[]{"Shake avoine-whey-lait", "Paëlla complète (viandes et fruits mer)", "Steak haché (2), purée beurre", "Mélange de noix XL"});
        gain.put("Samedi", new String[]{"Omelette fromage (5 oeufs), pain", "Pizza maison garnie viande", "Bœuf aux oignons, nouilles sautées", "Dessert lacté entier"});
        gain.put("Dimanche", new String[]{"Grand Brunch protéiné", "Poulet rôti entier, légumes, riz", "Pâtes au pesto et pignons", "Yaourt grec et granola"});
        mealData.put("Insuffisance", gain);
    }

    private void setActiveButton(Button b) {
        btnMassGain.setStyle("-fx-background-color: #2D2D2D;");
        btnMaintain.setStyle("-fx-background-color: #2D2D2D;");
        btnWeightLoss.setStyle("-fx-background-color: #2D2D2D;");
        b.setStyle("-fx-background-color: #00A3FF; -fx-text-fill: white;");
    }

    @FXML private void handleBackToDashboard() {
        try {
            Parent dashboard = FXMLLoader.load(getClass().getResource("/com/sportflow/fxml/dashboard_view.fxml"));
            StackPane contentArea = (StackPane) rootPane.getScene().lookup("#contentArea");
            if (contentArea != null) { contentArea.getChildren().clear(); contentArea.getChildren().add(dashboard); }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
