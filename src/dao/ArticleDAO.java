package dao;

import Modele.Article;
import Modele.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    public List<Article> listerArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id_article, a.nom, a.description, a.prix_unitaire, a.prix_vrac, a.quantite_vrac, a.photo, m.id_marque, m.nom AS marque " +
                "FROM Article a LEFT JOIN Marque m ON a.id_marque = m.id_marque";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Marque marque = new Marque(rs.getInt("id_marque"), rs.getString("marque"));
                Article article = new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac"),
                        marque,
                        rs.getString("photo")
                );
                articles.add(article);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des articles : " + e.getMessage());
        }

        return articles;
    }

    public List<Article> getArticlesParMarque(int idMarque) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id_article, a.nom, a.description, a.prix_unitaire, a.prix_vrac, a.quantite_vrac, a.photo, m.id_marque, m.nom AS marque " +
                "FROM Article a LEFT JOIN Marque m ON a.id_marque = m.id_marque WHERE a.id_marque = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMarque);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Marque marque = new Marque(rs.getInt("id_marque"), rs.getString("marque"));
                    Article article = new Article(
                            rs.getInt("id_article"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix_unitaire"),
                            rs.getDouble("prix_vrac"),
                            rs.getInt("quantite_vrac"),
                            marque,
                            rs.getString("photo")
                    );
                    articles.add(article);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur chargement des articles par marque : " + e.getMessage());
        }

        return articles;
    }

    public List<Article> rechercherArticles(String query) {
        List<Article> articles = new ArrayList<>();
        String searchQuery = "%" + query + "%";  // Utiliser des jokers pour faire correspondre n'importe quel texte

        String sql = "SELECT a.id_article, a.nom, a.description, a.prix_unitaire, a.prix_vrac, a.quantite_vrac, a.photo, m.id_marque, m.nom AS marque " +
                "FROM Article a LEFT JOIN Marque m ON a.id_marque = m.id_marque " +
                "WHERE a.nom LIKE ? OR a.description LIKE ? OR m.nom LIKE ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            stmt.setString(3, searchQuery);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Marque marque = new Marque(rs.getInt("id_marque"), rs.getString("marque"));
                    Article article = new Article(
                            rs.getInt("id_article"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix_unitaire"),
                            rs.getDouble("prix_vrac"),
                            rs.getInt("quantite_vrac"),
                            marque,
                            rs.getString("photo")
                    );
                    articles.add(article);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
