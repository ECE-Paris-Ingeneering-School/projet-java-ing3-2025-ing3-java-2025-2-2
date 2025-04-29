package Vue;

import Modele.Article;
import Modele.Commande;
import Modele.CommandeArticle;
import dao.CommandeDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Classe HistoriqueCommandeVue
 * Affiche l'historique des commandes passées par un client
 * Partie de la vue dans l'architecture MVC
 * Source : <a href="https://grafikart.fr/tutoriels/mvc-model-view-controller-574">Grafikart - Comprendre le modèle MVC</a>
 * @author Martin
 */
public class HistoriqueCommandeVue extends JFrame {
    private int idClient;
    private JFrame previousFrame;

    /**
     * Constructeur de HistoriqueCommandeVue
     * @param idClient identifiant du client connecté
     * @param previousFrame fenêtre précédente à rouvrir lors du retour
     */
    public HistoriqueCommandeVue(int idClient, JFrame previousFrame) {
        this.idClient = idClient;
        this.previousFrame = previousFrame;

        setTitle("📜 Historique de vos commandes");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));

        CommandeDAO commandeDAO = new CommandeDAO();
        List<Commande> commandes = commandeDAO.getCommandesParClient(idClient);

        if (commandes.isEmpty()) {
            JLabel videLabel = new JLabel("Aucune commande trouvée !");
            videLabel.setHorizontalAlignment(SwingConstants.CENTER);
            videLabel.setFont(new Font("Arial", Font.BOLD, 18));
            commandesPanel.add(videLabel);
        } else {
            for (Commande commande : commandes) {
                double totalCommande = 0.0;

                JPanel commandePanel = new JPanel();
                commandePanel.setLayout(new BoxLayout(commandePanel, BoxLayout.Y_AXIS));
                commandePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                JLabel titreCommande = new JLabel("Commande du " + commande.getDateCommande().toLocalDate());
                titreCommande.setFont(new Font("Arial", Font.BOLD, 16));
                commandePanel.add(titreCommande);
                commandePanel.add(Box.createVerticalStrut(10));

                for (CommandeArticle commandeArticle : commande.getArticles()) {
                    JPanel articlePanel = new JPanel(new BorderLayout(5, 5));
                    Article article = commandeArticle.getArticle();

                    double prixTotalArticle = commandeArticle.getPrixTotal();
                    totalCommande += prixTotalArticle;

                    JLabel nomLabel = new JLabel(
                            article.getNom() + " (" +
                                    String.format("%.2f €", article.getPrix_unitaire()) +
                                    ") x " + commandeArticle.getQuantite() +
                                    " = " + String.format("%.2f €", prixTotalArticle)
                    );
                    nomLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    try {
                        URL imgUrl = getClass().getClassLoader().getResource(article.getPhoto());
                        if (imgUrl != null) {
                            ImageIcon imageIcon = new ImageIcon(imgUrl);
                            Image img = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                            JLabel imgLabel = new JLabel(new ImageIcon(img));
                            articlePanel.add(imgLabel, BorderLayout.WEST);
                        } else {
                            articlePanel.add(new JLabel("Image non trouvée"), BorderLayout.WEST);
                        }
                    } catch (Exception e) {
                        articlePanel.add(new JLabel("Erreur image"), BorderLayout.WEST);
                    }

                    articlePanel.add(nomLabel, BorderLayout.CENTER);
                    commandePanel.add(articlePanel);
                    commandePanel.add(Box.createVerticalStrut(5));
                }

                JLabel totalCommandeLabel = new JLabel("Total de la commande : " + String.format("%.2f €", totalCommande));
                totalCommandeLabel.setFont(new Font("Arial", Font.BOLD, 14));
                totalCommandeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                commandePanel.add(totalCommandeLabel);

                commandesPanel.add(commandePanel);
                commandesPanel.add(Box.createVerticalStrut(20));
            }
        }

        JScrollPane scrollPane = new JScrollPane(commandesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton retourBtn = new JButton("←");
        retourBtn.setFont(new Font("Arial", Font.BOLD, 14));
        retourBtn.setBackground(new Color(34, 139, 34));
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.setBorderPainted(false);
        retourBtn.setOpaque(true);

        retourBtn.addActionListener(e -> {
            dispose();
            previousFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(retourBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
