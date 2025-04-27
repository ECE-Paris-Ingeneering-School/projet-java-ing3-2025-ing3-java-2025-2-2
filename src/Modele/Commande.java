package Modele;

import java.time.LocalDateTime;
import java.util.List;

public class Commande {
    private int idCommande;
    private Client client;
    private LocalDateTime dateCommande;
    private double prixTotal;
    private List<CommandeArticle> articles;

    public Commande(int idCommande, Client client, LocalDateTime dateCommande, double prixTotal) {
        this.idCommande = idCommande;
        this.client = client;
        this.dateCommande = dateCommande;
        this.prixTotal = prixTotal;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public List<CommandeArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<CommandeArticle> articles) {
        this.articles = articles;
    }
}

