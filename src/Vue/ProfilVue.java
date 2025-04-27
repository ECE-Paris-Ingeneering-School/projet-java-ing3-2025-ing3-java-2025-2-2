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
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (client != null) {
            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            panel.add(new JLabel("Nom :"));
            panel.add(new JLabel(client.getNom()));

            panel.add(new JLabel("Prénom :"));
            panel.add(new JLabel(client.getPrenom()));

            panel.add(new JLabel("Email :"));
            panel.add(new JLabel(client.getEmail()));


            add(panel);
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur : client introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
