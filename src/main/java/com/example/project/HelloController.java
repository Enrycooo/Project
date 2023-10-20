package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

@SuppressWarnings("ALL")
public class HelloController {
    @FXML
    private ComboBox<String> choiceBox;
    @FXML
    private ComboBox<String> choiceBox2;
    @FXML
    private VBox textFieldContainer;
    @FXML
    private VBox dynamicFieldsContainer;

    @FXML
    public void initialize() {
        // Initialisez choiceBox2 ici si nécessaire.
        choiceBox2 = createStyledComboBox("Type d'Enseignant", "Permanent", "Vacataire");
    }

    @FXML
    protected void onComboBox1Click() {
        String selectedValue = choiceBox.getValue();

        // Nettoyer le conteneur des textfields existants.
        dynamicFieldsContainer.getChildren().clear();

        switch (selectedValue) {
            case "Enseignant":
                TextField coursField = createStyledTextField("Nombre de cours");
                TextField specialtyField = createStyledTextField("Spécialité");

                // Créer un conteneur VBox pour aligner les éléments horizontalement.
                VBox enseignantFields = new VBox(30.0); // Espacement entre les éléments.

                // Ajouter les éléments au conteneur HBox.
                enseignantFields.getChildren().addAll(coursField, specialtyField, choiceBox2);
                // Ajouter le conteneur HBox au conteneur principal (VBox).
                dynamicFieldsContainer.getChildren().add(enseignantFields);
                initializeChoiceBox2();
                break;
            case "Vendeur":
                TextField ancienneteField = createStyledTextField("Ancienneté");
                TextField nomStandField = createStyledTextField("Nom du stand");

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(ancienneteField, nomStandField);
                break;
            case "Avocat":
                TextField nbAffaireField = createStyledTextField("Nombre d'affaires");
                TextField AdresseCabField = createStyledTextField("adresse du cabinet");

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(nbAffaireField, AdresseCabField);
                break;
            default:
                break;
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

    private ComboBox<String> createStyledComboBox(String promptText, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(promptText);
        comboBox.setPrefHeight(26.0);
        comboBox.setPrefWidth(150.0);

        // Définir les marges en utilisant Insets.
        Insets margin = new Insets(0, 125.0, 0, 125.0);
        VBox.setMargin(comboBox, margin);

        for (String itemText : items) {
            comboBox.getItems().add(itemText);
            comboBox.setId(itemText); // Utilisez le nom comme ID.
        }

        comboBox.setOnAction(event -> {
            // Gérez l'événement de sélection ici.
            // Vous pouvez obtenir l'élément sélectionné et son ID à partir de l'événement.
            String selectedValue = comboBox.getValue();
            String selectedItemID = comboBox.getId();
            // Faites ce que vous devez faire avec ces informations.
        });

        return comboBox;
    }

    private void initializeChoiceBox2() {
        choiceBox2.setOnAction(event -> {
            onEnseignantTypeSelected();
        });
    }
    private void onEnseignantTypeSelected() {
        String selectedValue2 = choiceBox2.getValue();

        // Nettoyer le conteneur des textfields existants.
        dynamicFieldsContainer.getChildren().clear();

        TextField coursField = createStyledTextField("Nombre de cours");
        TextField specialtyField = createStyledTextField("Spécialité");

        // Créer un conteneur VBox pour aligner les éléments horizontalement.
        VBox enseignantFields = new VBox(30.0); // Espacement entre les éléments.

        // Ajouter les éléments au conteneur HBox.
        enseignantFields.getChildren().addAll(coursField, specialtyField, choiceBox2);
        // Ajouter le conteneur HBox au conteneur principal (VBox).
        dynamicFieldsContainer.getChildren().add(enseignantFields);

        switch (selectedValue2) {
            case "Permanent":
                TextField nbBureauField = createStyledTextField("Numéro de bureau");

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(nbBureauField);
                break;
            case "Vacataire":
                TextField nbVacationField = createStyledTextField("Nombre de vacations");

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(nbVacationField);
                break;
            default:
                break;
        }
    }

}
