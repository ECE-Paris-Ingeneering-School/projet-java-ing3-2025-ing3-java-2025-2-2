package Modele;

public class Article {
    private int idArticle;
    private String nom;
    private String description;
    private double prix_unitaire;
    private double prix_vrac;
    private int qte_vrac;
    private Marque marque;
    private String photo;

    public Article() {}

    public Article(int idArticle, String nom, String description, double prix_unitaire, double prix_vrac, int qte_vrac, Marque marque, String photo) {
        this.idArticle = idArticle;
        this.nom = nom;
        this.description = description;
        this.prix_unitaire = prix_unitaire;
        this.prix_vrac = prix_vrac;
        this.qte_vrac = qte_vrac;
        this.marque = marque;
        this.photo = photo;
    }
    public String toString() {
        return nom + " - " + marque + " - " + prix_unitaire + " €";
    }

    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }
    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public double getPrix_vrac() {
        return prix_vrac;
    }
    public void setPrix_vrac(double prix_vrac) {
        this.prix_vrac = prix_vrac;
    }
    public int getQte_vrac() {
        return qte_vrac;
    }
    public void setQte_vrac(int qte_vrac) {
        this.qte_vrac = qte_vrac;
    }
    public Marque getMarque() {
        return marque;
    }
    public void setMarque(Marque marque) {
        this.marque = marque;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public double calculerPrixTotal(int quantite) {
        if (quantite >= qte_vrac && qte_vrac > 0 && prix_vrac > 0) {
            return quantite * prix_vrac;
        } else {
            return quantite * prix_unitaire;
        }
    }

}
