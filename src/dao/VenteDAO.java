package dao;

import java.sql.*;

public class VenteDAO {

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
