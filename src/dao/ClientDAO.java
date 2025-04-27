package dao;

import Modele.Client;
import java.sql.*;


public class ClientDAO {


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



}
