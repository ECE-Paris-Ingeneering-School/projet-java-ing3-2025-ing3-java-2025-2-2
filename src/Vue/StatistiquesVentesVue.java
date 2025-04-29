package Vue;

import Modele.Article;
import dao.VenteDAO;
import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe StatistiquesVentesVue
 * Fenêtre affichant les statistiques de ventes globales pour les articles
 * Partie de la vue dans l'architecture MVC
 * Source : <a href="https://openclassrooms.com/fr/courses/4670706-adoptez-une-architecture-mvc-en-php/7847928-decouvrez-comment-fonctionne-une-architecture-mvc">OpenClassrooms - Adoptez une architecture MVC</a>
 * @author Quentin
 */
public class StatistiquesVentesVue extends JFrame {
    private List<Article> articles;
    private VenteDAO venteDAO;
    private Administrateur admin;

    /**
     * Constructeur de StatistiquesVentesVue
     * @param articles liste des articles disponibles
     * @param admin administrateur connecté
     */
    public StatistiquesVentesVue(List<Article> articles, Administrateur admin) {
        this.articles = articles;
        this.venteDAO = new VenteDAO();
        this.admin = admin;

        setTitle("Statistiques de Ventes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
    }

    /**
     * Initialise les composants graphiques de la fenêtre
     */
    private void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retourButton = new JButton("←");
        styleButton(retourButton);
        retourButton.addActionListener(e -> {
            dispose();
            new AdminTabVue(admin).setVisible(true);
        });
        topPanel.add(retourButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int nombreTotalArticles = articles.size();
        double chiffreAffairesTotal = calculerChiffreAffairesTotal();
        Article articlePlusVendu = getArticlePlusVendu();
        Article articleMoinsVendu = getArticleMoinsVendu();

        int ventesPlusVendu = (articlePlusVendu != null) ? venteDAO.getQuantiteVendue(articlePlusVendu.getIdArticle()) : 0;
        int ventesMoinsVendu = (articleMoinsVendu != null) ? venteDAO.getQuantiteVendue(articleMoinsVendu.getIdArticle()) : 0;

        JLabel titreLabel = new JLabel("Statistiques Globales");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nbArticlesLabel = new JLabel("Nombre total d'articles : " + nombreTotalArticles);
        nbArticlesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nbArticlesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel chiffreAffairesLabel = new JLabel("Chiffre d'affaires total estimé : " + chiffreAffairesTotal + " €");
        chiffreAffairesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        chiffreAffairesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel plusVenduLabel = new JLabel("Article le plus vendu : " + (articlePlusVendu != null ? articlePlusVendu.getNom() + " (" + ventesPlusVendu + " ventes)" : "Aucun"));
        plusVenduLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        plusVenduLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel moinsVenduLabel = new JLabel("Article le moins vendu : " + (articleMoinsVendu != null ? articleMoinsVendu.getNom() + " (" + ventesMoinsVendu + " ventes)" : "Aucun"));
        moinsVenduLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        moinsVenduLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsPanel.add(titreLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        statsPanel.add(nbArticlesLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(chiffreAffairesLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(plusVenduLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(moinsVenduLabel);

        add(statsPanel, BorderLayout.CENTER);
    }

    /**
     * Calcule le chiffre d'affaires total estimé
     * @return chiffre d'affaires total
     */
    private double calculerChiffreAffairesTotal() {
        double total = 0;
        for (Article article : articles) {
            total += article.getPrix_unitaire() * article.getQte_vrac();
        }
        return total;
    }

    /**
     * Récupère l'article ayant réalisé le plus de ventes
     * @return article le plus vendu
     */
    private Article getArticlePlusVendu() {
        Article articlePlusVendu = null;
        int maxVentes = -1;

        for (Article article : articles) {
            int quantiteVendue = venteDAO.getQuantiteVendue(article.getIdArticle());
            if (quantiteVendue > maxVentes) {
                maxVentes = quantiteVendue;
                articlePlusVendu = article;
            }
        }
        return articlePlusVendu;
    }

    /**
     * Récupère l'article ayant réalisé le moins de ventes
     * @return article le moins vendu
     */
    private Article getArticleMoinsVendu() {
        Article articleMoinsVendu = null;
        int minVentes = Integer.MAX_VALUE;

        for (Article article : articles) {
            int quantiteVendue = venteDAO.getQuantiteVendue(article.getIdArticle());
            if (quantiteVendue < minVentes) {
                minVentes = quantiteVendue;
                articleMoinsVendu = article;
            }
        }
        return articleMoinsVendu;
    }

    /**
     * Applique un style uniforme aux boutons
     * @param button bouton à styliser
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(160, 40));
    }
}
