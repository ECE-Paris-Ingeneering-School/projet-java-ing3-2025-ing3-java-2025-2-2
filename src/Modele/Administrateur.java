package Modele;

/**
 * Classe Administrateur
 * Représente un administrateur du système avec ses informations d'authentification
 * Utilisée dans le cadre du modèle (MVC) pour gérer les accès administrateurs
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Alice
 */
public class Administrateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    /**
     * Constructeur complet pour instancier un administrateur avec toutes ses informations
     * @param id identifiant de l'administrateur
     * @param nom nom de l'administrateur
     * @param prenom prénom de l'administrateur
     * @param email email de connexion
     * @param motDePasse mot de passe de connexion
     */
    public Administrateur(int id, String nom, String prenom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    /**
     * Constructeur pour instancier un administrateur uniquement avec email et mot de passe
     * @param email email de connexion
     * @param motDePasse mot de passe de connexion
     */
    public Administrateur(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    /**
     * Retourne l'identifiant de l'administrateur
     * @return id de l'administrateur
     */
    public int getId() { return id; }

    /**
     * Modifie l'identifiant de l'administrateur
     * @param id nouvel identifiant
     */
    public void setId(int id) { this.id = id; }

    /**
     * Retourne le nom de l'administrateur
     * @return nom
     */
    public String getNom() { return nom; }

    /**
     * Modifie le nom de l'administrateur
     * @param nom nouveau nom
     */
    public void setNom(String nom) { this.nom = nom; }

    /**
     * Retourne le prénom de l'administrateur
     * @return prénom
     */
    public String getPrenom() { return prenom; }

    /**
     * Modifie le prénom de l'administrateur
     * @param prenom nouveau prénom
     */
    public void setPrenom(String prenom) { this.prenom = prenom; }

    /**
     * Retourne l'email de l'administrateur
     * @return email
     */
    public String getEmail() { return email; }

    /**
     * Modifie l'email de l'administrateur
     * @param email nouvel email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Retourne le mot de passe de l'administrateur
     * @return mot de passe
     */
    public String getMotDePasse() { return motDePasse; }

    /**
     * Modifie le mot de passe de l'administrateur
     * @param motDePasse nouveau mot de passe
     */
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
