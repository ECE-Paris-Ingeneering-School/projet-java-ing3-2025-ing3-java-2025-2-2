package dao;

import Modele.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour la gestion des clients en base de données
 */
public class ClientDAO {
    /**
     * Crée un nouveau client dans la base de données
     * @param client Le client à créer
     * @return true si la création a réussi
     */
    public boolean creer(Client client) {
        String sql = "INSERT INTO clients (nom, email, mot_de_passe, date_inscription, type_client) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getMdp());
            stmt.setString(5, client.getTypeClient());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        client.setIdClient(rs.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du client: " + e.getMessage());
        }
        return false;
    }

    /**
     * Trouve un client par son email
     * @param email L'email du client
     * @return Le client trouvé ou null
     */
    public Client trouverParEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ?";
        Client client = null;

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client();
                    client.setIdClient(rs.getInt("id"));
                    client.setNom(rs.getString("nom"));
                    client.setEmail(rs.getString("email"));
                    client.setMdp(rs.getString("mot_de_passe"));
                    client.setTypeClient(rs.getString("type_client"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
        }

        return client;
    }

    // Autres méthodes (mettreAJour, supprimer, trouverParId, etc.)
}