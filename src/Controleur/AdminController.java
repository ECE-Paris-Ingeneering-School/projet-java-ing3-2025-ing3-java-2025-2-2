package Controleur;

import dao.AdministrateurDAO;
import Modele.Administrateur;
import java.sql.Connection;

/**
 * Classe AdminController
 * Sert d'intermédiaire entre la vue et le modèle pour la gestion des administrateurs
 * Applique le modèle MVC en séparant la logique métier du modèle de données
 * Source : <a href="https://grafikart.fr/tutoriels/mvc-model-view-controller-574">Grafikart - Comprendre le modèle MVC</a>
 * @author Alice
 */
public class AdminController {
    private AdministrateurDAO adminDAO;

    /**
     * Constructeur de AdminController
     * @param connection connexion active à la base de données
     */
    public AdminController(Connection connection) {
        this.adminDAO = new AdministrateurDAO(connection);
    }

    /**
     * Permet à un administrateur de se connecter en vérifiant ses informations
     * @param email adresse email de l'administrateur
     * @param motDePasse mot de passe de l'administrateur
     * @return objet Administrateur si la connexion réussit, sinon null
     */
    public Administrateur seConnecter(String email, String motDePasse) {
        return adminDAO.seConnecter(email, motDePasse);
    }
}
