package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.ArtikelModel;

import java.util.List;

public interface ArticleService {
    List<ArtikelModel> findAll();
    void addArticle(ArtikelModel article);
    void updateArticle(ArtikelModel article);
    ArtikelModel findArticleById(Long id);
}
