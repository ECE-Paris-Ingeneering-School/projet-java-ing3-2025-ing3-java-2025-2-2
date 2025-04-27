package Vue;

import Modele.Article;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatistiquesVentesVue extends JFrame {
    private List<Article> articles;  // Liste des articles pour les stats

    public StatistiquesVentesVue(List<Article> articles) {
        this.articles = articles;

        setTitle("Statistiques de Ventes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retourButton = new JButton("← Retour");
        styleButton(retourButton);
        retourButton.addActionListener(e -> dispose());
        topPanel.add(retourButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int nombreTotalArticles = articles.size();
        double chiffreAffairesTotal = calculerChiffreAffairesTotal();

        JLabel titreLabel = new JLabel("Statistiques Globales");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nbArticlesLabel = new JLabel("Nombre total d'articles : " + nombreTotalArticles);
        nbArticlesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nbArticlesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel chiffreAffairesLabel = new JLabel("Chiffre d'affaires total estimé : " + chiffreAffairesTotal + " €");
        chiffreAffairesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        chiffreAffairesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsPanel.add(titreLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        statsPanel.add(nbArticlesLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(chiffreAffairesLabel);

        add(statsPanel, BorderLayout.CENTER);
    }

    private double calculerChiffreAffairesTotal() {
        double total = 0;
        for (Article article : articles) {
            total += article.getPrix_unitaire() * article.getQte_vrac();
        }
        return total;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(160, 40));
    }
}
