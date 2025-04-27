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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retourBtn = new JButton("←");
        retourBtn.setBackground(new Color(34, 139, 34));
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.addActionListener(e -> {
            new ArticleVue(idClient);
            dispose();
        });
        topPanel.add(retourBtn);
        detailPanel.add(topPanel);

        detailPanel.add(Box.createVerticalStrut(20));

        JLabel nomLabel = new JLabel(article.getNom(), SwingConstants.CENTER);
        nomLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(nomLabel);

        detailPanel.add(Box.createVerticalStrut(20));

        try {
            URL imgUrl = getClass().getClassLoader().getResource(article.getPhoto());
            if (imgUrl != null) {
                ImageIcon imageIcon = new ImageIcon(imgUrl);
                Image img = imageIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(img));
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailPanel.add(imageLabel);
            } else {
                JLabel imgNotFound = new JLabel("Image non trouvée", SwingConstants.CENTER);
                imgNotFound.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailPanel.add(imgNotFound);
            }
        } catch (Exception e) {
            JLabel imgError = new JLabel("Erreur chargement image", SwingConstants.CENTER);
            imgError.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailPanel.add(imgError);
        }

        detailPanel.add(Box.createVerticalStrut(15));

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
        description.setFont(new Font("Arial", Font.PLAIN, 14));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(description);

        detailPanel.add(Box.createVerticalStrut(20));

        JLabel reductionLabel = new JLabel();
        if (article.getQte_vrac() > 0 && article.getPrix_vrac() > 0) {
            reductionLabel.setText("Profitez d'une réduction pour " + article.getQte_vrac() + " articles achetés !");
            reductionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            reductionLabel.setForeground(new Color(0, 128, 0));
            reductionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailPanel.add(reductionLabel);
        }

        detailPanel.add(Box.createVerticalStrut(10));

        final double prixUnitaire = article.getPrix_unitaire();
        final double prixVrac = article.getPrix_vrac();
        final int qteVrac = article.getQte_vrac();

        JLabel prixUnitaireLabel = new JLabel("Prix unitaire : " + String.format("%.2f", prixUnitaire) + " €");
        prixUnitaireLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        prixUnitaireLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(prixUnitaireLabel);

        detailPanel.add(Box.createVerticalStrut(10));

        JLabel prixTotalLabel = new JLabel("Prix total : " + String.format("%.2f", prixUnitaire) + " €");
        prixTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        prixTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(prixTotalLabel);

        detailPanel.add(Box.createVerticalStrut(20));

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
                prixTotalLabel.setText("Prix total : " + String.format("%.2f", calculerPrixTotal(article, quantite[0])) + " €");
            }
        });

        plusBtn.addActionListener(e -> {
            quantite[0]++;
            quantiteLabel.setText(String.valueOf(quantite[0]));
            prixTotalLabel.setText("Prix total : " + String.format("%.2f", calculerPrixTotal(article, quantite[0])) + " €");
        });

        detailPanel.add(Box.createVerticalStrut(20));

        JButton ajouterPanierBtn = new JButton("Ajouter au panier");
        ajouterPanierBtn.setBackground(new Color(34, 139, 34));
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
                dispose();
                new ArticleVue(idClient);
            }
        });
        detailPanel.add(ajouterPanierBtn);

        detailPanel.add(Box.createVerticalStrut(20));

        JScrollPane scrollPane = new JScrollPane(detailPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        setVisible(true);
    }

    private double calculerPrixTotal(Article article, int quantite) {
        int lotsComplets = quantite / article.getQte_vrac();
        int reste = quantite % article.getQte_vrac();
        return lotsComplets * article.getQte_vrac() * article.getPrix_vrac() + reste * article.getPrix_unitaire();
    }
}
