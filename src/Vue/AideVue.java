package Vue;

import javax.swing.*;
import java.awt.*;

public class AideVue extends JFrame {
    public AideVue() {
        setTitle("Aide & Contact");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet FAQ
        JPanel faqPanel = new JPanel(new BorderLayout());
        String[] questions = {
                "Comment passer commande ?",
                "Quels sont les modes de paiement ?",
                "Délais de livraison ?"
        };
        JList<String> faqList = new JList<>(questions);
        faqPanel.add(new JScrollPane(faqList), BorderLayout.CENTER);
        tabbedPane.addTab("FAQ", faqPanel);

        // Onglet Contact
        JPanel contactPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        contactPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contactPanel.add(new JLabel("Email: support@boutique.com"));
        contactPanel.add(new JLabel("Téléphone: 01 23 45 67 89"));
        contactPanel.add(new JLabel("Horaires: Lun-Ven 9h-18h"));

        JButton btnContact = new JButton("Envoyer un message");
        contactPanel.add(btnContact);

        tabbedPane.addTab("Contact", contactPanel);

        add(tabbedPane);
    }
}