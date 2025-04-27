package dao;

import Modele.Client;
import java.sql.*;

/**
 * Classe ClientDAO
 * Gère l'accès aux données pour les opérations liées aux clients dans la base de données
 * Suivant le modèle DAO pour bien séparer la logique d'accès aux données
 * Source : <a href="https://www.baeldung.com/java-dao-pattern">Baeldung - DAO Pattern</a>
 * @author Martin
 */
public class ClientDAO {

    /**
     * Insère un nouveau client dans la base de données
     * @param client le client à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
    public boolean creer(Client client) {
        String sql = "INSERT INTO Client (nom, prenom, email, mot_de_passe, type_client) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getMdp());
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
     * Trouve un client à partir de son adresse email
     * @param email adresse email du client
     * @return l'objet Client correspondant ou null si non trouvé
     */
    public Client trouverParEmail(String email) {
        String sql = "SELECT * FROM Client WHERE email = ?";
        Client client = null;

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client();
                    client.setIdClient(rs.getInt("id_client"));
                    client.setNom(rs.getString("nom"));
                    client.setPrenom(rs.getString("prenom"));
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

    /**
     * Met à jour le type d'un client pour le passer à "ancien"
     * @param client le client concerné
     * @return true si la mise à jour est réussie, false sinon
     */
    public boolean basculerEnAncien(Client client) {
        String sql = "UPDATE Client SET type_client = 'ancien' WHERE id_client = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, client.getIdClient());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors du changement de type_client : " + e.getMessage());
            return false;
        }
    }

    /**
     * Trouve un client par son identifiant
     * @param idClient identifiant du client
     * @return l'objet Client correspondant ou null si non trouvé
     */
    public Client trouverParId(int idClient) {
        String sql = "SELECT * FROM Client WHERE id_client = ?";
        Client client = null;

        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client();
                    client.setIdClient(rs.getInt("id_client"));
                    client.setNom(rs.getString("nom"));
                    client.setPrenom(rs.getString("prenom"));
                    client.setEmail(rs.getString("email"));
                    client.setMdp(rs.getString("mot_de_passe"));
                    client.setTypeClient(rs.getString("type_client"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client par ID : " + e.getMessage());
        }

        return client;
    }

    /**
     * Met à jour les informations personnelles d'un client
     * @param client le client avec les nouvelles informations
     * @return true si la mise à jour est réussie, false sinon
     */
    public boolean mettreAJourClient(Client client) {
        String query = "UPDATE client SET nom = ?, prenom = ?, email = ? WHERE id_client = ?";
        try (Connection conn = ConnexionBDD.getConnexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getIdClient());

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du client : " + e.getMessage());
            return false;
        }
    }
}
