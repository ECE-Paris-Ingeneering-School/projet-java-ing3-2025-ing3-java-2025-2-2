package dao;

import Modele.Article;
import Modele.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ArticleDAO
 * Gère toutes les opérations d'accès aux données pour les articles du catalogue
 * Suivant le pattern DAO pour séparer l'accès aux données du reste de l'application
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Jean
 */
public class ArticleDAO {

    /**
     * Récupère tous les articles présents dans la base de données
     * @return une liste d'objets Article
     */
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

    /**
     * Récupère tous les articles associés à une marque spécifique
     * @param idMarque identifiant de la marque
     * @return une liste d'articles correspondant à la marque
     */
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

    /**
     * Recherche des articles par mot-clé dans leur nom, description ou marque
     * @param query chaîne de recherche
     * @return une liste d'articles correspondants
     */
    public List<Article> rechercherArticles(String query) {
        List<Article> articles = new ArrayList<>();
        String searchQuery = "%" + query + "%";

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

    /**
     * Ajoute un nouvel article à la base de données
     * @param article l'article à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
    public boolean ajouterArticle(Article article) {
        String sql = "INSERT INTO article (nom, description, prix_unitaire, prix_vrac, quantite_vrac, id_marque, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, article.getNom());
            stmt.setString(2, article.getDescription());
            stmt.setDouble(3, article.getPrix_unitaire());
            stmt.setDouble(4, article.getPrix_vrac());
            stmt.setInt(5, article.getQte_vrac());
            stmt.setInt(6, article.getMarque().getIdMarque());
            stmt.setString(7, article.getPhoto());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifie un article existant dans la base de données
     * @param article l'article modifié
     * @return true si la modification est réussie, false sinon
     */
    public boolean modifierArticle(Article article) {
        String sql = "UPDATE article SET nom = ?, description = ?, prix_unitaire = ?, prix_vrac = ?, quantite_vrac = ?, id_marque = ?, photo = ? WHERE id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, article.getNom());
            stmt.setString(2, article.getDescription());
            stmt.setDouble(3, article.getPrix_unitaire());
            stmt.setDouble(4, article.getPrix_vrac());
            stmt.setInt(5, article.getQte_vrac());
            stmt.setInt(6, article.getMarque().getIdMarque());
            stmt.setString(7, article.getPhoto());
            stmt.setInt(8, article.getIdArticle());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un article de la base de données par son identifiant
     * @param idArticle identifiant de l'article à supprimer
     * @return true si la suppression est réussie, false sinon
     */
    public boolean supprimerArticle(int idArticle) {
        String sql = "DELETE FROM article WHERE id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idArticle);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'article : " + e.getMessage());
            return false;
        }
    }

    /**
     * Trouve un article en fonction de son identifiant
     * @param idArticle identifiant de l'article recherché
     * @return l'objet Article correspondant, ou null si non trouvé
     */
    public Article trouverParId(int idArticle) {
        String sql = "SELECT a.*, m.nom AS nom_marque FROM article a LEFT JOIN marque m ON a.id_marque = m.id_marque WHERE a.id_article = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idArticle);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Marque marque = new Marque(rs.getInt("id_marque"), rs.getString("nom_marque"));
                return new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac"),
                        marque,
                        rs.getString("photo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'article : " + e.getMessage());
        }
        return null;
    }
}
