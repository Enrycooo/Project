package Class;
import include.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Personne {
    private int id;
    private String nom;
    private String numeroSecu;

    //region Constructor
    public Personne(String nom, String numeroSecu) {
        this.nom = nom;
        this.numeroSecu = numeroSecu;
    }
    public Personne(int id, String nom, String numeroSecu){
        this.id = id;
        this.numeroSecu = numeroSecu;
        this.nom = nom;
    }

    public Personne() {

    }

    //endregion

    //region Getter | Setter
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

    //region getLastInsertId
    public static void getLastInsertId(){
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("SELECT LAST_INSERT_ID()");

            statement.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
