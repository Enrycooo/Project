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
    public static void create(Avocat avocat) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO avocat (nombre_affaires, adresse_cabinet, id_personne) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, avocat.getNombreAffaires());
            preparedStatement.setString(2, avocat.getAdresseCabinet());

            // Exécution de la requête d'insertion
            int lignesAffectees = preparedStatement.executeUpdate();

            if (lignesAffectees > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int lastInsertId = resultSet.getInt(1);
                    preparedStatement.close();
                    resultSet.close();
                    connection.close();
                    DatabaseConnection.closeConnection();

                    System.out.println("Dernière ID insérée : " + lastInsertId);
                }
            } else {
                preparedStatement.close();
                connection.close();
                DatabaseConnection.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
