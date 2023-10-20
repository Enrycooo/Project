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

    //region getVendeurById
    public static Vendeur getVendeurById(int id) {
        Vendeur vendeur = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT vendeur.*, personne.nom, personne.numero_secu FROM vendeur JOIN personne ON vendeur.id_personne = personne.id WHERE vendeur.id ="+id);

            if (resultSet.next()) {
                vendeur = new Vendeur();
                vendeur.setId(resultSet.getInt("id"));
                vendeur.setNom(resultSet.getString("nom"));
                vendeur.setNumeroSecu(resultSet.getString("numero_secu"));
                vendeur.setAnciennete(resultSet.getInt("anciennete"));
                vendeur.setNomDuStand(resultSet.getString("nom_du_stand"));
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendeur;
    }
    //endregion

    //region getAllVendeurs
    public static List<Vendeur> getAllVendeurs() {
        List<Vendeur> vendeurs = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT vendeur.*, personne.nom, personne.numero_secu FROM vendeur JOIN personne ON vendeur.id_personne = personne.id");

            while (resultSet.next()) {
                Vendeur vendeur = new Vendeur();
                vendeur.setId(resultSet.getInt("id"));
                vendeur.setNom(resultSet.getString("nom"));
                vendeur.setNumeroSecu(resultSet.getString("numero_secu"));
                vendeur.setAnciennete(resultSet.getInt("anciennete"));
                vendeur.setNomDuStand(resultSet.getString("nom_du_stand"));
                vendeurs.add(vendeur);
            }

            statement.close();
            resultSet.close();
            connection.close();
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vendeurs;
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

    //region Update
    public static void update(Vendeur vendeur) {

        try {
            Connection connection = DatabaseConnection.getConnection();

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE personne SET nom = '" + vendeur.getNom() + "', numero_secu = '" + vendeur.getNumeroSecu() + "' WHERE id = " + vendeur.getId());
            statement.executeUpdate("UPDATE vendeur SET anciennete = '" + vendeur.getAnciennete() + "', nom_du_stand = '" + vendeur.getNomDuStand() + "' WHERE id = " + vendeur.getId());

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
            statement.executeUpdate("DELETE FROM vendeur WHERE id = " + id);

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
                ", Ancienneté =" + anciennete +
                ", Nom du stand ='" + nomDuStand + '\'';
    }
    //endregion
}
