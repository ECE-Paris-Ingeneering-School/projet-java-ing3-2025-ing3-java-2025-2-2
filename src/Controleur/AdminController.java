package Controleur;

import dao.AdministrateurDAO;
import Modele.Administrateur;

import java.sql.Connection;

public class AdminController {
    private AdministrateurDAO adminDAO;

    public AdminController(Connection connection) {
        this.adminDAO = new AdministrateurDAO(connection);
    }

    public Administrateur seConnecter(String email, String motDePasse) {
        return adminDAO.seConnecter(email, motDePasse);
    }
}
