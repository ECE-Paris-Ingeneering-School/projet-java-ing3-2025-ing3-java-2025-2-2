package dao;

import Modele.Article;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    public List<Article> listerArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id_article, a.nom, a.description, a.prix_unitaire, a.prix_vrac, a.quantite_vrac, m.nom AS marque " +
                "FROM Article a LEFT JOIN Marque m ON a.id_marque = m.id_marque";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac"),
                        rs.getString("marque")
                );
                articles.add(article);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }

        return articles;
    }
}
