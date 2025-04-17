package Vue;

import Modele.Article;
import dao.ArticleDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArticleVue extends JFrame {

    public ArticleVue() {
        setTitle("Catalogue des Articles");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ArticleDAO articleDAO = new ArticleDAO();
        List<Article> articles = articleDAO.listerArticles();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Article a : articles) {
            model.addElement(a.toString());
        }

        JList<String> articleList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(articleList);

        add(scrollPane, BorderLayout.CENTER);
    }
}
