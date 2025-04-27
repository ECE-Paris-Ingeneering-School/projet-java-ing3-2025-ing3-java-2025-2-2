package Controleur;

import dao.PanierDAO;
import Modele.Article;
import Modele.Panier;
import java.util.Map;

/**
 * Classe PanierControleur
 * Sert d'intermédiaire entre la vue et le modèle pour la gestion du panier
 * Permet de modifier, vider et récupérer les articles du panier
 * Source : <a href="https://grafikart.fr/tutoriels/mvc-model-view-controller-574">Grafikart - Comprendre le modèle MVC</a>
 * @author Jean
 */
public class PanierControleur {

    private Panier panier;

    /**
     * Constructeur de PanierControleur
     * Initialise le panier local via le Singleton
     */
    public PanierControleur() {
        this.panier = Panier.getInstance();
    }

    /**
     * Ajoute un article au panier du client
     * Met à jour à la fois en local et dans la base de données
     * @param idClient identifiant du client
     * @param article article à ajouter
     * @param quantite quantité d'articles à ajouter
     */
    public void ajouterArticle(int idClient, Article article, int quantite) {
        PanierDAO.ajouterArticle(idClient, article.getIdArticle(), quantite);
        panier.ajouterArticle(article, quantite);
    }

    /**
     * Récupère le panier d'un client depuis la base de données
     * @param idClient identifiant du client
     * @return map des articles et leur quantité
     */
    public Map<Article, Integer> getPanier(int idClient) {
        return PanierDAO.getPanier(idClient);
    }

    /**
     * Vide complètement le panier du client, localement et dans la base de données
     * @param idClient identifiant du client
     */
    public void viderPanier(int idClient) {
        panier.viderPanier();
        PanierDAO.viderPanier(idClient);
    }

    /**
     * Met à jour la quantité d'un article dans le panier du client
     * @param idClient identifiant du client
     * @param article article concerné
     * @param nouvelleQuantite nouvelle quantité souhaitée
     */
    public void mettreAJourPanier(int idClient, Article article, int nouvelleQuantite) {
        PanierDAO.ajouterArticle(idClient, article.getIdArticle(), nouvelleQuantite);
        panier.ajouterArticle(article, nouvelleQuantite);
    }
}
