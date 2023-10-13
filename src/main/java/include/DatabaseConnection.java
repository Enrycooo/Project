package include;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/projet_revision";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection connection;

    private DatabaseConnection() {
    }
    //region Connexion BDD
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erreur lors de la connexion à la base de données.");
                    }
                }
            }
        }
        return connection;
    }
    //endregion
    //region closeConnection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion
}
