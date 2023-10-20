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

    //region getAllEnseignants
    public static List<Enseignant> getAllEnseignants() {
        List<Enseignant> enseignants = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT enseignant.*, personne.nom, personne.numero_secu FROM enseignant JOIN personne ON enseignant.id_personne = personne.id");

            while (resultSet.next()) {
                Enseignant enseignant = new Enseignant();
                enseignant.setId(resultSet.getInt("id"));
                enseignant.setNom(resultSet.getString("nom"));
                enseignant.setNumeroSecu(resultSet.getString("numero_secu"));
                enseignant.setNombreCours(resultSet.getInt("nombre_cours"));
                enseignant.setSpecialite(resultSet.getString("specialite"));
                enseignants.add(enseignant);
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return enseignants;
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

    //region getEnseignantById
    public static Enseignant getEnseignantById(int id) {
        Enseignant enseignant = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT enseignant.*, personne.nom, personne.numero_secu FROM enseignant JOIN personne ON enseignant.id_personne = personne.id WHERE enseignant.id ="+id);

            if (resultSet.next()) {
                enseignant = new Enseignant();
                enseignant.setId(resultSet.getInt("id"));
                enseignant.setNom(resultSet.getString("nom"));
                enseignant.setNumeroSecu(resultSet.getString("numero_secu"));
                enseignant.setNombreCours(resultSet.getInt("nombre_cours"));
                enseignant.setSpecialite(resultSet.getString("specialite"));
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enseignant;
    }
    //endregion

    //region Update
    public static void update(Enseignant enseignant) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE personne SET nom = '" + enseignant.getNom() + "', numero_secu = '" + enseignant.getNumeroSecu() + "' WHERE id = " + enseignant.getId());
            statement.executeUpdate("UPDATE enseignant SET nombre_cours = '" + enseignant.getNombreCours() + "', specialite = '" + enseignant.getSpecialite() + "' WHERE id = " + enseignant.getId());

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
            statement.executeUpdate("DELETE FROM enseignant WHERE id = " + id);

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
                ", Nombres de cours =" + nombreCours +
                ", Spécialité ='" + specialite + '\'';
    }
    //endregion
}
