public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String typeClient;

    public Client(int id_Client, String nom, String prenom, String email, String mdp) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.typeClient = typeClient;
    }

    public int getIdClient() {
        return idClient;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getMdp() {
        return mdp;
    }
    public String getTypeClient() {
        return typeClient;
    }
}
