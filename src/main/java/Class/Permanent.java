package Class;

import include.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Permanent extends Enseignant {

    private int numeroBureau;

    //region Constructeur
    public Permanent(int id, String nom, String numeroSecu, int nombreCours, String specialite, int numeroBureau) {
        super(id, nom, numeroSecu, nombreCours, specialite);
        this.numeroBureau = numeroBureau;
    }

    public Permanent() {

    }
    //endregion

    //region Getter | Setter
    public int getNumeroBureau() {
        return numeroBureau;
    }

    public void setNumeroBureau(int numeroBureau) {
        this.numeroBureau = numeroBureau;
    }
    //endregion

    //region Create
    public static void create(String nom, String numeroSecu, int nombreCours, String specialite, int numeroBureau) {
        try {
            // Insère un enseignant dans la table Personne
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement personne = connection.prepareStatement("INSERT INTO Personne (nom, numero_secu) VALUES (?, ?)");
            personne.setString(1, nom);
            personne.setString(2, numeroSecu);
            personne.executeUpdate();

            //region Récupération id de la personne
            String query = "SELECT LAST_INSERT_ID()";
            int lastInsertedId = 0;
            ResultSet resultSet = personne.executeQuery(query);
            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt(1);
            }
            //endregion

            PreparedStatement enseignant = connection.prepareStatement("INSERT INTO Enseignant (nombre_cours, specialite, id_personne) VALUES (?, ?, ?)");
            enseignant.setInt(1, nombreCours);
            enseignant.setString(2, specialite);
            enseignant.setInt(3, lastInsertedId);
            enseignant.executeUpdate();

            //region Récupération id de l'enseignant
            query = "SELECT LAST_INSERT_ID()";
            lastInsertedId = 0;
            resultSet = personne.executeQuery(query);
            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt(1);
            }
            //endregion

            PreparedStatement permanent = connection.prepareStatement("INSERT INTO permanent (numero_bureau, id_enseignant) VALUES (?, ?)");
            permanent.setInt(1, numeroBureau);
            permanent.setInt(2, lastInsertedId);
            permanent.executeUpdate();

            enseignant.close();
            personne.close();
            permanent.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion
}