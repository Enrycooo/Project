package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import Class.Permanent;
import Class.Vacataire;
import Class.Vendeur;
import Class.Avocat;

import java.util.HashMap;
import java.util.Map;

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
                dynamicTextFields.put("textFieldNbrCours", coursField);
                TextField specialtyField = createStyledTextField("Spécialité");
                dynamicTextFields.put("textFieldSpecialite", specialtyField);

                // Ajouter les TextFields au conteneur.
                dynamicFieldsContainer.getChildren().addAll(coursField, specialtyField);

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
                dynamicTextFields.put("textFieldAnciennete", ancienneteField);
                TextField nomStandField = createStyledTextField("Nom du stand");
                dynamicTextFields.put("textFieldNomStand", nomStandField);

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(ancienneteField, nomStandField);
                break;
            case "Avocat":
                TextField nbAffaireField = createStyledTextField("Nombre d'affaires");
                dynamicTextFields.put("textFieldNbrAffaire", nbAffaireField);
                TextField AdresseCabField = createStyledTextField("Adresse du cabinet");
                dynamicTextFields.put("textFieldAdresseCabinet", AdresseCabField);

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

    public void onSubmitButtonClick(ActionEvent actionEvent) {

        String types = choiceBox.getValue();    // Valeur types de personne
        String typesEnseignant = choiceBox2.getValue();
        String nom = textFieldNom.getText();
        String numSecu = textFieldNumSecu.getText();

        // VALIDATION

        switch (types){
            case "Enseignant":
                switch (typesEnseignant){
                    case "Vacataire":
                        int nbrVacations = Integer.parseInt(dynamicTextFields.get("textFieldNbrVacations").getText());  // Conversion en int
                        int nbrCours = Integer.parseInt(dynamicTextFields.get("textFieldNbrCours").getText());  // Conversion en int
                        String specialite = dynamicTextFields.get("textFieldSpecialite").getText();
                        Vacataire.create(nom, numSecu, nbrCours, specialite, nbrVacations);
                        break;
                    case "Permanent":
                        int numBureau = Integer.parseInt(dynamicTextFields.get("textFieldNumeroBureau").getText());  // Conversion en int
                        nbrCours = Integer.parseInt(dynamicTextFields.get("textFieldNbrCours").getText());  // Conversion en int
                        specialite = dynamicTextFields.get("textFieldSpecialite").getText();
                        Permanent.create(nom, numSecu, nbrCours, specialite, numBureau);
                        break;
                    default:
                        System.out.println("Types d'enseignant invalide");
                }
                break;
            case "Vendeur":
                    int anciennete = Integer.parseInt(dynamicTextFields.get("textFieldAnciennete").getText());
                    String nomDuStand = dynamicTextFields.get("textFieldNomStand").getText();
                    Vendeur.create(nom, numSecu, anciennete, nomDuStand);
                break;
            case "Avocat":
                    int nbrAffaires = Integer.parseInt(dynamicTextFields.get("textFieldNbrAffaire").getText());
                    String adresseCabinet = dynamicTextFields.get("textFieldAdresseCabinet").getText();
                    Avocat.create(nom, numSecu, nbrAffaires, adresseCabinet);
                break;
            default:
                System.out.println("Types de personne invalide");
        }

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
        dynamicTextFields.put("textFieldNbrCours", coursField);
        TextField specialtyField = createStyledTextField("Spécialité");
        dynamicTextFields.put("textFieldSpecialite", specialtyField);

        // Créer un conteneur VBox pour aligner les éléments horizontalement.
        VBox enseignantFields = new VBox(30.0); // Espacement entre les éléments.

        // Ajouter les éléments au conteneur HBox.
        enseignantFields.getChildren().addAll(coursField, specialtyField, choiceBox2);
        // Ajouter le conteneur HBox au conteneur principal (VBox).
        dynamicFieldsContainer.getChildren().add(enseignantFields);

        switch (selectedValue2) {
            case "Permanent":
                TextField nbBureauField = createStyledTextField("Numéro de bureau");
                dynamicTextFields.put("textFieldNumeroBureau", nbBureauField);

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(nbBureauField);
                break;
            case "Vacataire":
                TextField nbVacationField = createStyledTextField("Nombre de vacations");
                dynamicTextFields.put("textFieldNbrVacations", nbVacationField);

                // Ajouter les éléments au conteneur.
                dynamicFieldsContainer.getChildren().addAll(nbVacationField);
                break;
            default:
                break;
        }
    }
}
