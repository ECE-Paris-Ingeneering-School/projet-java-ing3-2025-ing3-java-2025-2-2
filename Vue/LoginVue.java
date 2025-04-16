package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue pour la connexion et l'inscription des clients
 */
public class LoginVue extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginVue() {
        setTitle("Connexion - Boutique en ligne");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Se connecter");
        panel.add(loginButton);

        registerButton = new JButton("S'inscrire");
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        // Exemple d'action pour le bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Ici, vous appelleriez le contrôleur pour gérer la connexion
                // ClientController controller = new ClientController();
                // Client client = controller.connecterClient(email, password);
                // if (client != null) { ouvrir la vue principale... }
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}