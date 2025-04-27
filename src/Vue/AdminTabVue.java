package Vue;

import dao.ArticleDAO;
import Modele.Article;
import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Fenêtre principale de l'interface administrateur pour gérer les articles.
 * Permet d'afficher, ajouter, modifier, supprimer des articles et d'afficher des statistiques de ventes.
 *
 * @author Jean
 * @source https://openclassrooms.com/fr/courses/2434016-developpez-des-sites-web-avec-java-ee/2438576-le-modele-mvc
 */
public class AdminTabVue extends JFrame {
    private Administrateur admin;
    private JPanel articlesPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton ajouterButton;
    private JButton statistiquesButton;
    private JMenu compteMenu;
    private List<Article> listeArticles;

    private ArticleDAO articleDAO;

    /**
     * Constructeur de la fenêtre principale de l'interface administrateur.
     *
     * @param admin L'administrateur connecté à l'interface.
     */
    public AdminTabVue(Administrateur admin) {
        this.admin = admin;
        this.articleDAO = new ArticleDAO();

        setTitle("Catalogue Admin - ShopECE");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        afficherArticles(articleDAO.listerArticles());
    }

    /**
     * Initialise l'interface utilisateur pour la gestion des articles.
     * Configure les boutons, menus et panneaux de l'interface.
     */
    private void initUI() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        ajouterButton = new JButton("Ajouter Article");
        ajouterButton.setBackground(new Color(34, 139, 34));
        ajouterButton.setFont(new Font("Arial", Font.BOLD, 14));
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setFocusPainted(false);

        searchField = new JTextField(20);

        searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(34, 139, 34));
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        statistiquesButton = new JButton("Statistiques Ventes");
        statistiquesButton.setBackground(new Color(34, 139, 34));
        statistiquesButton.setFont(new Font("Arial", Font.BOLD, 14));
        statistiquesButton.setForeground(Color.WHITE);
        statistiquesButton.setFocusPainted(false);

        menuBar.add(ajouterButton);
        menuBar.add(statistiquesButton);
        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(searchField);
        menuBar.add(searchButton);
        menuBar.add(Box.createHorizontalGlue());

        compteMenu = new JMenu("Compte Admin");
        compteMenu.setFont(new Font("Arial", Font.BOLD, 14));
        compteMenu.setForeground(new Color(34, 139, 34));
        JMenuItem profilItem = new JMenuItem("Informations");
        JMenuItem deconnexionItem = new JMenuItem("Se déconnecter");

        compteMenu.add(profilItem);
        compteMenu.add(deconnexionItem);

        menuBar.add(compteMenu);
        setJMenuBar(menuBar);

        articlesPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        JScrollPane scrollPane = new JScrollPane(articlesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        ajouterButton.addActionListener(e -> ajouterArticle());

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            afficherArticles(articleDAO.rechercherArticles(query));
        });

        deconnexionItem.addActionListener(e -> {
            new LoginVue().setVisible(true);
            dispose();
        });

        profilItem.addActionListener(e -> {
            dispose();
            new AdminProfilVue(admin).setVisible(true);
        });

        statistiquesButton.addActionListener(e -> {
            dispose();
            ArticleDAO articleDAO = new ArticleDAO();
            List<Article> articles = articleDAO.listerArticles();

            StatistiquesVentesVue statistiquesVentesVue = new StatistiquesVentesVue(articles, admin);
            statistiquesVentesVue.setVisible(true);
        });
    }

    /**
     * Affiche la liste des articles dans le panneau central.
     *
     * @param articles La liste des articles à afficher.
     */
    private void afficherArticles(List<Article> articles) {
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
                    if (SwingUtilities.isRightMouseButton(evt)) {
                        JPopupMenu popup = new JPopupMenu();
                        JMenuItem modifierItem = new JMenuItem("Modifier");
                        JMenuItem supprimerItem = new JMenuItem("Supprimer");

                        modifierItem.addActionListener(e -> modifierArticle(article));
                        supprimerItem.addActionListener(e -> supprimerArticle(article.getIdArticle()));

                        popup.add(modifierItem);
                        popup.add(supprimerItem);
                        popup.show(panel, evt.getX(), evt.getY());
                    }
                }
            });

            articlesPanel.add(panel);
        }

        articlesPanel.revalidate();
        articlesPanel.repaint();
    }

    /**
     * Ouvre un formulaire pour ajouter un nouvel article.
     */
    private void ajouterArticle() {
        dispose();
        ArticleFormVue dialog = new ArticleFormVue(this, null, admin);
        dialog.setVisible(true);

        if (dialog.isValide()) {
            Article nouvelArticle = dialog.obtenirArticleDesChamps();
            if (articleDAO.ajouterArticle(nouvelArticle)) {
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Ouvre un formulaire pour modifier un article existant.
     *
     * @param article L'article à modifier.
     */
    private void modifierArticle(Article article) {
        dispose();
        ArticleFormVue dialog = new ArticleFormVue(this, article, admin);
        dialog.setVisible(true);

        if (dialog.isValide()) {
            Article articleModifie = dialog.obtenirArticleDesChamps();
            if (articleDAO.modifierArticle(articleModifie)) {
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Supprime un article existant.
     *
     * @param idArticle L'identifiant de l'article à supprimer.
     */
    private void supprimerArticle(int idArticle) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer cet article ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (articleDAO.supprimerArticle(idArticle)) {
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}