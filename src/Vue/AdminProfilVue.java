package Vue;

import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;

public class AdminProfilVue extends JFrame {
    private Administrateur admin;

    public AdminProfilVue(Administrateur admin) {
        this.admin = admin;

        setTitle("Profil Administrateur");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom :"));
        panel.add(new JLabel(admin.getNom()));

        panel.add(new JLabel("Prénom :"));
        panel.add(new JLabel(admin.getPrenom()));

        panel.add(new JLabel("Email :"));
        panel.add(new JLabel(admin.getEmail()));

        add(panel);
        setVisible(true);
    }
}
