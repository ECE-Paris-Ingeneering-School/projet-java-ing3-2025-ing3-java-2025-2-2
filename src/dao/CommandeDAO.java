package dao;

import Modele.Article;
import Modele.Commande;
import Modele.CommandeArticle;
import Modele.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe CommandeDAO
 * Gère l'accès aux données concernant les commandes et les lignes de commande
 * Suivant le modèle DAO pour une meilleure séparation de responsabilités
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Alice
 */
public class CommandeDAO {

    /**
     * Récupère toutes les commandes passées par un client
     * @param idClient identifiant du client
     * @return liste de commandes associées à ce client
     */
    public List<Commande> getCommandesParClient(int idClient) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE id_client = ? ORDER BY date_commande DESC";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Commande cmd = new Commande(
                        rs.getInt("id_commande"),
                        null,
                        rs.getTimestamp("date_commande").toLocalDateTime(),
                        rs.getDouble("total")
                );
                cmd.setArticles(getCommandeArticles(cmd.getIdCommande(), conn));
                commandes.add(cmd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    /**
     * Récupère tous les articles associés à une commande donnée
     * @param idCommande identifiant de la commande
     * @param conn connexion active à la base de données
     * @return liste des articles et quantités associés à la commande
     */
    private List<CommandeArticle> getCommandeArticles(int idCommande, Connection conn) {
        List<CommandeArticle> articles = new ArrayList<>();
        String sql = "SELECT a.*, ca.quantite, ca.prix_total " +
                "FROM commande_article ca " +
                "JOIN article a ON ca.id_article = a.id_article " +
                "WHERE ca.id_commande = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
                        new Marque(rs.getInt("id_marque"), rs.getString("nom")),
                        rs.getString("photo")
                );
                int quantite = rs.getInt("quantite");
                double prixTotal = rs.getDouble("prix_total");

                CommandeArticle commandeArticle = new CommandeArticle(article, quantite, prixTotal);
                articles.add(commandeArticle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }

    /**
     * Valide une commande en enregistrant la commande et ses articles en base de données
     * @param idClient identifiant du client
     * @param articles articles commandés avec leur quantité
     * @param total montant total de la commande
     * @return true si la commande a été enregistrée avec succès, false sinon
     */
    public boolean validerCommande(int idClient, Map<Article, Integer> articles, double total) {
        try (Connection conn = ConnexionBDD.getConnexion()) {
            String sqlCommande = "INSERT INTO commande (id_client, date_commande, total) VALUES (?, NOW(), ?)";
            PreparedStatement stmtCommande = conn.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS);
            stmtCommande.setInt(1, idClient);
            stmtCommande.setDouble(2, total);
            stmtCommande.executeUpdate();

            ResultSet rs = stmtCommande.getGeneratedKeys();
            if (rs.next()) {
                int idCommande = rs.getInt(1);

                String sqlArticle = "INSERT INTO commande_article (id_commande, id_article, quantite, prix_total) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtArticle = conn.prepareStatement(sqlArticle);

                for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
                    Article article = entry.getKey();
                    int quantite = entry.getValue();
                    double prix = article.calculerPrixTotal(quantite);

                    stmtArticle.setInt(1, idCommande);
                    stmtArticle.setInt(2, article.getIdArticle());
                    stmtArticle.setInt(3, quantite);
                    stmtArticle.setDouble(4, prix);
                    stmtArticle.addBatch();
                }

                stmtArticle.executeBatch();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
