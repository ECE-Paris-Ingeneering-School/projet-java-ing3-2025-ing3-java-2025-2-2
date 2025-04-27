package Vue;

import Modele.Article;
import Modele.Commande;
import Modele.CommandeArticle;
import dao.CommandeDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class HistoriqueCommandeVue extends JFrame {
    private int idClient;
    private JFrame previousFrame;

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
                JPanel commandePanel = new JPanel();
                commandePanel.setLayout(new BoxLayout(commandePanel, BoxLayout.Y_AXIS));
                commandePanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                JLabel titreCommande = new JLabel("Commande du " + commande.getDateCommande().toLocalDate() +
                        " - Total : " + String.format("%.2f €", commande.getPrixTotal()));
                titreCommande.setFont(new Font("Arial", Font.BOLD, 16));
                commandePanel.add(titreCommande);

                commandePanel.add(Box.createVerticalStrut(10));

                for (CommandeArticle commandeArticle : commande.getArticles()) {
                    JPanel articlePanel = new JPanel(new BorderLayout(5, 5));
                    Article article = commandeArticle.getArticle();

                    JLabel nomLabel = new JLabel(
                            article.getNom() +
                                    " (" + String.format("%.2f €", article.getPrix_unitaire()) +
                                    ") x " + commandeArticle.getQuantite() +
                                    " = " + String.format("%.2f €", commandeArticle.getPrixTotal())
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
                            JLabel imgLabel = new JLabel("Image non trouvée");
                            articlePanel.add(imgLabel, BorderLayout.WEST);
                        }
                    } catch (Exception e) {
                        articlePanel.add(new JLabel("Erreur image"), BorderLayout.WEST);
                    }

                    articlePanel.add(nomLabel, BorderLayout.CENTER);
                    commandePanel.add(articlePanel);
                    commandePanel.add(Box.createVerticalStrut(5));
                }

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

