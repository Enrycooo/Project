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

    //region getAllPermanents
    public static List<Permanent> getAllPermanents() {
        List<Permanent> permanents = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT permanent.*, enseignant.nombre_cours, enseignant.specialite, personne.nom, personne.numero_secu FROM permanent INNER JOIN enseignant ON permanent.id_enseignant = enseignant.id INNER JOIN personne ON personne.id = enseignant.id_personne");

            while (resultSet.next()) {
                Permanent permanent = new Permanent();
                permanent.setId(resultSet.getInt("id"));
                permanent.setNom(resultSet.getString("nom"));
                permanent.setNumeroSecu(resultSet.getString("numero_secu"));
                permanent.setNombreCours(resultSet.getInt("nombre_cours"));
                permanent.setSpecialite(resultSet.getString("specialite"));
                permanent.setNumeroBureau(resultSet.getInt("numero_bureau"));
                permanents.add(permanent);
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return permanents;
    }
    //endregion

    //region getPermanentById
    public static Permanent getPermanentById(int id) {
        Permanent permanent = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT permanent.*, enseignant.nombre_cours, enseignant.specialite, personne.nom, personne.numero_secu FROM permanent INNER JOIN enseignant ON permanent.id_enseignant = enseignant.id INNER JOIN personne ON personne.id = enseignant.id_personne WHERE permanent.id =" +id);

            if (resultSet.next()) {
                permanent = new Permanent();
                permanent.setId(resultSet.getInt("id"));
                permanent.setNom(resultSet.getString("nom"));
                permanent.setNumeroSecu(resultSet.getString("numero_secu"));
                permanent.setNombreCours(resultSet.getInt("nombre_cours"));
                permanent.setSpecialite(resultSet.getString("specialite"));
                permanent.setNumeroBureau(resultSet.getInt("numero_bureau"));
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permanent;
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

    //region Update
    public static void update(Permanent permanent) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE personne SET nom = '" + permanent.getNom() + "', numero_secu = '" + permanent.getNumeroSecu() + "' WHERE id = " + permanent.getId());
            statement.executeUpdate("UPDATE enseignant SET nombre_cours = '" + permanent.getNombreCours() + "', specialite = '" + permanent.getSpecialite() + "' WHERE id = " + permanent.getId());
            statement.executeUpdate("UPDATE permanent SET numero_bureau = '" + permanent.numeroBureau + "' WHERE id = " + permanent.getId());

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
            statement.executeUpdate("DELETE FROM permanent WHERE id = " + id);

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
                ", Numéro de bureau =" + getNumeroBureau();
    }
    //endregion
}