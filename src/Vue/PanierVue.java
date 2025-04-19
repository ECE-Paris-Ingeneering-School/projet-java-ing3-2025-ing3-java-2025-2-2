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
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Récupération du panier depuis la base
        Map<Article, Integer> panier = PanierDAO.getPanier(idClient);
        Panier.getInstance().setArticles(panier); // mettre à jour le panier en mémoire

        if (panier.isEmpty()) {
            panel.add(new JLabel("Votre panier est vide."));
        } else {
            double total = 0.0;

            for (Map.Entry<Article, Integer> entry : panier.entrySet()) {
                Article article = entry.getKey();
                int quantite = entry.getValue();

                double prixTotalArticle = article.calculerPrixTotal(quantite);
                total += prixTotalArticle;

                JPanel ligne = new JPanel(new BorderLayout());
                ligne.add(new JLabel(article.getNom() + " x" + quantite), BorderLayout.WEST);
                ligne.add(new JLabel(String.format("%.2f €", prixTotalArticle)), BorderLayout.EAST);

                panel.add(ligne);
            }

            JLabel totalLabel = new JLabel("Total : " + String.format("%.2f €", total), SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(Box.createVerticalStrut(10));
            panel.add(totalLabel);
        }

        // Bouton pour vider le panier
        JButton viderBtn = new JButton("Vider le panier");
        viderBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous vraiment vider votre panier ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Panier.getInstance().viderPanier();     // Vide en mémoire
                PanierDAO.viderPanier(idClient);        // Vide en BDD
                dispose();                              // Ferme la fenêtre actuelle
                new PanierVue(idClient);                // Recharge la vue vide
            }
        });

        panel.add(Box.createVerticalStrut(20));
        panel.add(viderBtn);

        // Bouton valider commande
        JButton validerBtn = new JButton("Valider la commande");
        validerBtn.addActionListener(e -> {
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
                    dispose(); // Fermer la vue panier
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la validation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(validerBtn);


        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }
}
