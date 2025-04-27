package Vue;

import Controleur.ClientControleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnregistrementVue extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel messageLabel;
    private JLabel alreadyAccountLabel;

    private ClientControleur controleur;

    public EnregistrementVue() {
        controleur = new ClientControleur();

        setTitle("Inscription - ShopECE");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Inscription", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(1000, 60));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        nomField = new JTextField(30);
        prenomField = new JTextField(30);
        emailField = new JTextField(30);
        passwordField = new JPasswordField(30);
        registerButton = new JButton("S'inscrire");

        registerButton.setPreferredSize(new Dimension(200, 40));
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        alreadyAccountLabel = new JLabel("<html><u>J'ai déjà un compte</u></html>");
        alreadyAccountLabel.setForeground(new Color(0, 128, 0));  // Vert
        alreadyAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nom :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Prénom :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Email :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(alreadyAccountLabel, gbc);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String mdp = new String(passwordField.getPassword());

            boolean success = controleur.inscrireClient(nom, prenom, email, mdp, "nouveau");
            messageLabel = new JLabel("", SwingConstants.CENTER);
            if (success) {
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Inscription réussie !");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Erreur lors de l'inscription");
            }
            add(messageLabel, BorderLayout.SOUTH);
            revalidate();
            repaint();
        });

        alreadyAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginVue().afficher();
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
