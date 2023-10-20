package com.example.project;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HelloController {
    @FXML
    private ComboBox<String> choiceBox;
    @FXML
    private VBox textFieldContainer;
    @FXML
    private VBox dynamicFieldsContainer;
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
}

