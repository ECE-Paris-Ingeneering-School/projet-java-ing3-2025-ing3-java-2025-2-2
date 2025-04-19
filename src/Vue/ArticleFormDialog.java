package Vue;

import Modele.Article;
import Modele.Marque;

import javax.swing.*;
import java.awt.*;

public class ArticleFormDialog extends JDialog {
    private JTextField nomField;
    private JTextArea descriptionArea;
    private JTextField prixUnitaireField;
    private JTextField prixVracField;
    private JTextField quantiteVracField;
    private JTextField idMarqueField; // simplification
    private JTextField photoField;
    private JButton saveButton;
    private JButton cancelButton;

    private Article article; // Si non null : modification, sinon ajout.
    private boolean validated = false; // Pour savoir si l'utilisateur a validé

    public ArticleFormDialog(Frame parent, Article article) {
        super(parent, true);
        this.article = article;
        setTitle(article == null ? "Ajouter un article" : "Modifier un article");
        initUI();
        if (article != null) {
            populateFields(article);
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

        panel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Description :"));
        descriptionArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(descriptionArea));

        panel.add(new JLabel("Prix Unitaire :"));
        prixUnitaireField = new JTextField();
        panel.add(prixUnitaireField);

        panel.add(new JLabel("Prix Vrac :"));
        prixVracField = new JTextField();
        panel.add(prixVracField);

        panel.add(new JLabel("Quantité Vrac :"));
        quantiteVracField = new JTextField();
        panel.add(quantiteVracField);

        panel.add(new JLabel("ID Marque :"));
        idMarqueField = new JTextField();
        panel.add(idMarqueField);

        panel.add(new JLabel("Photo (chemin) :"));
        photoField = new JTextField();
        panel.add(photoField);

        saveButton = new JButton("Sauvegarder");
        cancelButton = new JButton("Annuler");
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            try {
                // Validation des champs obligatoires
                if (nomField.getText().trim().isEmpty()) {
                    throw new Exception("Le nom est obligatoire");
                }
                if (prixUnitaireField.getText().trim().isEmpty()) {
                    throw new Exception("Le prix unitaire est obligatoire");
                }

                // Validation des nombres
                Double.parseDouble(prixUnitaireField.getText());
                if (!prixVracField.getText().trim().isEmpty()) {
                    Double.parseDouble(prixVracField.getText());
                }
                if (!quantiteVracField.getText().trim().isEmpty()) {
                    Integer.parseInt(quantiteVracField.getText());
                }
                if (!idMarqueField.getText().trim().isEmpty()) {
                    Integer.parseInt(idMarqueField.getText());
                }

                validated = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides", "Erreur de format", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> {
            validated = false;
            dispose();
        });
    }

    private void populateFields(Article art) {
        nomField.setText(art.getNom());
        descriptionArea.setText(art.getDescription());
        prixUnitaireField.setText(String.valueOf(art.getPrix_unitaire()));
        prixVracField.setText(String.valueOf(art.getPrix_vrac()));
        quantiteVracField.setText(String.valueOf(art.getQte_vrac()));
        idMarqueField.setText(String.valueOf(art.getMarque().getIdMarque()));
        photoField.setText(art.getPhoto());
    }

    public boolean isValidated() {
        return validated;
    }

    public Article getArticleFromFields() {
        String nom = nomField.getText();
        String description = descriptionArea.getText();
        double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
        double prixVrac = Double.parseDouble(prixVracField.getText());
        int quantiteVrac = Integer.parseInt(quantiteVracField.getText());
        int idMarque = Integer.parseInt(idMarqueField.getText());
        String photo = photoField.getText();

        // On crée un objet Marque simplifié (id uniquement dans cet exemple)
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
