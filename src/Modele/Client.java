package Modele;

/**
 * Classe Client
 * Représente un client de l'application avec ses informations personnelles et son statut
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Martin
 */
public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String typeClient;

    /**
     * Constructeur par défaut de Client
     */
    public Client() {}

    /**
     * Constructeur pour créer un nouveau client avec ses informations
     * @param nom nom du client
     * @param prenom prénom du client
     * @param email adresse email du client
     * @param mdp mot de passe du client
     * @param typeClient type de client ("nouveau" ou "ancien")
     */
    public Client(String nom, String prenom, String email, String mdp, String typeClient) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.typeClient = typeClient;
    }

    /**
     * Retourne l'identifiant du client
     * @return id du client
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Modifie l'identifiant du client
     * @param idClient nouvel identifiant
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * Retourne le nom du client
     * @return nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du client
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prénom du client
     * @return prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifie le prénom du client
     * @param prenom nouveau prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne l'email du client
     * @return adresse email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifie l'email du client
     * @param email nouvel email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe du client
     * @return mot de passe
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Modifie le mot de passe du client
     * @param mdp nouveau mot de passe
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Retourne le type du client ("nouveau" ou "ancien")
     * @return type du client
     */
    public String getTypeClient() {
        return typeClient;
    }

    /**
     * Modifie le type du client
     * @param typeClient nouveau type ("nouveau" ou "ancien")
     */
    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }
}
