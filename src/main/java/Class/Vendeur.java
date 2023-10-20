package Class;

import include.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vendeur extends Personne {

    private int anciennete;
    private String nomDuStand;
    private boolean permanent;
    private int numeroBureau;

    //region Constructeur
    public Vendeur(int id, String nom, String numeroSecu, int anciennete, String nomDuStand) {
        super(id, nom, numeroSecu);
        this.anciennete = anciennete;
        this.nomDuStand = nomDuStand;
    }

    public Vendeur() {

    }
    //endregion

    //region Getter | Setter
    public int getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(int anciennete) {
        this.anciennete = anciennete;
    }

    public String getNomDuStand() {
        return nomDuStand;
    }

    public void setNomDuStand(String nomDuStand) {
        this.nomDuStand = nomDuStand;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public int getNumeroBureau() {
        return numeroBureau;
    }

    public void setNumeroBureau(int numeroBureau) {
        this.numeroBureau = numeroBureau;
    }
    //endregion

    //region Create
    public static void create(String nom, String numeroSecu, int anciennete, String nomDuStand) {
        try {
            // Insère un vendeur dans la table Personne
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

            PreparedStatement enseignant = connection.prepareStatement("INSERT INTO Vendeur (anciennete, nom_du_stand, id_personne) VALUES (?, ?, ?)");
            enseignant.setInt(1, anciennete);
            enseignant.setString(2, nomDuStand);
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
