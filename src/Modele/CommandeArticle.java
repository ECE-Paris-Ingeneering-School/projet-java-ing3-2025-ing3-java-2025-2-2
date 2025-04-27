package Modele;

/**
 * Classe CommandeArticle
 * Représente un article spécifique associé à une commande, avec sa quantité et son prix total
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Alice
 */
public class CommandeArticle {
    private Article article;
    private int quantite;
    private double prixTotal;

    /**
     * Constructeur de CommandeArticle
     * @param article article concerné
     * @param quantite quantité commandée
     * @param prixTotal prix total pour cet article commandé
     */
    public CommandeArticle(Article article, int quantite, double prixTotal) {
        this.article = article;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    /**
     * Retourne l'article de la commande
     * @return article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Modifie l'article de la commande
     * @param article nouvel article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Retourne la quantité commandée de l'article
     * @return quantité commandée
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Modifie la quantité commandée
     * @param quantite nouvelle quantité
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Retourne le prix total de cet article commandé
     * @return prix total
     */
    public double getPrixTotal() {
        return prixTotal;
    }

    /**
     * Modifie le prix total de l'article commandé
     * @param prixTotal nouveau prix total
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
