package Class;

import include.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vacataire extends Enseignant {

    private int nombreVacations;

    //region Constructeur
    public Vacataire(int id, String nom, String numeroSecu, int nombreCours, String specialite, int nombreVacations) {
        super(id, nom, numeroSecu, nombreCours, specialite);
        this.nombreVacations = nombreVacations;
    }
    //endregion

    public Vacataire() {
    }

    //region Getter || Setter
    public int getNombreVacations() {
        return nombreVacations;
    }

    public void setNombreVacations(int nombreVacations) {
        this.nombreVacations = nombreVacations;
    }
    //endregion
    //region Create
    public static void create(String nom, String numeroSecu, int nombreCours, String specialite, int nombreVacations) {
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

            PreparedStatement vacataire = connection.prepareStatement("INSERT INTO vacataire (nombre_vacations, id_enseignant) VALUES (?, ?)");
            vacataire.setInt(1, nombreVacations);
            vacataire.setInt(2, lastInsertedId);
            vacataire.executeUpdate();

            enseignant.close();
            personne.close();
            vacataire.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion
    }
