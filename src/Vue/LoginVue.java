package Vue;

import Controleur.ClientControleur;
import Modele.Client;
import Modele.Panier;
import dao.ConnexionBDD;
import Modele.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerLink;
    private JButton adminLink;
    private JLabel messageLabel;
    private JLabel titleLabel;

    private ClientControleur controleur;

    public LoginVue() {
        controleur = new ClientControleur();

        setTitle("Connexion - ShopECE");
        setSize(1000, 600);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(1000, 60));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));


        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 150, 20, 150));


        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);
        loginButton = new JButton("Se connecter");
        registerLink = new JButton("Je n'ai pas encore de compte");
        adminLink = new JButton("Connexion Admin");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        loginButton.setPreferredSize(new Dimension(100, 20));
        registerLink.setPreferredSize(new Dimension(100, 20));
        adminLink.setPreferredSize(new Dimension(100, 20));

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(loginButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(registerLink);
        bottomPanel.add(adminLink);
        add(bottomPanel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                Client client = controleur.connecterClient(email, password);

                if (client != null) {
                    Session.setClient(client);
                    Session.setAncienClient();
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

        registerLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EnregistrementVue().afficher();
            }
        });

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int idClient = -1;
                ArticleVue articleVue = new ArticleVue(idClient);
                articleVue.setVisible(true);
                dispose();
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
