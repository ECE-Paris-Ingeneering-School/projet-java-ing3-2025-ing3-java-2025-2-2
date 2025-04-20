package Vue;

import javax.swing.*;
import java.awt.*;

public class DetailCommandeVue extends JFrame {
    public DetailCommandeVue(String numCommande) {
        setTitle("Détail commande " + numCommande);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // En-tête
        JPanel headerPanel = new JPanel(new GridLayout(0, 2));
        headerPanel.add(new JLabel("N° Commande:"));
        headerPanel.add(new JLabel(numCommande));
        headerPanel.add(new JLabel("Date:"));
        headerPanel.add(new JLabel("2025-05-15")); // Exemple
        panel.add(headerPanel, BorderLayout.NORTH);

        // Liste des articles
        String[] articles = {"T-shirt Blanc x2", "Jean Slim x1", "Chaussettes x3"};
        JList<String> articleList = new JList<>(articles);
        panel.add(new JScrollPane(articleList), BorderLayout.CENTER);

        // Total
        JLabel totalLabel = new JLabel("Total: 129.99 €", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(totalLabel, BorderLayout.SOUTH);

        add(panel);
    }
}