package Controleur;

import Modele.Client;
import dao.ClientDAO;

/**
 * Classe ClientControleur
 * Sert d'intermédiaire entre la vue et le modèle pour la gestion des clients
 * Permet d'inscrire et connecter un client en appliquant la logique métier
 * Source : <a href="https://grafikart.fr/tutoriels/mvc-model-view-controller-574">Grafikart - Comprendre le modèle MVC</a>
 * @author Quentin
 */
public class ClientControleur {
    private ClientDAO clientDAO;

    /**
     * Constructeur de ClientControleur
     * Initialise le DAO pour accéder aux données clients
     */
    public ClientControleur() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Inscrit un nouveau client en base de données
     * @param nom nom du client
     * @param prenom prénom du client
     * @param email adresse email du client
     * @param mdp mot de passe du client
     * @param typeClient type de client ("nouveau" ou "ancien")
     * @return true si l'inscription réussit, false sinon
     */
    public boolean inscrireClient(String nom, String prenom, String email, String mdp, String typeClient) {
        Client client = new Client(nom, prenom, email, mdp, typeClient);
        return clientDAO.creer(client);
    }

    /**
     * Connecte un client en vérifiant ses informations d'identification
     * Si le client est nouveau, il est automatiquement basculé en ancien client
     * @param email adresse email du client
     * @param motDePasse mot de passe du client
     * @return objet Client si la connexion réussit, sinon null
     */
    public Client connecterClient(String email, String motDePasse) {
        Client client = clientDAO.trouverParEmail(email);
        if (client != null && client.getMdp().equals(motDePasse)) {

            if ("nouveau".equalsIgnoreCase(client.getTypeClient())) {
                boolean majOK = clientDAO.basculerEnAncien(client);
                if (majOK) {
                    client.setTypeClient("ancien");
                    System.out.println("Client passé de 'nouveau' à 'ancien'");
                }
            }

            return client;
        }
        return null;
    }
}
