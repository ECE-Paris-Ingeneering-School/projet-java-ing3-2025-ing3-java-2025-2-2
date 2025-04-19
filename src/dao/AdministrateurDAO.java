package dao;

import Modele.Administrateur;
import java.sql.*;

public class AdministrateurDAO {

    private Connection connection;

    public AdministrateurDAO(Connection connection) {
        this.connection = connection;
    }

    public Administrateur seConnecter(String email, String motDePasse) {
        String query = "SELECT * FROM administrateur WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Administrateur(
                        rs.getInt("id_admin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
