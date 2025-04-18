package Vue;

import Modele.Article;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PanierVue extends JFrame {
    public PanierVue() {
        setTitle("Mon Panier");
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Map<Article, Integer> articles = Panier.getInstance().getArticles();

        if (articles.isEmpty()) {
            panel.add(new JLabel("Votre panier est vide."));
        } else {
            for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
                Article article = entry.getKey();
                int quantite = entry.getValue();

                JPanel ligne = new JPanel(new BorderLayout());
                ligne.add(new JLabel(article.getNom() + " x" + quantite), BorderLayout.WEST);
                ligne.add(new JLabel(String.format("%.2f €", article.getPrix_unitaire() * quantite)), BorderLayout.EAST);

                panel.add(ligne);
            }

            JLabel totalLabel = new JLabel("Total : " + String.format("%.2f €", Panier.getInstance().getTotal()), SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(Box.createVerticalStrut(10));
            panel.add(totalLabel);
        }

        JButton viderBtn = new JButton("Vider le panier");
        viderBtn.addActionListener(e -> {
            Panier.getInstance().viderPanier();
            dispose();
            new PanierVue().setVisible(true);
        });

        panel.add(Box.createVerticalStrut(20));
        panel.add(viderBtn);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }
}
