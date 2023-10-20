package Class;
import include.DatabaseConnection;

import java.sql.*;

public class Avocat extends Personne {
    private int nombreAffaires;
    private String adresseCabinet;

    //region Constructeur
    public Avocat(int id, String nom, String numeroSecu, int nombreAffaires, String adresseCabinet) {
        super(id, nom, numeroSecu);
        this.nombreAffaires = nombreAffaires;
        this.adresseCabinet = adresseCabinet;
    }

    public Avocat() {
    }

    //endregion

    //region Getter | Setter


    public int getNombreAffaires() {
        return nombreAffaires;
    }

    public void setNombreAffaires(int nombreAffaires) {
        this.nombreAffaires = nombreAffaires;
    }

    public String getAdresseCabinet() {
        return adresseCabinet;
    }

    public void setAdresseCabinet(String adresseCabinet) {
        this.adresseCabinet = adresseCabinet;
    }

    //endregion
    //region create
    public static void create(String nom, String numeroSecu, int nbrAffaires, String adresseCabinet) {
        try {
            // Insère un avocat dans la table Personne
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

            PreparedStatement enseignant = connection.prepareStatement("INSERT INTO Avocat (nombre_affaires, adresse_cabinet, id_personne) VALUES (?, ?, ?)");
            enseignant.setInt(1, nbrAffaires);
            enseignant.setString(2, adresseCabinet);
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
