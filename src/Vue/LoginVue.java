package Vue;

import Controleur.ClientControleur;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerLink;
    private JLabel messageLabel;

    private ClientControleur controleur;

    public LoginVue() {
        controleur = new ClientControleur();

        setTitle("Connexion - Boutique en ligne");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuration du panel principal
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Se connecter");
        registerLink = new JButton("Je n'ai pas encore de compte");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        add(registerLink, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        // Action du bouton Connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                Client client = controleur.connecterClient(email, password);

                if (client != null) {
                    Panier panier = Panier.getInstance();
                    panier.chargerDepuisBase(client.getIdClient()); // Charge le panier depuis la base de données

                    ArticleVue articleVue = new ArticleVue(client.getIdClient());
                    articleVue.setVisible(true);
                    dispose(); // Ferme la fenêtre de connexion
                } else {
                    JOptionPane.showMessageDialog(null,
                            "L'email ou le mot de passe ne correspondent pas.",
                            "Erreur de connexion",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Lien vers la page d'inscription
        registerLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre actuelle
                new enregistrement().afficher(); // Affiche l'interface d'inscription
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
