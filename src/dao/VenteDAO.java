package dao;

import java.sql.*;

/**
 * Classe VenteDAO
 * Gère l'accès aux données pour le suivi des ventes d'articles
 * Permet de récupérer la quantité totale vendue pour un article donné
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Quentin
 */
public class VenteDAO {

    /**
     * Récupère la quantité totale vendue pour un article spécifique
     * @param idArticle identifiant de l'article
     * @return quantité vendue de l'article
     */
    public int getQuantiteVendue(int idArticle) {
        int quantiteVendue = 0;
        String sql = "SELECT SUM(quantite) AS quantite_vendue FROM commande_article WHERE id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idArticle);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    quantiteVendue = rs.getInt("quantite_vendue");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des ventes : " + e.getMessage());
        }

        return quantiteVendue;
    }
}
