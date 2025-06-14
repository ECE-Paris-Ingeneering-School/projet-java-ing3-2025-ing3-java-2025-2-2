package Vue;

import Modele.Article;
import Modele.Session;
import dao.ArticleDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Classe représentant la vue d'affichage des articles dans un catalogue.
 * Elle permet de rechercher des articles, afficher les articles dans une grille, et gérer les actions liées au panier et au compte.
 * Cette vue utilise un modèle MVC pour afficher les informations des articles et interagir avec l'utilisateur.
 *
 * @author Martin
 * @source https://openclassrooms.com/fr/courses/2434016-developpez-des-sites-web-avec-java-ee/2438576-le-modele-mvc
 */
public class ArticleVue extends JFrame {

    private JPanel articlesPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton panierButton;
    private JButton catalogueButton;
    private JButton compteButton;
    private int idClient;

    /**
     * Constructeur de la vue d'articles.
     *
     * @param idClient L'ID du client actuel.
     */
    public ArticleVue(int idClient) {
        this.idClient = idClient;
        setTitle("Catalogue d'articles");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        catalogueButton = new JButton("Catalogue");
        searchField = new JTextField(20);
        searchButton = new JButton("Rechercher");
        panierButton = new JButton("Panier");
        compteButton = new JButton("Compte");

        Color greenColor = new Color(34, 139, 34);

        catalogueButton.setBackground(greenColor);
        searchButton.setBackground(greenColor);
        panierButton.setBackground(greenColor);
        compteButton.setBackground(Color.WHITE);

        compteButton.setFont(new Font("Arial", Font.BOLD, 14));
        panierButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        catalogueButton.setFont(new Font("Arial", Font.BOLD, 14));

        catalogueButton.setForeground(Color.WHITE);
        searchButton.setForeground(Color.WHITE);
        panierButton.setForeground(Color.WHITE);
        compteButton.setForeground(new Color(34, 139, 34));

        catalogueButton.setFocusPainted(false);
        searchButton.setFocusPainted(false);
        panierButton.setFocusPainted(false);
        compteButton.setFocusPainted(false);

        catalogueButton.setBorderPainted(false);
        searchButton.setBorderPainted(false);
        panierButton.setBorderPainted(false);
        compteButton.setBorderPainted(false);

        catalogueButton.setOpaque(true);
        searchButton.setOpaque(true);
        panierButton.setOpaque(true);
        compteButton.setOpaque(true);

        menuBar.add(catalogueButton);
        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(searchField);
        menuBar.add(searchButton);
        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(panierButton);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(compteButton);
        setJMenuBar(menuBar);

        JPopupMenu compteMenu = new JPopupMenu();

        if (Session.estAncienClient()) {
            JMenuItem profilItem = new JMenuItem("Informations personnelles");
            JMenuItem historiqueItem = new JMenuItem("Historique des commandes");
            JMenuItem deconnexionItem = new JMenuItem("Se déconnecter");

            compteMenu.add(profilItem);
            compteMenu.add(historiqueItem);
            compteMenu.add(deconnexionItem);

            profilItem.addActionListener(e -> {
                dispose(); // Fermer ArticleVue avant d'ouvrir ProfilVue
                new ProfilVue(idClient).setVisible(true);
            });
            historiqueItem.addActionListener(e -> {
                dispose();
                new HistoriqueCommandeVue(idClient, this);
            });

            deconnexionItem.addActionListener(e -> {
                Session.deconnecter();
                new ArticleVue(-1).setVisible(true);
                dispose();
            });

        } else {
            JMenuItem seConnecterItem = new JMenuItem("Se connecter");
            compteMenu.add(seConnecterItem);

            seConnecterItem.addActionListener(e -> {
                new LoginVue().setVisible(true);
                dispose();
            });
        }

        compteButton.addActionListener(e -> compteMenu.show(compteButton, 0, compteButton.getHeight()));

        articlesPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        JScrollPane scrollPane = new JScrollPane(articlesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        afficherArticles(new ArticleDAO().listerArticles());

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            afficherArticles(new ArticleDAO().rechercherArticles(query));
        });

        panierButton.addActionListener(e -> {
            if (Session.estAncienClient()) {
                dispose();
                new PanierVue(idClient).setVisible(true);
            } else {
                afficherConnexionRequise("le panier");
            }
        });

        catalogueButton.addActionListener(e -> afficherArticles(new ArticleDAO().listerArticles()));

        setVisible(true);
    }

    /**
     * Affiche un message de connexion requise pour accéder à une fonctionnalité.
     *
     * @param fonctionnalite Le nom de la fonctionnalité.
     */
    private void afficherConnexionRequise(String fonctionnalite) {
        JOptionPane.showMessageDialog(this,
                "Veuillez vous connecter pour accéder à " + fonctionnalite + ".",
                "Accès refusé",
                JOptionPane.WARNING_MESSAGE);
        new LoginVue().setVisible(true);
        dispose();
    }

    /**
     * Affiche la liste des articles dans le panel.
     *
     * @param articles La liste des articles à afficher.
     */
    public void afficherArticles(List<Article> articles) {
        articlesPanel.removeAll();

        for (Article article : articles) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel nomLabel = new JLabel(article.getNom(), SwingConstants.CENTER);
            nomLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(nomLabel, BorderLayout.NORTH);
            try {
                URL imgUrl = getClass().getClassLoader().getResource(article.getPhoto());
                if (imgUrl != null) {
                    ImageIcon imageIcon = new ImageIcon(imgUrl);
                    Image img = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    panel.add(imageLabel, BorderLayout.CENTER);
                } else {
                    panel.add(new JLabel("Image non trouvée", SwingConstants.CENTER), BorderLayout.CENTER);
                }
            } catch (Exception e) {
                panel.add(new JLabel("Erreur chargement image", SwingConstants.CENTER), BorderLayout.CENTER);
            }

            panel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    dispose();
                    new ArticleDetailVue(article, idClient);
                }
            });

            articlesPanel.add(panel);
        }

        articlesPanel.revalidate();
        articlesPanel.repaint();
    }
}