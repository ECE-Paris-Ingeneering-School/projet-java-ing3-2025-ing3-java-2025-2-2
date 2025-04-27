package Modele;

public class Session {
    private static Client clientActuel;

    public static void setClient(Client client) {
        clientActuel = client;
    }

    public static Client getClient() {
        return clientActuel;
    }

    public static boolean estConnecte() {
        return clientActuel != null;
    }

    public static boolean estAncienClient() {
        return estConnecte() && "ancien".equalsIgnoreCase(clientActuel.getTypeClient());
    }

    public static void setNouveauClient() {
        Client client = new Client();
        client.setTypeClient("nouveau");
        setClient(client);  // On définit un client avec un type "nouveau"
    }

    public static void setAncienClient() {
        if (clientActuel != null && !"ancien".equalsIgnoreCase(clientActuel.getTypeClient())) {
            clientActuel.setTypeClient("ancien");
        }
    }

    public static void deconnecter() {
        clientActuel = null;
        setNouveauClient();
    }
}
