package Vue;

import Modele.Administrateur;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre affichant le profil de l'administrateur. Permet à l'administrateur
 * de voir ses informations personnelles (nom, prénom, email).
 *
 * @author Jean
 * @source https://openclassrooms.com/fr/courses/2434016-developpez-des-sites-web-avec-java-ee/2438576-le-modele-mvc
 */
public class AdminProfilVue extends JFrame {
    private Administrateur admin;
    Color greenColor = new Color(34, 139, 34);

    /**
     * Constructeur de la fenêtre du profil administrateur.
     *
     * @param admin L'administrateur dont les informations sont affichées.
     */
    public AdminProfilVue(Administrateur admin) {
        this.admin = admin;

        setTitle("Profil Administrateur");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    /**
     * Initialise l'interface utilisateur pour afficher les informations du profil.
     * Configure les composants graphiques et la disposition de la fenêtre.
     */
    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        JButton retourBtn = new JButton("←");
        retourBtn.setFont(new Font("Arial", Font.BOLD, 14));
        retourBtn.setBackground(greenColor);
        retourBtn.setForeground(Color.WHITE);
        retourBtn.setFocusPainted(false);
        retourBtn.setPreferredSize(new Dimension(120, 40));

        retourBtn.addActionListener(e -> {
            dispose();
            new AdminTabVue(admin).setVisible(true);
        });

        topPanel.add(retourBtn);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titre = new JLabel("Profil Administrateur");
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setForeground(greenColor);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(titre);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        detailsPanel.add(new JLabel("Nom :"));
        detailsPanel.add(new JLabel(admin.getNom()));

        detailsPanel.add(new JLabel("Prénom :"));
        detailsPanel.add(new JLabel(admin.getPrenom()));

        detailsPanel.add(new JLabel("Email :"));
        detailsPanel.add(new JLabel(admin.getEmail()));

        infoPanel.add(detailsPanel);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);

        getContentPane().add(panel);

        setVisible(true);
    }
}