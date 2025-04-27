package Vue;

import Modele.Client;
import dao.ClientDAO;

import javax.swing.*;
import java.awt.*;

public class ProfilVue extends JFrame {
    private int idClient;
    private Client client;

    public ProfilVue(int idClient) {
        this.idClient = idClient;
        ClientDAO clientDAO = new ClientDAO();
        client = clientDAO.trouverParId(idClient);

        setTitle("Informations personnelles");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton retourBtn = new JButton("←");
        retourBtn.setBackground(new Color(34, 139, 34));
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.addActionListener(e -> {
            new ArticleVue(idClient);
            dispose();
        });
        topPanel.add(retourBtn);
        mainPanel.add(topPanel);

        mainPanel.add(Box.createVerticalStrut(20));

        if (client != null) {
            JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setPreferredSize(new Dimension(600, 300));

            infoPanel.add(new JLabel("Nom :"));
            infoPanel.add(new JLabel(client.getNom()));

            infoPanel.add(new JLabel("Prénom :"));
            infoPanel.add(new JLabel(client.getPrenom()));

            infoPanel.add(new JLabel("Email :"));
            infoPanel.add(new JLabel(client.getEmail()));

            JPanel centeredPanel = new JPanel();
            centeredPanel.setLayout(new BorderLayout());
            centeredPanel.add(infoPanel, BorderLayout.CENTER);

            mainPanel.add(centeredPanel);

            JButton modifierBtn = new JButton("Modifier");
            modifierBtn.setBackground(new Color(34, 139, 34));
            modifierBtn.setForeground(Color.WHITE);
            modifierBtn.setFocusPainted(false);
            modifierBtn.addActionListener(e -> {
                afficherFormulaireModification();
            });
            mainPanel.add(modifierBtn);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur : client introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);

        setVisible(true);
    }

    private void afficherFormulaireModification() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomField = new JTextField(client.getNom());
        JTextField prenomField = new JTextField(client.getPrenom());
        JTextField emailField = new JTextField(client.getEmail());

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(nomField);

        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(prenomField);

        formPanel.add(new JLabel("Email :"));
        formPanel.add(emailField);

        int option = JOptionPane.showConfirmDialog(this, formPanel, "Modifier vos informations", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String nouveauNom = nomField.getText();
            String nouveauPrenom = prenomField.getText();
            String nouvelEmail = emailField.getText();

            client.setNom(nouveauNom);
            client.setPrenom(nouveauPrenom);
            client.setEmail(nouvelEmail);

            ClientDAO clientDAO = new ClientDAO();
            if (clientDAO.mettreAJourClient(client)) {
                JOptionPane.showMessageDialog(this, "Informations mises à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new ProfilVue(idClient);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour des informations.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
