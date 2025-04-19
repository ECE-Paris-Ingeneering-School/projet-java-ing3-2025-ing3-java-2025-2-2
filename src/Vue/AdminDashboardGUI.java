package Vue;

import dao.ArticleDAO;
import Modele.Article;
import Modele.Administrateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardGUI extends JFrame {
    private Administrateur admin;
    private JTable tableArticles;
    private DefaultTableModel tableModel;
    private ArticleDAO articleDAO;

    public AdminDashboardGUI(Administrateur admin) {
        this.admin = admin;
        this.articleDAO = new ArticleDAO();  // Connexion gérée dans DAO

        setTitle("Dashboard Administrateur - Bienvenue " + admin.getPrenom());
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        chargerArticles();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Colonnes du tableau
        String[] colonnes = {"ID", "Nom", "Description", "Prix Unitaire", "Prix Vrac", "Quantité Vrac", "Marque"};

        tableModel = new DefaultTableModel(colonnes, 0);
        tableArticles = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableArticles);
        add(scrollPane, BorderLayout.CENTER);

        // Pour plus tard : barre de boutons (ajouter, modifier, supprimer)
        JPanel boutonPanel = new JPanel();
        boutonPanel.add(new JButton("Ajouter"));
        boutonPanel.add(new JButton("Modifier"));
        boutonPanel.add(new JButton("Supprimer"));

        add(boutonPanel, BorderLayout.SOUTH);
    }

    private void chargerArticles() {
        List<Article> articles = articleDAO.listerArticles();

        for (Article article : articles) {
            tableModel.addRow(new Object[]{
                    article.getIdArticle(),
                    article.getNom(),
                    article.getDescription(),
                    article.getPrix_unitaire(),
                    article.getPrix_vrac(),
                    article.getQte_vrac(),
                    article.getMarque() != null ? article.getMarque().getNom() : "N/A"
            });
        }
    }
}
