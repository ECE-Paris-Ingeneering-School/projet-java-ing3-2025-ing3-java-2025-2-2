package Vue;

import Controleur.AdminController;
import Modele.Administrateur;
import dao.ConnexionBDD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AdminLoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton retourButton;
    private JLabel titleLabel;
    private JLabel messageLabel;

    private AdminController controller;

    public AdminLoginVue(Connection connection) {
        controller = new AdminController(connection);

        setTitle("Connexion Admin - ShopECE");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        retourButton = new JButton("←");
        retourButton.setPreferredSize(new Dimension(100, 40));
        retourButton.setBackground(new Color(34, 139, 34));
        retourButton.setFont(new Font("Arial", Font.BOLD, 12));
        retourButton.setForeground(Color.WHITE);
        retourButton.setFocusPainted(false);

        titleLabel = new JLabel("Connexion Administrateur", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(800, 60));

        messageLabel = new JLabel("", SwingConstants.CENTER);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        northPanel.add(retourButton, BorderLayout.WEST);
        northPanel.add(titleLabel, BorderLayout.CENTER);
        northPanel.add(messageLabel, BorderLayout.SOUTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        emailField = new JTextField(25);
        passwordField = new JPasswordField(25);
        emailField.setPreferredSize(new Dimension(300, 30));
        passwordField.setPreferredSize(new Dimension(300, 30));

        loginButton = new JButton("Connexion Admin");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(loginButton, gbc);

        add(northPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginVue().setVisible(true);
                dispose();
            }
        });
    }

    private void handleLogin() {
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());

        Administrateur admin = controller.seConnecter(email, motDePasse);
        if (admin != null) {
            dispose();
            new AdminTabVue(admin).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants invalides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
