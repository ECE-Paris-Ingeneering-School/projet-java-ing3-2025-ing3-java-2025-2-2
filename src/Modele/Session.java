package Modele;

/**
 * Classe Session
 * Gère l'état de la session de l'utilisateur connecté (client actuel)
 * Permet de savoir si un client est connecté et son type ("nouveau" ou "ancien")
 * Partie du modèle dans l'architecture MVC
 * Source : <a href="https://baptiste-wicht.developpez.com/tutoriels/conception/mvc/">Baptiste Wicht - Développement de l'architecture MVC</a>
 * @author Quentin
 */
public class Session {
    private static Client clientActuel;

    /**
     * Définit le client actuellement connecté
     * @param client client connecté
     */
    public static void setClient(Client client) {
        clientActuel = client;
    }

    /**
     * Retourne le client actuellement connecté
     * @return client actuel
     */
    public static Client getClient() {
        return clientActuel;
    }

    /**
     * Vérifie si un client est actuellement connecté
     * @return true si connecté, false sinon
     */
    public static boolean estConnecte() {
        return clientActuel != null;
    }

    /**
     * Vérifie si le client connecté est un ancien client
     * @return true si le client est "ancien", false sinon
     */
    public static boolean estAncienClient() {
        return estConnecte() && "ancien".equalsIgnoreCase(clientActuel.getTypeClient());
    }

    /**
     * Initialise la session avec un nouveau client
     */
    public static void setNouveauClient() {
        Client client = new Client();
        client.setTypeClient("nouveau");
        setClient(client);
    }

    /**
     * Modifie le type du client actuel en "ancien" s'il est connecté
     */
    public static void setAncienClient() {
        if (clientActuel != null && !"ancien".equalsIgnoreCase(clientActuel.getTypeClient())) {
            clientActuel.setTypeClient("ancien");
        }
    }

    /**
     * Déconnecte le client actuel et réinitialise la session à un client "nouveau"
     */
    public static void deconnecter() {
        clientActuel = null;
        setNouveauClient();
    }
}
