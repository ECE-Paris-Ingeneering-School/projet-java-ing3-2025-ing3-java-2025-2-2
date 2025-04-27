package Modele;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe Commande
 * Représente une commande passée par un client, contenant plusieurs articles
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Quentin
 */
public class Commande {
    private int idCommande;
    private Client client;
    private LocalDateTime dateCommande;
    private double prixTotal;
    private List<CommandeArticle> articles;

    /**
     * Constructeur complet d'une commande
     * @param idCommande identifiant de la commande
     * @param client client ayant passé la commande
     * @param dateCommande date et heure de la commande
     * @param prixTotal montant total de la commande
     */
    public Commande(int idCommande, Client client, LocalDateTime dateCommande, double prixTotal) {
        this.idCommande = idCommande;
        this.client = client;
        this.dateCommande = dateCommande;
        this.prixTotal = prixTotal;
    }

    /**
     * Retourne l'identifiant de la commande
     * @return id de la commande
     */
    public int getIdCommande() {
        return idCommande;
    }

    /**
     * Modifie l'identifiant de la commande
     * @param idCommande nouvel identifiant
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    /**
     * Retourne le client ayant passé la commande
     * @return client associé
     */
    public Client getClient() {
        return client;
    }

    /**
     * Modifie le client associé à la commande
     * @param client nouveau client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Retourne la date et l'heure de la commande
     * @return date de la commande
     */
    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    /**
     * Modifie la date de la commande
     * @param dateCommande nouvelle date
     */
    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    /**
     * Retourne le prix total de la commande
     * @return prix total
     */
    public double getPrixTotal() {
        return prixTotal;
    }

    /**
     * Modifie le prix total de la commande
     * @param prixTotal nouveau prix
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    /**
     * Retourne la liste des articles associés à la commande
     * @return liste des CommandeArticle
     */
    public List<CommandeArticle> getArticles() {
        return articles;
    }

    /**
     * Modifie la liste des articles de la commande
     * @param articles nouvelle liste d'articles
     */
    public void setArticles(List<CommandeArticle> articles) {
        this.articles = articles;
    }
}
