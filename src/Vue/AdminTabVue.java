package Vue;

import dao.ArticleDAO;
import Modele.Article;
import Modele.Administrateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminTabVue extends JFrame {
    private Administrateur admin;
    private JTable tableArticles;
    private DefaultTableModel tableModel;
    private ArticleDAO articleDAO;

    public AdminTabVue(Administrateur admin) {
        this.admin = admin;
        this.articleDAO = new ArticleDAO();

        setTitle("Dashboard Administrateur - Bienvenue " + admin.getPrenom());
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        chargerArticles();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        String[] colonnes = {"ID", "Nom", "Description", "Prix Unitaire", "Prix Vrac", "Quantité Vrac", "Marque"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableArticles = new JTable(tableModel);

        tableArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableArticles);
        add(scrollPane, BorderLayout.CENTER);

        JPanel boutonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.addActionListener(e -> ajouterArticle());

        JButton modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(e -> modifierArticle());

        JButton supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(e -> supprimerArticle());

        boutonPanel.add(ajouterBtn);
        boutonPanel.add(modifierBtn);
        boutonPanel.add(supprimerBtn);

        add(boutonPanel, BorderLayout.SOUTH);
    }

    private void chargerArticles() {
        tableModel.setRowCount(0);

        List<Article> articles = articleDAO.listerArticles();

        for (Article article : articles) {
            tableModel.addRow(new Object[]{
                    article.getIdArticle(),
                    article.getNom(),
                    article.getDescription(),
                    String.format("%.2f €", article.getPrix_unitaire()),
                    article.getPrix_vrac() > 0 ? String.format("%.2f €", article.getPrix_vrac()) : "-",
                    article.getQte_vrac() > 0 ? article.getQte_vrac() : "-",
                    article.getMarque() != null ? article.getMarque().getNom() : "N/A"
            });
        }
    }

    private void ajouterArticle() {
        ArticleFormDialog dialog = new ArticleFormDialog(this, null);
        dialog.setVisible(true);

        if (dialog.isValidated()) {
            Article nouvelArticle = dialog.getArticleFromFields();
            if (articleDAO.ajouterArticle(nouvelArticle)) {
                JOptionPane.showMessageDialog(this, "Article ajouté avec succès !");
                chargerArticles();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'article", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierArticle() {
        int selectedRow = tableArticles.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à modifier", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idArticle = (int) tableModel.getValueAt(selectedRow, 0);
        Article article = articleDAO.trouverParId(idArticle);

        if (article != null) {
            ArticleFormDialog dialog = new ArticleFormDialog(this, article);
            dialog.setVisible(true);

            if (dialog.isValidated()) {
                Article articleModifie = dialog.getArticleFromFields();
                if (articleDAO.modifierArticle(articleModifie)) {
                    JOptionPane.showMessageDialog(this, "Article modifié avec succès !");
                    chargerArticles();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void supprimerArticle() {
        int selectedRow = tableArticles.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à supprimer", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idArticle = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir supprimer cet article ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (articleDAO.supprimerArticle(idArticle)) { // À implémenter dans ArticleDAO
                JOptionPane.showMessageDialog(this, "Article supprimé avec succès !");
                chargerArticles();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}