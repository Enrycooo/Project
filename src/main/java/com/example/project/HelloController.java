package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import Class.Personne;
import Class.Enseignant;

import java.util.HashMap;
import java.util.Map;

public class HelloController {
    @FXML
    private ComboBox<String> choiceBox;
    @FXML
    private VBox textFieldContainer;
    @FXML
    private VBox dynamicFieldsContainer;
    @FXML
    private TextField textFieldNom; // Champ "Nom"
    @FXML
    private TextField textFieldNumSecu; // Champ "Numéro de sécurité"
    @FXML
    private TextField coursesField; // Champ "Nombre de cours"
    @FXML
    private TextField specialtyField; // Champ "Specialite"

    public HelloController() {
    }

    private Map<String, TextField> dynamicTextFields = new HashMap<>(); // Map pour stocker les TextFields dynamiques

    @FXML
    protected void onComboBoxClick() {
        String selectedValue = choiceBox.getValue();

        // Nettoyer le conteneur des textfields existants.
        dynamicFieldsContainer.getChildren().clear();

        if (selectedValue != null) {
            if (selectedValue.equals("Enseignant")) {
                // Créer et ajouter les TextFields pour "Nombre de cours" et "Spécialité".
                TextField coursesField = createStyledTextField("Nombre de cours");
                TextField specialtyField = createStyledTextField("Spécialité");

                // Ajouter les TextFields au conteneur.
                dynamicFieldsContainer.getChildren().addAll(coursesField, specialtyField);

                // Stocke les TextFields dans Map avec clé unique
                dynamicTextFields.put("textFieldNbrCours", coursesField);
                dynamicTextFields.put("textFieldSpecialite", specialtyField);
            }
        }
    }
    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setAlignment(Pos.CENTER);
        textField.setPrefHeight(25.0);
        textField.setPrefWidth(150.0);

        // Définir les marges en utilisant Insets.
        Insets margin = new Insets(0, 125.0, 0, 125.0);
        VBox.setMargin(textField, margin);

        return textField;
    }

    public void onSubmitButtonClick(ActionEvent actionEvent) {

        String types = choiceBox.getValue();    // Valeur types de personne

        String nom = textFieldNom.getText();
        String numSecu = textFieldNumSecu.getText();

        // VALIDATION

        switch (types){
            case "Enseignant":
                int nbrCours = Integer.parseInt(dynamicTextFields.get("textFieldNbrCours").getText());  // Conversion en int
                String specialite = dynamicTextFields.get("textFieldSpecialite").getText();
                Enseignant.create(nom, numSecu, nbrCours, specialite);

                break;
            case "Vendeur":

                break;
            case "Avocat":

                break;
            default:
                System.out.println("Valeur invalide");
        }

    }
}

