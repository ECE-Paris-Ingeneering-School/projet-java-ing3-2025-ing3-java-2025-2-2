package Modele;

import java.util.HashMap;
import java.util.Map;
import dao.PanierDAO;

public class Panier {
    private static Panier instance; // Singleton pour garantir une seule instance du panier
    private Map<Article, Integer> articles; // Cartographie des articles et de leur quantité

    // Constructeur privé pour empêcher l'instanciation directe
    private Panier() {
        articles = new HashMap<>();
    }

    // Méthode statique pour accéder à l'instance unique du panier
    public static Panier getInstance() {
        if (instance == null) {
            instance = new Panier();
        }
        return instance;
    }
    public void chargerDepuisBase(int idClient) {
        PanierDAO panierDAO = new PanierDAO();
        articles = panierDAO.getPanier(idClient); // Charge les articles du panier pour le client
    }

    // Ajouter un article au panier
    public void ajouterArticle(Article article, int quantite) {
        // Si l'article est déjà dans le panier, on met à jour sa quantité
        articles.put(article, articles.getOrDefault(article, 0) + quantite);
    }

    // Retirer un article du panier
    public void retirerArticle(Article article) {
        articles.remove(article);
    }

    // Obtenir tous les articles du panier
    public Map<Article, Integer> getArticles() {
        return articles;
    }
    public void setArticles(Map<Article, Integer> articles) {
        this.articles = articles;
    }


    // Vider le panier
    public void viderPanier() {
        articles.clear();
    }

    // Calculer le total du panier (somme des prix * quantités des articles)
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
            total += entry.getKey().getPrix_unitaire() * entry.getValue();
        }
        return total;
    }

    // Vérifier si le panier est vide
    public boolean estVide() {
        return articles.isEmpty();
    }

    // Mettre à jour la quantité d'un article dans le panier
    public void mettreAJourQuantite(Article article, int nouvelleQuantite) {
        if (articles.containsKey(article)) {
            articles.put(article, nouvelleQuantite);
        }
    }
}
