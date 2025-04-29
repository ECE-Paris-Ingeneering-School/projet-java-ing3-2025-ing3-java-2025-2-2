package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe FenetrePaiement
 * Fenêtre permettant la saisie sécurisée des informations de paiement utilisateur
 * Partie de la vue dans l'architecture MVC
 * Source : <a href="https://grafikart.fr/tutoriels/mvc-model-view-controller-574">Grafikart - Comprendre le modèle MVC</a>
 * @author Jean
 */
public class FenetrePaiement extends JFrame {

    private JTextField numeroCarteField;
    private JTextField expirationField;
    private JTextField cvvField;
    private JButton payerButton;

    /**
     * Constructeur de la fenêtre de paiement
     * @param onPaiementValide action à exécuter lorsque le paiement est validé
     */
    public FenetrePaiement(Runnable onPaiementValide) {
        setTitle("Paiement sécurisé");
        initComponents(onPaiementValide);
        pack();
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialise les composants graphiques de la fenêtre
     * @param onPaiementValide action à exécuter après validation du formulaire
     */
    private void initComponents(Runnable onPaiementValide) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titre = new JLabel("Veuillez entrer vos informations bancaires");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titre, BorderLayout.NORTH);

        JPanel champsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        champsPanel.add(new JLabel("Numéro de carte:"), gbc);
        gbc.gridx = 1;
        numeroCarteField = new JTextField();
        numeroCarteField.setPreferredSize(new Dimension(200, 30));
        champsPanel.add(numeroCarteField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        champsPanel.add(new JLabel("Expiration (MM/AA):"), gbc);
        gbc.gridx = 1;
        expirationField = new JTextField();
        expirationField.setPreferredSize(new Dimension(200, 30));
        champsPanel.add(expirationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        champsPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        cvvField = new JTextField();
        cvvField.setPreferredSize(new Dimension(200, 30));
        champsPanel.add(cvvField, gbc);

        mainPanel.add(champsPanel, BorderLayout.CENTER);

        payerButton = new JButton("Payer");
        payerButton.setFont(new Font("Arial", Font.BOLD, 14));
        payerButton.setBackground(new Color(76, 175, 80));
        payerButton.setForeground(Color.WHITE);
        payerButton.setFocusPainted(false);
        payerButton.setPreferredSize(new Dimension(120, 40));

        payerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroCarte = numeroCarteField.getText().trim();
                String expiration = expirationField.getText().trim();
                String cvv = cvvField.getText().trim();

                if (numeroCarte.length() != 16 || !numeroCarte.matches("\\d{16}")) {
                    JOptionPane.showMessageDialog(FenetrePaiement.this, "Paiement refusé : numéro de carte invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (onPaiementValide != null) {
                    onPaiementValide.run();
                }
                dispose();
            }
        });

        JPanel boutonPanel = new JPanel();
        boutonPanel.add(payerButton);

        mainPanel.add(boutonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
