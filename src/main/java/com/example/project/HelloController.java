package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ComboBox<String> choiceBox;
    @FXML
    private Label lblNombreCours;
    @FXML
    private Label lblSpecialite;
    @FXML
    private Label txtSpecialite;
    @FXML
    private Label ;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("C'est toi le plus beau habibi");
    }

    @FXML
    protected void onComboBoxClick() {
        String selectedValue = choiceBox.getValue();

        if (selectedValue != null) {
            if (selectedValue.equals("Enseignant")) {
                lblNombreCours.setVisible(true);
                lblSpecialite.setVisible(true);
                txtSpecialite.setVisible(true);
                txtNombrecours.setVisible(true);
            } else {
                lblNombreCours.setVisible(false);
                lblSpecialite.setVisible(false);
                txtSpecialite.setVisible(false);
                txtNombrecours.setVisible(false);
            }
        }
    }
}
