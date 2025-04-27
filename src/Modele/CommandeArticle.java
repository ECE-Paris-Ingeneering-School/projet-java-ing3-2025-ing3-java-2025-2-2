package Modele;

public class CommandeArticle {
    private Article article;
    private int quantite;
    private double prixTotal;

    public CommandeArticle(Article article, int quantite, double prixTotal) {
        this.article = article;
        this.quantite = quantite;
        this.prixTotal = prixTotal;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixTotal() {
        return prixTotal;
    }


    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
