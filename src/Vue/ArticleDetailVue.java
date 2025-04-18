package Vue;

import Modele.Article;
import dao.ArticleDAO;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;

public class ArticleDetailVue extends JFrame {
    public ArticleDetailVue(Article article) {
        setTitle("Détail de l'article");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nom
        JLabel nomLabel = new JLabel(article.getNom(), SwingConstants.CENTER);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(nomLabel);

        detailPanel.add(Box.createVerticalStrut(15));

        // Image
        try {
            URL imgUrl = getClass().getClassLoader().getResource(article.getPhoto());
            if (imgUrl != null) {
                ImageIcon imageIcon = new ImageIcon(imgUrl);
                Image img = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailPanel.add(imageLabel);
            } else {
                detailPanel.add(new JLabel("Image non trouvée", SwingConstants.CENTER));
            }
        } catch (Exception e) {
            detailPanel.add(new JLabel("Erreur chargement image", SwingConstants.CENTER));
        }

        detailPanel.add(Box.createVerticalStrut(10));

        // Marque (cliquable)
        JButton marqueBtn = new JButton(article.getMarque().getNom());
        marqueBtn.setBorderPainted(false);
        marqueBtn.setContentAreaFilled(false);
        marqueBtn.setForeground(Color.BLUE);
        marqueBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        marqueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        marqueBtn.addActionListener(e -> {
            List<Article> articlesParMarque = new ArticleDAO().getArticlesParMarque(article.getMarque().getIdMarque());
            ArticleVue vue = new ArticleVue();
            vue.afficherArticles(articlesParMarque);
            dispose();
        });
        detailPanel.add(marqueBtn);

        detailPanel.add(Box.createVerticalStrut(10));

        // Description
        JTextArea description = new JTextArea(article.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(detailPanel.getBackground());
        detailPanel.add(description);

        detailPanel.add(Box.createVerticalStrut(10));

        // Prix
        JLabel prixLabel = new JLabel("Prix : " + article.getPrix_unitaire() + " €");
        prixLabel.setFont(new Font("Arial", Font.BOLD, 16));
        prixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(prixLabel);

        detailPanel.add(Box.createVerticalStrut(15));

        // Sélection quantité
        JPanel quantitePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton moinsBtn = new JButton("-");
        JLabel quantiteLabel = new JLabel("1");
        JButton plusBtn = new JButton("+");
        quantitePanel.add(moinsBtn);
        quantitePanel.add(quantiteLabel);
        quantitePanel.add(plusBtn);
        detailPanel.add(quantitePanel);

        final int[] quantite = {1};
        moinsBtn.addActionListener(e -> {
            if (quantite[0] > 1) {
                quantite[0]--;
                quantiteLabel.setText(String.valueOf(quantite[0]));
            }
        });
        plusBtn.addActionListener(e -> {
            quantite[0]++;
            quantiteLabel.setText(String.valueOf(quantite[0]));
        });

        detailPanel.add(Box.createVerticalStrut(10));

        // Bouton Ajouter au panier
        JButton ajouterPanierBtn = new JButton("Ajouter au panier");
        ajouterPanierBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        ajouterPanierBtn.addActionListener(e -> {
            Panier.getInstance().ajouterArticle(article, quantite[0]);
            JOptionPane.showMessageDialog(this, quantite[0] + " article(s) ajouté(s) au panier !");
        });

        detailPanel.add(ajouterPanierBtn);

        add(detailPanel);
        setVisible(true);
    }
}
