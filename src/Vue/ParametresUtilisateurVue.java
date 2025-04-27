package Vue;

import javax.swing.*;
import java.awt.*;

public class ParametresUtilisateurVue extends JFrame {
    public ParametresUtilisateurVue(int idClient) {
        setTitle("Paramètres du compte");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Champs éditables
        panel.add(new JLabel("Nom:"));
        panel.add(new JTextField("Dupont"));

        panel.add(new JLabel("Prénom:"));
        panel.add(new JTextField("Jean"));

        panel.add(new JLabel("Email:"));
        panel.add(new JTextField("jean.dupont@example.com"));

        panel.add(new JLabel("Mot de passe:"));
        panel.add(new JPasswordField());

        // Boutons
        JButton btnSave = new JButton("Enregistrer");
        JButton btnCancel = new JButton("Annuler");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}