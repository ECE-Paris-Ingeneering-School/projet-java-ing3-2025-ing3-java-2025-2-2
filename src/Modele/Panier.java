package Modele;

import java.util.HashMap;
import java.util.Map;
import dao.PanierDAO;

/**
 * Classe Panier
 * Implémente un panier d'achats pour un client sous forme de Singleton
 * Permet l'ajout, la suppression, la mise à jour et la récupération des articles
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Martin
 */
public class Panier {
    private static Panier instance;
    private Map<Article, Integer> articles;

    /**
     * Constructeur privé pour garantir le modèle Singleton
     */
    private Panier() {
        articles = new HashMap<>();
    }

    /**
     * Retourne l'unique instance du Panier
     * @return instance unique de Panier
     */
    public static Panier getInstance() {
        if (instance == null) {
            instance = new Panier();
        }
        return instance;
    }

    /**
     * Charge les articles du panier d'un client depuis la base de données
     * @param idClient identifiant du client
     */
    public void chargerDepuisBase(int idClient) {
        PanierDAO panierDAO = new PanierDAO();
        articles = panierDAO.getPanier(idClient);
    }

    /**
     * Ajoute un article au panier avec une quantité spécifiée
     * @param article article à ajouter
     * @param quantite quantité à ajouter
     */
    public void ajouterArticle(Article article, int quantite) {
        articles.put(article, articles.getOrDefault(article, 0) + quantite);
    }

    /**
     * Retire un article du panier
     * @param article article à retirer
     */
    public void retirerArticle(Article article) {
        articles.remove(article);
    }

    /**
     * Retourne les articles présents dans le panier
     * @return map des articles et de leur quantité
     */
    public Map<Article, Integer> getArticles() {
        return articles;
    }

    /**
     * Définit manuellement les articles du panier
     * @param articles nouvelle map d'articles
     */
    public void setArticles(Map<Article, Integer> articles) {
        this.articles = articles;
    }

    /**
     * Vide entièrement le panier
     */
    public void viderPanier() {
        articles.clear();
    }

    /**
     * Calcule le total du panier en fonction des prix unitaires et des quantités
     * @return montant total du panier
     */
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
            total += entry.getKey().getPrix_unitaire() * entry.getValue();
        }
        return total;
    }

    /**
     * Vérifie si le panier est vide
     * @return true si le panier est vide, false sinon
     */
    public boolean estVide() {
        return articles.isEmpty();
    }

    /**
     * Met à jour la quantité d'un article dans le panier
     * @param article article concerné
     * @param nouvelleQuantite nouvelle quantité
     */
    public void mettreAJourQuantite(Article article, int nouvelleQuantite) {
        if (articles.containsKey(article)) {
            articles.put(article, nouvelleQuantite);
        }
    }
}
