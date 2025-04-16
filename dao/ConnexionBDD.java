package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de gestion de la connexion à la base de données
 */
public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connexion = null;

    // Constructeur privé pour empêcher l'instanciation
    private ConnexionBDD() {}

    /**
     * Obtient une connexion à la base de données
     * @return Connection objet de connexion
     * @throws SQLException en cas d'erreur de connexion
     */
    public static Connection getConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC non trouvé", e);
            }
        }
        return connexion;
    }

    /**
     * Ferme la connexion à la base de données
     */
    public static void fermerConnexion() {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
            }
        }
    }
}