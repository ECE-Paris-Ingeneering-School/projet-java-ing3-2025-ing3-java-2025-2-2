package Vue;

import Controleur.AdminController;
import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class AdminLoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private AdminController controller;

    public AdminLoginVue(Connection connection) {
        controller = new AdminController(connection);

        setTitle("Connexion Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Email :"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Connexion");
        loginButton.addActionListener(e -> handleLogin());
        panel.add(loginButton);

        add(panel);
    }

    private void handleLogin() {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());

        Administrateur admin = controller.seConnecter(email, motDePasse);
        if (admin != null) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            dispose(); // ferme la fenêtre de login
            new AdminTabVue(admin).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants invalides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
