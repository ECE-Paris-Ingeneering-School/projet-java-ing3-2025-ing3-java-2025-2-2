package dao;

import Modele.Article;
import Modele.Marque;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe PanierDAO
 * Gère l'accès aux données pour les opérations liées aux paniers clients
 * Implémente l'ajout, la récupération et la suppression d'articles dans un panier
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Martin
 */
public class PanierDAO {

    /**
     * Ajoute un article au panier du client.
     * Si le panier n'existe pas, il est créé.
     * Si l'article existe déjà dans le panier, la quantité est incrémentée.
     * @param idClient identifiant du client
     * @param idArticle identifiant de l'article
     * @param quantite quantité à ajouter
     */
    public static void ajouterArticle(int idClient, int idArticle, int quantite) {
        try (Connection conn = ConnexionBDD.getConnexion()) {
            int idPanier = -1;
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT id_panier FROM panier WHERE id_client = ?"
            );
            checkStmt.setInt(1, idClient);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                idPanier = rs.getInt("id_panier");
            } else {
                PreparedStatement insertPanier = conn.prepareStatement(
                        "INSERT INTO panier (id_client, date_creation) VALUES (?, NOW())",
                        Statement.RETURN_GENERATED_KEYS
                );
                insertPanier.setInt(1, idClient);
                insertPanier.executeUpdate();

                ResultSet generatedKeys = insertPanier.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idPanier = generatedKeys.getInt(1);
                }
            }

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO panier_article (id_panier, id_article, quantite) " +
                            "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantite = quantite + ?"
            );
            stmt.setInt(1, idPanier);
            stmt.setInt(2, idArticle);
            stmt.setInt(3, quantite);
            stmt.setInt(4, quantite);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère le contenu du panier pour un client donné
     * @param idClient identifiant du client
     * @return une map d'articles et leur quantité respective
     */
    public static Map<Article, Integer> getPanier(int idClient) {
        Map<Article, Integer> panier = new HashMap<>();
        try (Connection conn = ConnexionBDD.getConnexion()) {
            String sqlPanier = "SELECT id_panier FROM panier WHERE id_client = ?";
            PreparedStatement stmtPanier = conn.prepareStatement(sqlPanier);
            stmtPanier.setInt(1, idClient);

            ResultSet rsPanier = stmtPanier.executeQuery();
            if (rsPanier.next()) {
                int idPanier = rsPanier.getInt("id_panier");

                String sqlArticles = "SELECT a.*, pa.quantite " +
                        "FROM panier_article pa " +
                        "JOIN article a ON pa.id_article = a.id_article " +
                        "WHERE pa.id_panier = ?";
                PreparedStatement stmtArticles = conn.prepareStatement(sqlArticles);
                stmtArticles.setInt(1, idPanier);

                ResultSet rsArticles = stmtArticles.executeQuery();
                while (rsArticles.next()) {
                    Article a = new Article(
                            rsArticles.getInt("id_article"),
                            rsArticles.getString("nom"),
                            rsArticles.getString("description"),
                            rsArticles.getDouble("prix_unitaire"),
                            rsArticles.getDouble("prix_vrac"),
                            rsArticles.getInt("quantite_vrac"),
                            new Marque(rsArticles.getInt("id_marque"), rsArticles.getString("nom")),
                            rsArticles.getString("photo")
                    );
                    panier.put(a, rsArticles.getInt("quantite"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panier;
    }

    /**
     * Vide le panier du client spécifié
     * @param idClient identifiant du client
     */
    public static void viderPanier(int idClient) {
        try (Connection conn = ConnexionBDD.getConnexion()) {
            PreparedStatement stmtPanier = conn.prepareStatement(
                    "SELECT id_panier FROM panier WHERE id_client = ?"
            );
            stmtPanier.setInt(1, idClient);
            ResultSet rs = stmtPanier.executeQuery();

            if (rs.next()) {
                int idPanier = rs.getInt("id_panier");

                PreparedStatement stmtDelete = conn.prepareStatement(
                        "DELETE FROM panier_article WHERE id_panier = ?"
                );
                stmtDelete.setInt(1, idPanier);
                stmtDelete.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
