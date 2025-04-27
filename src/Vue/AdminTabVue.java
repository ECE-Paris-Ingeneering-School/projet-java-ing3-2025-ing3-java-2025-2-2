package Vue;

import dao.ArticleDAO;
import Modele.Article;
import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;

public class AdminTabVue extends JFrame {
    private Administrateur admin;
    private JPanel articlesPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton ajouterButton;
    private JMenu compteMenu;

    private ArticleDAO articleDAO;

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

    private void initUI() {
        // Menu bar
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

        ajouterButton.setFocusPainted(false);
        searchButton.setFocusPainted(false);

        menuBar.add(ajouterButton);
        menuBar.add(Box.createHorizontalStrut(10));
        menuBar.add(searchField);
        menuBar.add(searchButton);
        menuBar.add(Box.createHorizontalGlue());

        // Compte admin
        compteMenu = new JMenu("Compte Admin");
        compteMenu.setFont(new Font("Arial", Font.BOLD, 14));
        compteMenu.setForeground(new Color(34, 139, 34));
        JMenuItem profilItem = new JMenuItem("Informations");
        JMenuItem deconnexionItem = new JMenuItem("Se déconnecter");

        compteMenu.add(profilItem);
        compteMenu.add(deconnexionItem);

        menuBar.add(compteMenu);
        setJMenuBar(menuBar);

        // Panel articles
        articlesPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        JScrollPane scrollPane = new JScrollPane(articlesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Actions
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
            new AdminProfilVue(admin).setVisible(true);
        });
    }

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

            // Clic droit pour modifier ou supprimer
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

    private void ajouterArticle() {
        ArticleFormDialog dialog = new ArticleFormDialog(this, null);
        dialog.setVisible(true);

        if (dialog.isValidated()) {
            Article nouvelArticle = dialog.getArticleFromFields();
            if (articleDAO.ajouterArticle(nouvelArticle)) {
                JOptionPane.showMessageDialog(this, "Article ajouté !");
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierArticle(Article article) {
        ArticleFormDialog dialog = new ArticleFormDialog(this, article);
        dialog.setVisible(true);

        if (dialog.isValidated()) {
            Article articleModifie = dialog.getArticleFromFields();
            if (articleDAO.modifierArticle(articleModifie)) {
                JOptionPane.showMessageDialog(this, "Article modifié !");
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerArticle(int idArticle) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer cet article ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (articleDAO.supprimerArticle(idArticle)) {
                JOptionPane.showMessageDialog(this, "Article supprimé !");
                afficherArticles(articleDAO.listerArticles());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
