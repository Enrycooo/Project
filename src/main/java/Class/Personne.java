package Class;
import include.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Personne {

    private int id;
    private String nom;
    private String numeroSecu;

    //region Constructeur
    public Personne(int id, String nom, String numeroSecu) {
        this.nom = nom;
        this.numeroSecu = numeroSecu;
        this.id = id;
    }

    public Personne() {

    }

    //endregion

    //region Getter | Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroSecu() {
        return numeroSecu;
    }

    public void setNumeroSecu(String numeroSecu) {
        this.numeroSecu = numeroSecu;
    }
    //endregion

    //region getAllPersonnes
    public static List<Personne> getAllPersonnes() {
        List<Personne> personnes = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM personne");

            while (resultSet.next()) {
                Personne personne = new Personne();
                personne.setId(resultSet.getInt("id"));
                personne.setNom(resultSet.getString("nom"));
                personne.setNumeroSecu(resultSet.getString("numero_secu"));
                personnes.add(personne);
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personnes;
    }
    //endregion

    //region getPersonneById
    public static Personne getPersonneById(int id) {
        Personne personne = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM personne WHERE id = " + id);

            if (resultSet.next()) {
                personne = new Personne();
                personne.setId(id);
                personne.setNom(resultSet.getString("nom"));
                personne.setNumeroSecu(resultSet.getString("numero_secu"));
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personne;
    }
    //endregion

    //region create
    public static void create(Personne personne) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO personne (nom, numero_secu) VALUES ('" + personne.getNom() + "', '" + personne.getNumeroSecu() + "')");

            statement.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region update
    public static void update(Personne personne) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE personne SET nom = '" + personne.getNom() + "', numero_secu = '" + personne.getNumeroSecu() + "' WHERE id = " + personne.getId());

            statement.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region delete
    public static void delete(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM personne WHERE id = " + id);

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
        return  "Nom='" + nom + '\'' +
                ", Numéro de sécu='" + numeroSecu + '\'';
    }
    //endregion
}
