package Class;

import include.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Enseignant extends Personne {

    private int nombreCours;
    private String specialite;

    //region Constructeur
    public Enseignant(int id, String nom, String numeroSecu, int nombreCours, String specialite) {
        super(id, nom, numeroSecu);
        this.nombreCours = nombreCours;
        this.specialite = specialite;
    }

    public Enseignant() {

    }
    //endregion

    //region Getter | Setter
    public int getNombreCours() {
        return nombreCours;
    }

    public void setNombreCours(int nombreCours) {
        this.nombreCours = nombreCours;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    //endregion

    //region Create
    public static void create(String nom, String numeroSecu, int nombreCours, String specialite) {
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

            enseignant.close();
            personne.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
