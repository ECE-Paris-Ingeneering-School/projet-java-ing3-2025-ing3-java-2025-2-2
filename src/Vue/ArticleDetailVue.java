package Vue;

import Modele.Article;
import Modele.Panier;
import dao.ArticleDAO;
import dao.PanierDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ArticleDetailVue extends JFrame {
    private int idClient;

    public ArticleDetailVue(Article article, int idClient) {
        this.idClient = idClient;

        setTitle("Détail de l'article");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color greenColor = new Color(34, 139, 34);

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton retourBtn = new JButton("←");
        retourBtn.setBackground(greenColor);
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.setPreferredSize(new Dimension(50, 30));
        retourBtn.addActionListener(e -> {
            new ArticleVue(idClient);
            dispose();
        });
        topPanel.add(retourBtn);
        detailPanel.add(topPanel);

        JLabel nomLabel = new JLabel(article.getNom(), SwingConstants.CENTER);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(nomLabel);

        detailPanel.add(Box.createVerticalStrut(15));

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

        JButton marqueBtn = new JButton(article.getMarque().getNom());
        marqueBtn.setBorderPainted(false);
        marqueBtn.setContentAreaFilled(false);
        marqueBtn.setForeground(Color.BLUE);
        marqueBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        marqueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        marqueBtn.addActionListener(e -> {
            List<Article> articlesParMarque = new ArticleDAO().getArticlesParMarque(article.getMarque().getIdMarque());
            ArticleVue vue = new ArticleVue(idClient);
            vue.afficherArticles(articlesParMarque);
            dispose();
        });
        detailPanel.add(marqueBtn);

        detailPanel.add(Box.createVerticalStrut(10));
        JTextArea description = new JTextArea(article.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(detailPanel.getBackground());
        detailPanel.add(description);

        detailPanel.add(Box.createVerticalStrut(10));

        final double prixUnitaire = article.getPrix_unitaire();
        JLabel prixUnitaireLabel = new JLabel("Prix unitaire : " + String.format("%.2f", prixUnitaire) + " €");
        prixUnitaireLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        prixUnitaireLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(prixUnitaireLabel);

        JLabel prixTotalLabel = new JLabel("Prix total : " + String.format("%.2f", prixUnitaire) + " €");
        prixTotalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        prixTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(prixTotalLabel);

        detailPanel.add(Box.createVerticalStrut(15));

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
                prixTotalLabel.setText("Prix total : " + String.format("%.2f", prixUnitaire * quantite[0]) + " €");
            }
        });

        plusBtn.addActionListener(e -> {
            quantite[0]++;
            quantiteLabel.setText(String.valueOf(quantite[0]));
            prixTotalLabel.setText("Prix total : " + String.format("%.2f", prixUnitaire * quantite[0]) + " €");
        });

        detailPanel.add(Box.createVerticalStrut(10));

        JButton ajouterPanierBtn = new JButton("Ajouter au panier");
        ajouterPanierBtn.setBackground(greenColor);
        ajouterPanierBtn.setForeground(Color.WHITE);
        ajouterPanierBtn.setFocusPainted(false);
        ajouterPanierBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        ajouterPanierBtn.addActionListener(e -> {
            if (idClient == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez vous connecter pour ajouter des articles au panier.");
                new LoginVue().setVisible(true);
                dispose();
            } else {
                Panier.getInstance().ajouterArticle(article, quantite[0]);
                PanierDAO.ajouterArticle(idClient, article.getIdArticle(), quantite[0]);
                new ArticleVue(idClient);
                dispose();
            }
        });

        detailPanel.add(ajouterPanierBtn);

        add(detailPanel);
        setVisible(true);
    }
}
