package org.akouma.stock.service;

import org.akouma.stock.dao.StockRepository;
import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.StockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public  boolean DecrementerArticle(ArticleCommandeFournissseur articleCommandeFournissseur, int nbreAffecte) {
       StockEntity stock=stockRepository.findByArticle(articleCommandeFournissseur.getArticle());
        int resultat = stock.getQuantite() - nbreAffecte;
        if (resultat >= 0) {
            stock.setQuantite(resultat);
            stockRepository.save(stock);
            return true;
        }
        return false;
    }
    public  boolean DecrementerArticleDansleStock(Article article, int nbreAffecte) {
        StockEntity stock=stockRepository.findByArticle(article);
        int resultat = stock.getQuantite() - nbreAffecte;
        if (resultat >= 0) {
            stock.setQuantite(resultat);
            stockRepository.save(stock);
            return true;
        }
        return false;
    }
    public  boolean incrementerArticleDansleStock(Article article, int nbreAffecte) {
        StockEntity stock=stockRepository.findByArticle(article);
        int resultat = stock.getQuantite() + nbreAffecte;
        if (resultat >= 0) {
            stock.setQuantite(resultat);
            stockRepository.save(stock);
            return true;
        }
        return false;
    }


    public boolean IncrementerArticle(ArticleCommandeFournissseur articleCommandeFournissseur, int nbreAffecte,boolean firstTime) {
        StockEntity stock=stockRepository.findByArticle(articleCommandeFournissseur.getArticle());
        int resultat =0;
        if(!firstTime) {
            resultat = stock.getQuantite() + articleCommandeFournissseur.getQuantite();
            if (resultat >= 0) {
                stock.setQuantite(resultat);
                stockRepository.save(stock);
                return true;
            }
            return false;
        }
        return true;
    }
    public boolean getArticleStoc(Article article){
      return   stockRepository.existsByArticle(article);
    }
    public StockEntity getArticleStock(Article article){
        return   stockRepository.findByArticle(article);
    }
    public void SaveStock(StockEntity stockEntity){
        stockRepository.save(stockEntity);
    }

    public Page<StockEntity> getStockBoutique(Boutique boutique,int max,Integer page){
     return  stockRepository.findByBoutique(boutique, PageRequest.of(page, max));
    }
}
