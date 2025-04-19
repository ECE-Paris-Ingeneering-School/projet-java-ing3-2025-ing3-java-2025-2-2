package Vue;

import Controleur.ClientControleur;

import javax.swing.*;
import java.awt.*;

public class enregistrement extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel messageLabel;

    private ClientControleur controleur;

    public enregistrement() {
        controleur = new ClientControleur();

        setTitle("Inscription - Boutique en ligne");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("S'inscrire");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.NORTH);

        registerButton.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());

            boolean succes = controleur.inscrireClient(nom, prenom, email, mdp, "nouveau");
            if (succes) {
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("✅ Inscription réussie !");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("❌ Erreur lors de l'inscription.");
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
