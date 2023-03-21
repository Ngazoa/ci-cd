package org.akouma.stock.service;

import org.akouma.stock.dao.Articles;
import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.Boutique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServices {
    @Autowired
    private Articles articlesRepo;

    public boolean AjouterArticleDansBoutique(Article articles) {
        if (articlesRepo.save(articles) != null) {
            return true;
        }
        return false;
    }

    public Iterable<Article> GetAllArticleBoutique(Boutique boutique) {
        return articlesRepo.findAllByBoutiqueAndIsEnabled(boutique,true);
    }

    public boolean ActiverOuDesactiverArticle(Article article) {
        if (article.isEnabled()) {
            article.setActive(false);
            articlesRepo.save(article);
            return true;
        }
        article.setActive(true);
        articlesRepo.save(article);
        return false;
    }

    public long getArticleCount(Boutique boutique) {
        return articlesRepo.countByIsActiveAndBoutique(true, boutique);
    }

    public boolean DecrementerArticle(Article article, int nbreAffecte) {

        int resultat = article.getQuantite() - nbreAffecte;
        if (resultat >= 0) {
            article.setQuantite(resultat);
            articlesRepo.save(article);
            return true;
        }
        return false;
    }
    public boolean deleteArticle(Article article){
        if(article.isEnabled()){
            article.setEnabled(false);
            articlesRepo.save(article);
            return true;
        }
        article.setEnabled(true);
        articlesRepo.save(article);
        return false;
    }
    public Article GetArticleById(Article article){
        return articlesRepo.findById(article.getId()).get();
    }
    public Article GetArticleById2(Long id){
        return articlesRepo.findById(id).get();
    }
    public boolean IncrementerArticle(Article article, int nbreAffecte) {
        int resultat = article.getQuantite() + nbreAffecte;
        if (resultat >= 0) {
            article.setQuantite(resultat);
            articlesRepo.save(article);
            return true;
        }
        return false;
    }
    public boolean checckArticleByBoutique( Boutique boutique,String namearticle){
       return articlesRepo.existsByBoutiqueAndName(boutique,namearticle);
    }
}
