package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe ConnexionBDD
 * Gère la connexion unique à la base de données MySQL pour toute l'application
 * Implémente un modèle Singleton pour assurer une seule connexion active
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Jean
 */
public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connexion = null;

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe
     */
    private ConnexionBDD() {}

    /**
     * Obtient une connexion active à la base de données
     * Si aucune connexion n'existe, une nouvelle est créée
     * @return objet Connection vers la base de données
     * @throws SQLException si la connexion échoue
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
     * Ferme la connexion active à la base de données
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
