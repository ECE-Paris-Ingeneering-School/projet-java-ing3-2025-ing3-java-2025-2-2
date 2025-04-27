package Vue;

import Modele.Article;
import Modele.Commande;
import dao.CommandeDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoriqueCommandeVue extends JFrame {

    public HistoriqueCommandeVue(int idClient) {
        setTitle("Historique des commandes");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        CommandeDAO commandeDAO = new CommandeDAO();
        List<Commande> commandes = commandeDAO.getCommandesParClient(idClient);

        if (commandes.isEmpty()) {
            mainPanel.add(new JLabel("Aucune commande trouvée."));
        } else {
            for (Commande commande : commandes) {
                JPanel commandePanel = new JPanel();
                commandePanel.setLayout(new BoxLayout(commandePanel, BoxLayout.Y_AXIS));
                commandePanel.setBorder(BorderFactory.createTitledBorder(
                        "Commande du " + commande.getDateCommande() + " - Total : " + String.format("%.2f €", commande.getPrixTotal())
                ));

                for (Article article : commande.getArticles()) {
                    JLabel articleLabel = new JLabel("• " + article.getNom() + " - " + article.getPrix_unitaire() + " €");
                    commandePanel.add(articleLabel);
                }

                mainPanel.add(commandePanel);
                mainPanel.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
        setVisible(true);
    }
}
