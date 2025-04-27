package Vue;

import Modele.Article;
import Modele.Panier;
import dao.PanierDAO;
import dao.CommandeDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PanierVue extends JFrame {

    private JPanel panel;

    public PanierVue(int idClient) {
        setTitle("Mon Panier");
        setSize(1000, 600); // Même taille que le Menu
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        afficherPanier(idClient);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }

    private void afficherPanier(int idClient) {
        panel.removeAll();

        Map<Article, Integer> panier = PanierDAO.getPanier(idClient);
        Panier.getInstance().setArticles(panier);

        if (panier.isEmpty()) {
            JLabel videLabel = new JLabel("Votre panier est vide.");
            videLabel.setFont(new Font("Arial", Font.BOLD, 16));
            videLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(videLabel);
        } else {
            double total = 0.0;

            for (Map.Entry<Article, Integer> entry : panier.entrySet()) {
                Article article = entry.getKey();
                int quantite = entry.getValue();

                double prixTotalArticle = article.calculerPrixTotal(quantite);
                total += prixTotalArticle;

                JPanel ligne = new JPanel(new GridLayout(1, 3));
                ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                String photoPath = article.getPhoto();
                ImageIcon icon = null;

                try {
                    icon = new ImageIcon(getClass().getClassLoader().getResource(photoPath));
                    Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(scaledImage);
                } catch (Exception e) {
                    icon = new ImageIcon();
                }

                JLabel imageLabel = new JLabel(icon);
                JLabel nomLabel = new JLabel(article.getNom() + " x" + quantite);
                JLabel prixLabel = new JLabel(String.format("%.2f €", prixTotalArticle));
                prixLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                ligne.add(imageLabel);
                ligne.add(nomLabel);
                ligne.add(prixLabel);

                panel.add(ligne);
                panel.add(Box.createVerticalStrut(5));
            }

            panel.add(Box.createVerticalStrut(10));

            JLabel totalLabel = new JLabel("Total : " + String.format("%.2f €", total), SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
            totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(totalLabel);
        }

        panel.add(Box.createVerticalStrut(30));

        JButton validerBtn = new JButton("Valider la commande");
        validerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        validerBtn.setBackground(new Color(76, 175, 80));
        validerBtn.setForeground(Color.WHITE);
        validerBtn.setFocusPainted(false);
        validerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        validerBtn.setMaximumSize(new Dimension(200, 40));

        validerBtn.addActionListener(e -> {
            FenetrePaiement fenetrePaiement = new FenetrePaiement(() -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Souhaitez-vous valider cette commande ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Map<Article, Integer> panierMap = Panier.getInstance().getArticles();
                    double total = Panier.getInstance().getTotal();
                    CommandeDAO commandeDAO = new CommandeDAO();
                    boolean success = commandeDAO.validerCommande(idClient, panierMap, total);

                    if (success) {
                        Panier.getInstance().viderPanier();
                        PanierDAO.viderPanier(idClient);
                        JOptionPane.showMessageDialog(this, "Commande validée avec succès !");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la validation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            fenetrePaiement.setVisible(true);
        });

        JButton viderBtn = new JButton("Vider le Panier");
        viderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        viderBtn.setBackground(new Color(76, 175, 80));
        viderBtn.setForeground(Color.WHITE);
        viderBtn.setFocusPainted(false);
        viderBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viderBtn.setMaximumSize(new Dimension(200, 40));

        viderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Panier.getInstance().viderPanier();
                PanierDAO.viderPanier(idClient);
                afficherPanier(idClient);
            }
        });

        JButton retourBtn = new JButton("Retour au Menu");
        retourBtn.setFont(new Font("Arial", Font.BOLD, 16));
        retourBtn.setBackground(new Color(76, 175, 80));
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourBtn.setMaximumSize(new Dimension(200, 40));

        retourBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ArticleVue(idClient);
            }
        });

        panel.add(validerBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viderBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(retourBtn);

        panel.revalidate();
        panel.repaint();
    }
}
