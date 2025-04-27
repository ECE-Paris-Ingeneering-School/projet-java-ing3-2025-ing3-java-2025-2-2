package Vue;

import Modele.Article;
import Modele.Panier;
import dao.PanierDAO;
import dao.CommandeDAO;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PanierVue extends JFrame {

    public PanierVue(int idClient) {
        setTitle("Mon Panier");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Récupération du panier depuis la base
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

                JPanel ligne = new JPanel(new GridLayout(1, 2));
                ligne.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                JLabel nomLabel = new JLabel(article.getNom() + " x" + quantite);
                JLabel prixLabel = new JLabel(String.format("%.2f €", prixTotalArticle));
                prixLabel.setHorizontalAlignment(SwingConstants.RIGHT);

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

        // Bouton "Valider la commande"
        JButton validerBtn = new JButton("Valider la commande");
        validerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        validerBtn.setBackground(new Color(76, 175, 80)); // vert
        validerBtn.setForeground(Color.WHITE);
        validerBtn.setFocusPainted(false);
        validerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        validerBtn.setPreferredSize(new Dimension(200, 40));

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

        panel.add(validerBtn);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }
}
