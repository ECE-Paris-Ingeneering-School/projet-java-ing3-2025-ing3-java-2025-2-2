package Modele;

import java.util.HashMap;
import java.util.Map;

public class Panier {
    private static Panier instance; // Singleton

    private Map<Article, Integer> articles;

    private Panier() {
        articles = new HashMap<>();
    }

    // Méthode statique pour accéder à l’instance unique
    public static Panier getInstance() {
        if (instance == null) {
            instance = new Panier();
        }
        return instance;
    }

    public void ajouterArticle(Article article, int quantite) {
        articles.put(article, articles.getOrDefault(article, 0) + quantite);
    }

    public void retirerArticle(Article article) {
        articles.remove(article);
    }

    public Map<Article, Integer> getArticles() {
        return articles;
    }

    public void viderPanier() {
        articles.clear();
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
            total += entry.getKey().getPrix_unitaire() * entry.getValue();
        }
        return total;
    }

    public boolean estVide() {
        return articles.isEmpty();
    }
}
