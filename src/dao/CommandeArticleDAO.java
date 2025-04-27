package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modele.Article;

/**
 * Classe CommandeArticleDAO
 * Gère les opérations entre les commandes et les articles (relation commande-article)
 * Implémente les actions CRUD sur la table de liaison commande_article
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Quentin
 */
public class CommandeArticleDAO {

    /**
     * Insère une ligne dans la table commande_article
     * @param idCommande identifiant de la commande
     * @param article article concerné
     * @param quantite quantité commandée de l'article
     * @param prixTotal prix total pour cette ligne (quantité * prix unitaire ou vrac)
     */
    public void insererCommandeArticle(int idCommande, Article article, int quantite, double prixTotal) {
        String sql = "INSERT INTO commande_article (id_commande, id_article, quantite, prix_total) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCommande);
            stmt.setInt(2, article.getIdArticle());
            stmt.setInt(3, quantite);
            stmt.setDouble(4, prixTotal);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les articles associés à une commande spécifique
     * @param idCommande identifiant de la commande
     * @return liste d'objets Article présents dans la commande
     */
    public List<Article> getArticlesParCommande(int idCommande) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.*, ca.quantite FROM commande_article ca " +
                "JOIN article a ON ca.id_article = a.id_article " +
                "WHERE ca.id_commande = ?";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id_article"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac"),
                        null,
                        rs.getString("photo")
                );
                articles.add(article);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
