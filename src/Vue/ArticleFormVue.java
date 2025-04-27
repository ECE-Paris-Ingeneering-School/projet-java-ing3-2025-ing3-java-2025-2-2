package Vue;

import Modele.Administrateur;
import Modele.Article;
import Modele.Marque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticleFormVue extends JDialog {
    private JTextField nomChamp;
    private JTextArea descriptionArea;
    private JTextField prixUnitaireChamp;
    private JTextField prixVracChamp;
    private JTextField quantiteVracChamp;
    private JTextField idMarqueChamp;
    private JTextField photoChamp;
    private JButton sauvegarderButton;
    private JButton retourButton;
    private Administrateur admin;

    private Article article;
    private boolean valide = false;

    public ArticleFormVue(Frame parent, Article article, Administrateur admin) {
        super(parent, true);
        this.admin = admin;
        this.article = article;
        setTitle(article == null ? "Ajouter un article" : "Modifier un article");
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        initUI();
        if (article != null) {
            remplirChamps(article);
        }
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        retourButton = new JButton("←");
        styleButton(retourButton);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(retourButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nom :"), gbc);
        nomChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomChamp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description :"), gbc);
        descriptionArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        panel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Prix Unitaire :"), gbc);
        prixUnitaireChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(prixUnitaireChamp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Prix Vrac :"), gbc);
        prixVracChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(prixVracChamp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Quantité Vrac :"), gbc);
        quantiteVracChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(quantiteVracChamp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("ID Marque :"), gbc);
        idMarqueChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(idMarqueChamp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Photo (chemin) :"), gbc);
        photoChamp = new JTextField(20);
        gbc.gridx = 1;
        panel.add(photoChamp, gbc);

        sauvegarderButton = new JButton("Sauvegarder");
        styleButton(sauvegarderButton);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(sauvegarderButton, gbc);

        add(panel, BorderLayout.CENTER);

        sauvegarderButton.addActionListener(e -> {
            try {
                if (nomChamp.getText().trim().isEmpty()) {
                    throw new Exception("Le nom est obligatoire");
                }
                if (prixUnitaireChamp.getText().trim().isEmpty()) {
                    throw new Exception("Le prix unitaire est obligatoire");
                }

                Double.parseDouble(prixUnitaireChamp.getText());
                if (!prixVracChamp.getText().trim().isEmpty()) {
                    Double.parseDouble(prixVracChamp.getText());
                }
                if (!quantiteVracChamp.getText().trim().isEmpty()) {
                    Integer.parseInt(quantiteVracChamp.getText());
                }
                if (!idMarqueChamp.getText().trim().isEmpty()) {
                    Integer.parseInt(idMarqueChamp.getText());
                }

                valide = true;
                new AdminTabVue(null).setVisible(true);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides", "Erreur de format", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        retourButton.addActionListener(e -> {
            AdminTabVue adminTabVue = new AdminTabVue(admin);
            adminTabVue.setVisible(true);

            dispose();  // Ferme la fenêtre actuelle (AdminProfilVue)
            adminTabVue.toFront();  // Assure-toi que AdminTabVue est au premier plan
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(34, 139, 34));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void remplirChamps(Article art) {
        nomChamp.setText(art.getNom());
        descriptionArea.setText(art.getDescription());
        prixUnitaireChamp.setText(String.valueOf(art.getPrix_unitaire()));
        prixVracChamp.setText(String.valueOf(art.getPrix_vrac()));
        quantiteVracChamp.setText(String.valueOf(art.getQte_vrac()));
        idMarqueChamp.setText(String.valueOf(art.getMarque().getIdMarque()));
        photoChamp.setText(art.getPhoto());
    }

    public boolean isValide() {
        return valide;
    }

    public Article obtenirArticleDesChamps() {
        String nom = nomChamp.getText();
        String description = descriptionArea.getText();
        double prixUnitaire = Double.parseDouble(prixUnitaireChamp.getText());
        double prixVrac = Double.parseDouble(prixVracChamp.getText());
        int quantiteVrac = Integer.parseInt(quantiteVracChamp.getText());
        int idMarque = Integer.parseInt(idMarqueChamp.getText());
        String photo = photoChamp.getText();

        Marque marque = new Marque(idMarque, "");

        if (article == null) {
            article = new Article(0, nom, description, prixUnitaire, prixVrac, quantiteVrac, marque, photo);
        } else {
            article.setNom(nom);
            article.setDescription(description);
            article.setPrix_unitaire(prixUnitaire);
            article.setPrix_vrac(prixVrac);
            article.setQte_vrac(quantiteVrac);
            article.setMarque(marque);
            article.setPhoto(photo);
        }
        return article;
    }
}
