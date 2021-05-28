package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.ArtikelModel;
import propensi.d06.sihedes.repository.ArticleDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    ArticleDb articleDb;

    @Override
    public List<ArtikelModel> findAll() {
        return articleDb.findAll();
    }

    @Override
    public void addArticle(ArtikelModel article) {
        articleDb.save(article);
    }

    @Override
    public void updateArticle(ArtikelModel article) {
        articleDb.save(article);
    }

    @Override
    public ArtikelModel findArticleById(Long id) {
        return articleDb.findById(id).get();
    }
}
