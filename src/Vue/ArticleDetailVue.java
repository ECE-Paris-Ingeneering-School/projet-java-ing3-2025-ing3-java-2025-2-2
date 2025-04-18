package Vue;

import Modele.Article;
import dao.ArticleDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ArticleDetailVue extends JFrame {

    public ArticleDetailVue(Article article) {
        setTitle("Détail de l'article");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Image de l'article
        JLabel imageLabel;
        try {
            URL imgUrl = getClass().getClassLoader().getResource(article.getPhoto());
            if (imgUrl != null) {
                ImageIcon imageIcon = new ImageIcon(imgUrl);
                imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
            } else {
                imageLabel = new JLabel("Image non trouvée");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            imageLabel = new JLabel("Erreur de chargement de l'image");
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(imageLabel, BorderLayout.NORTH);

        // Centre : Infos article
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel nomLabel = new JLabel(article.getNom());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel prixLabel = new JLabel(String.format("Prix unitaire : %.2f €", article.getPrix_unitaire()));
        prixLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        prixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("<html><p style='width:300px'>" + article.getDescription() + "</p></html>");
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton marqueButton = new JButton(article.getMarque().getNom());
        marqueButton.setBorderPainted(false);
        marqueButton.setContentAreaFilled(false);
        marqueButton.setForeground(Color.BLUE);
        marqueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        marqueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        marqueButton.addActionListener(e -> {
            List<Article> articlesParMarque = new ArticleDAO().getArticlesParMarque(article.getMarque().getIdMarque());
            ArticleVue articleVue = new ArticleVue();
            articleVue.afficherArticles(articlesParMarque);
            this.dispose(); // ferme la fenêtre actuelle
        });

        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(nomLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(prixLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(descriptionLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(marqueButton);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Sud : Ajouter au panier
        JButton ajouterPanierBtn = new JButton("Ajouter au panier");
        ajouterPanierBtn.setPreferredSize(new Dimension(200, 40));
        ajouterPanierBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        ajouterPanierBtn.addActionListener(e -> {
            // Implémenter la logique panier ici
            JOptionPane.showMessageDialog(this, "Ajouté au panier !");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(ajouterPanierBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
