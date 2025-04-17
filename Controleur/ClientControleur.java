package Controleur;

import Modele.Client;
import dao.ClientDAO;

/**
 * Contrôleur pour la gestion des clients
 */
public class ClientControleur {
    private ClientDAO clientDAO;

    public ClientControleur() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Inscrit un nouveau client
     * @param nom Le nom du client
     * @param prenom Le prénom du client
     * @param email L'email du client
     * @param mdp Le mot de passe
     * @param typeClient Le type de client (nouveau / ancien)
     * @return true si l'inscription a réussi
     */
    public boolean inscrireClient(String nom, String prenom, String email, String mdp, String typeClient) {
        Client client = new Client(nom, prenom, email, mdp, typeClient);
        return clientDAO.creer(client);
    }

    /**
     * Connecte un client
     * @param email L'email du client
     * @param motDePasse Le mot de passe
     * @return Le client connecté ou null si échec
     */
    public Client connecterClient(String email, String motDePasse) {
        Client client = clientDAO.trouverParEmail(email);
        if (client != null && client.getMdp().equals(motDePasse)) {
            return client;
        }
        return null;
    }

    // Tu peux ensuite ajouter : afficherHistoriqueCommandes(), modifierMotDePasse(), etc.
}
