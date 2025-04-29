package Modele;

/**
 * Classe Marque
 * Représente une marque associée aux articles du catalogue
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://openclassrooms.com/fr/courses/4670706-adoptez-une-architecture-mvc-en-php/7847928-decouvrez-comment-fonctionne-une-architecture-mvc">OpenClassrooms - Adoptez une architecture MVC</a> * @author Jean
 */
public class Marque {
    private int idMarque;
    private String nom;

    /**
     * Constructeur par défaut de Marque
     */
    public Marque() {}

    /**
     * Constructeur complet d'une marque
     * @param idMarque identifiant de la marque
     * @param nom nom de la marque
     */
    public Marque(int idMarque, String nom) {
        this.idMarque = idMarque;
        this.nom = nom;
    }

    /**
     * Retourne l'identifiant de la marque
     * @return id de la marque
     */
    public int getIdMarque() {
        return idMarque;
    }

    /**
     * Modifie l'identifiant de la marque
     * @param idMarque nouvel identifiant
     */
    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    /**
     * Retourne le nom de la marque
     * @return nom de la marque
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la marque
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne une représentation textuelle de la marque
     * @return nom de la marque
     */
    @Override
    public String toString() {
        return nom;
    }
}
