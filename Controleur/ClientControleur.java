package Controleur;

import Modele.Client;
import dao.ClientDAO;


public class ClientControleur {
    private ClientDAO clientDAO;

    public ClientControleur() {
        this.clientDAO = new ClientDAO();
    }


    public boolean inscrireClient(String nom, String prenom, String email, String mdp, String typeClient) {
        Client client = new Client(nom, prenom, email, mdp, typeClient);
        return clientDAO.creer(client);
    }


    public Client connecterClient(String email, String motDePasse) {
        Client client = clientDAO.trouverParEmail(email);
        if (client != null && client.getMdp().equals(motDePasse)) {
            return client;
        }
        return null;
    }

}
