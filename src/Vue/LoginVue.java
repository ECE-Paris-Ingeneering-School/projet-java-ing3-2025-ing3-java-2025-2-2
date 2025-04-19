package Vue;

import Controleur.ClientControleur;
import Modele.Client;
import Modele.Panier;
import dao.ConnexionBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerLink;
    private JButton adminLink; // 🔥 Ajout ici
    private JLabel messageLabel;

    private ClientControleur controleur;

    public LoginVue() {
        controleur = new ClientControleur();

        setTitle("Connexion - Boutique en ligne");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Se connecter");
        registerLink = new JButton("Je n'ai pas encore de compte");
        adminLink = new JButton("Connexion Admin"); // 🔥 Bouton admin
        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        // Ajout d’un panel bas pour les deux liens
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(registerLink);
        bottomPanel.add(adminLink);
        add(bottomPanel, BorderLayout.SOUTH);

        add(messageLabel, BorderLayout.NORTH);

        // Action bouton "Se connecter" (client)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                Client client = controleur.connecterClient(email, password);

                if (client != null) {
                    Panier panier = Panier.getInstance();
                    panier.chargerDepuisBase(client.getIdClient());

                    ArticleVue articleVue = new ArticleVue(client.getIdClient());
                    articleVue.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "L'email ou le mot de passe ne correspondent pas.",
                            "Erreur de connexion",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action bouton inscription
        registerLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new enregistrement().afficher();
            }
        });

        // 🔐 Action bouton admin
        adminLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Connection conn = null;
                try {
                    conn = ConnexionBDD.getConnexion();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (conn != null) {
                    new AdminLoginVue(conn).setVisible(true);
                }
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
