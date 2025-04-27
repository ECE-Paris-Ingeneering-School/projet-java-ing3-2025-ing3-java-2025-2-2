package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetrePaiement extends JFrame {

    private JTextField numeroCarteField;
    private JTextField expirationField;
    private JTextField cvvField;
    private JButton payerButton;

    public FenetrePaiement(Runnable onPaiementValide) {
        setTitle("Paiement sécurisé");
        initComponents(onPaiementValide);
        pack(); // Ajuste automatiquement après avoir ajouté tous les composants
        setSize(450, 350); // Définit une bonne taille de base
        setLocationRelativeTo(null); // Centre la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents(Runnable onPaiementValide) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titre
        JLabel titre = new JLabel("Veuillez entrer vos informations bancaires");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titre, BorderLayout.NORTH);

        // Centre - Champs
        JPanel champsPanel = new JPanel();
        champsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        champsPanel.add(new JLabel("Numéro de carte:"), gbc);
        gbc.gridx = 1;
        numeroCarteField = new JTextField();
        numeroCarteField.setPreferredSize(new Dimension(200, 30)); // Fixe la taille
        champsPanel.add(numeroCarteField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        champsPanel.add(new JLabel("Expiration (MM/AA):"), gbc);
        gbc.gridx = 1;
        expirationField = new JTextField();
        expirationField.setPreferredSize(new Dimension(200, 30)); // Fixe la taille
        champsPanel.add(expirationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        champsPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        cvvField = new JTextField();
        cvvField.setPreferredSize(new Dimension(200, 30)); // Fixe la taille
        champsPanel.add(cvvField, gbc);

        mainPanel.add(champsPanel, BorderLayout.CENTER);

        // Bas - Bouton Payer
        payerButton = new JButton("Payer");
        payerButton.setFont(new Font("Arial", Font.BOLD, 14));
        payerButton.setBackground(new Color(76, 175, 80)); // Vert
        payerButton.setForeground(Color.WHITE);
        payerButton.setFocusPainted(false);
        payerButton.setPreferredSize(new Dimension(120, 40));

        payerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
