package Class;
import include.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    //region getAllAvocats
    public static List<Avocat> getAllAvocats() {
        List<Avocat> avocats = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT avocat.*, personne.nom, personne.numero_secu FROM avocat JOIN personne ON avocat.id = personne.id");

            while (resultSet.next()) {
                Avocat avocat = new Avocat();
                avocat.setId(resultSet.getInt("id"));
                avocat.setNom(resultSet.getString("nom"));
                avocat.setNumeroSecu(resultSet.getString("numero_secu"));
                avocat.setAdresseCabinet(resultSet.getString("adresse_cabinet"));
                avocat.setNombreAffaires(resultSet.getInt("nombre_affaires"));
                avocats.add(avocat);
            }

            statement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return avocats;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return  "id=" + getId() +
                ", Nom ='" + getNom() + '\'' +
                ", Numéro de sécu ='" + getNumeroSecu() + '\'' +
                ", Nombres d'affaires =" + nombreAffaires +
                ", Adresse du cabinet ='" + adresseCabinet + '\'';
    }
    //endregion

}