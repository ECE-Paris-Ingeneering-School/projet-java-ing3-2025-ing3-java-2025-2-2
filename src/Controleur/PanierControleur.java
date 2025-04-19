package Controleur;

import dao.PanierDAO;
import Modele.Article;
import Modele.Panier;

import java.util.Map;

public class PanierControleur {

    private Panier panier;

    public PanierControleur() {
        this.panier = Panier.getInstance(); // Utilisation du panier singleton
    }

    // Ajouter un article au panier
    public void ajouterArticle(int idClient, Article article, int quantite) {
        // Appel à la méthode de PanierDAO pour ajouter l'article à la base de données
        PanierDAO.ajouterArticle(idClient, article.getIdArticle(), quantite);

        // Ajouter l'article au panier local (si ce n'est pas déjà fait)
        panier.ajouterArticle(article, quantite);
    }

    // Récupérer le panier d'un client depuis la base de données et l'afficher
    public Map<Article, Integer> getPanier(int idClient) {
        // Récupère le panier depuis la base de données via le DAO
        return PanierDAO.getPanier(idClient);
    }

    // Vider le panier (sur la base de données et localement)
    public void viderPanier(int idClient) {
        // Vider le panier local
        panier.viderPanier();

        // Vider également le panier sur la base de données via le DAO
        PanierDAO.viderPanier(idClient);
    }

    // Mettre à jour le panier avec les nouvelles quantités (si l'article existe déjà)
    public void mettreAJourPanier(int idClient, Article article, int nouvelleQuantite) {
        // Met à jour l'article dans le panier (si l'article est déjà présent, la quantité est mise à jour)
        PanierDAO.ajouterArticle(idClient, article.getIdArticle(), nouvelleQuantite);

        // Met à jour le panier local en fonction des modifications
        panier.ajouterArticle(article, nouvelleQuantite);
    }
}
