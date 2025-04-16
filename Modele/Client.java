package Modele;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String typeClient;

    public Client() {}

    public Client(String nom, String prenom, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.typeClient = typeClient;
    }

    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public String getTypeClient() {
        return typeClient;
    }
    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }
}
