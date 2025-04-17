package Vue;

import Controleur.ClientControleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue pour l'inscription des clients
 */
public class Inscription extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField mdpField;
    private JComboBox<String> typeClientBox;
    private JButton inscrireButton;

    private ClientControleur controleur;

    public Inscription() {
        controleur = new ClientControleur();

        setTitle("Inscription - Boutique en ligne");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Champs de formulaire
        panel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        panel.add(nomField);

        panel.add(new JLabel("Prénom:"));
        prenomField = new JTextField();
        panel.add(prenomField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe:"));
        mdpField = new JPasswordField();
        panel.add(mdpField);

        panel.add(new JLabel("Type de client:"));
        typeClientBox = new JComboBox<>(new String[]{"nouveau", "ancien"});
        panel.add(typeClientBox);

        inscrireButton = new JButton("S'inscrire");
        panel.add(inscrireButton);

        add(panel, BorderLayout.CENTER);

        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String mdp = new String(mdpField.getPassword());
                String type = (String) typeClientBox.getSelectedItem();

                boolean success = controleur.inscrireClient(nom, prenom, email, mdp, type);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Inscription réussie !");
                    dispose(); // Fermer la fenêtre après succès
                } else {
                    JOptionPane.showMessageDialog(null, "Échec de l'inscription. Vérifiez les champs ou l'email.");
                }
            }
        });
    }

    public void afficher() {
        setVisible(true);
    }
}
