package dao;

import Modele.Administrateur;
import java.sql.*;

/**
 * Classe AdministrateurDAO
 * Gère l'accès aux données concernant les administrateurs dans la base de données
 * Suivant le pattern DAO (Data Access Object) pour séparer la logique d'accès aux données
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Alice
 */
public class AdministrateurDAO {

    private Connection connection;

    /**
     * Constructeur de AdministrateurDAO
     * Initialise l'accès à la base de données avec une connexion existante
     * @param connection la connexion JDBC active
     */
    public AdministrateurDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Permet de connecter un administrateur en vérifiant l'email et le mot de passe
     * @param email email de l'administrateur
     * @param motDePasse mot de passe de l'administrateur
     * @return un objet Administrateur si la connexion est réussie, sinon null
     */
    public Administrateur seConnecter(String email, String motDePasse) {
        String query = "SELECT * FROM administrateur WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Administrateur(
                        rs.getInt("id_admin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
