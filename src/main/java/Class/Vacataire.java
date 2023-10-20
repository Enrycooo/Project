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

    //region getAllVacataires
    public static List<Vacataire> getAllVacataires() {
        List<Vacataire> vacataires = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT vacataire.*, enseignant.nombre_cours, enseignant.specialite, personne.nom, personne.numero_secu FROM vacataire INNER JOIN enseignant ON vacataire.id_enseignant = enseignant.id INNER JOIN personne ON personne.id = enseignant.id_personne");

            while (resultSet.next()) {
                Vacataire vacataire = new Vacataire();
                vacataire.setId(resultSet.getInt("id"));
                vacataire.setNom(resultSet.getString("nom"));
                vacataire.setNumeroSecu(resultSet.getString("numero_secu"));
                vacataire.setNombreCours(resultSet.getInt("nombre_cours"));
                vacataire.setSpecialite(resultSet.getString("specialite"));
                vacataire.setNombreVacations(resultSet.getInt("nombre_vacations"));
                vacataires.add(vacataire);
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vacataires;
    }
    //endregion

    //region getVacataireById
    public static Vacataire getVacataireById(int id) {
        Vacataire vacataire = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT vacataire.*, enseignant.nombre_cours, enseignant.specialite, personne.nom, personne.numero_secu FROM vacataire INNER JOIN enseignant ON vacataire.id_enseignant = enseignant.id INNER JOIN personne ON personne.id = enseignant.id_personne WHERE vacataire.id =" +id);

            if (resultSet.next()) {
                vacataire = new Vacataire();
                vacataire.setId(resultSet.getInt("id"));
                vacataire.setNom(resultSet.getString("nom"));
                vacataire.setNumeroSecu(resultSet.getString("numero_secu"));
                vacataire.setNombreCours(resultSet.getInt("nombre_cours"));
                vacataire.setSpecialite(resultSet.getString("specialite"));
                vacataire.setNombreVacations(resultSet.getInt("nombre_vacations"));
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vacataire;
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

    //region Update
    public static void update(Vacataire vacataire) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE personne SET nom = '" + vacataire.getNom() + "', numero_secu = '" + vacataire.getNumeroSecu() + "' WHERE id = " + vacataire.getId());
            statement.executeUpdate("UPDATE enseignant SET nombre_cours = '" + vacataire.getNombreCours() + "', specialite = '" + vacataire.getSpecialite() + "' WHERE id = " + vacataire.getId());
            statement.executeUpdate("UPDATE vacataire SET nombre_vacations = '" + vacataire.nombreVacations + "' WHERE id = " + vacataire.getId());

            statement.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region Delete
    public static void delete(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM vacataire WHERE id = " + id);

            statement.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return  "id=" + getId() +
                ", Nom ='" + getNom() + '\'' +
                ", Numéro de sécu ='" + getNumeroSecu() + '\'' +
                ", Nombres de cours =" + getNombreCours() +
                ", Spécialité ='" + getSpecialite() +
                ", Nombre de vacations =" + getNombreVacations();
    }
    //endregion
}
