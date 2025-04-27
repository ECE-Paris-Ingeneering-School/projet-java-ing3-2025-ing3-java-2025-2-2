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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLink;
    private JButton adminLink;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private JButton backButton;  // Bouton retour

    private ClientControleur controleur;

    public LoginVue() {
        controleur = new ClientControleur();

        setTitle("Connexion - ShopECE");
        setSize(1000, 600);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(1000, 60));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 50));

        JButton backButton = new JButton("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(34, 139, 34));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(50, 40));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);
        emailField.setPreferredSize(new Dimension(300, 30));
        passwordField.setPreferredSize(new Dimension(300, 30));

        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);

        registerLink = new JLabel("<html><u>Je n'ai pas encore de compte</u></html>");
        registerLink.setForeground(new Color(34, 139, 34));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        adminLink = new JButton("Connexion Admin");
        adminLink.setPreferredSize(new Dimension(200, 30));
        adminLink.setBackground(new Color(34, 139, 34));
        adminLink.setForeground(Color.WHITE);
        messageLabel = new JLabel("", SwingConstants.CENTER);

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(registerLink, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(loginButton, gbc);

        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel backPanel = new JPanel();
        backPanel.add(backButton);
        northPanel.add(backPanel, BorderLayout.WEST);
        northPanel.add(titleLabel, BorderLayout.CENTER);
        northPanel.add(messageLabel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(adminLink);
        add(bottomPanel, BorderLayout.SOUTH);

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

        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idClient = -1;
                ArticleVue articleVue = new ArticleVue(idClient);
                articleVue.setVisible(true);
                dispose();
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
