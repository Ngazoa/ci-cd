package org.akouma.stock.service;

import org.akouma.stock.dao.cathegrieArticle;
import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CathegorieArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatheorieServices {
    @Autowired
    private cathegrieArticle cathegrieArticleRepo;

    public Iterable<CathegorieArticle> getAllCategories(Boutique boutique){
        return cathegrieArticleRepo.findByBoutique_Id(boutique.getId());
    }
    public String SaveCategorie(CathegorieArticle cathegorieArticle){
        if(cathegrieArticleRepo.save(cathegorieArticle)!=null){
            return "Succes ";
        }
        return "Erreur";
    }
    public CathegorieArticle getArticleCategorie(CathegorieArticle cathegorieArticle){
        return  cathegrieArticleRepo.findById(cathegorieArticle.getId()).get();
    }
    public boolean SuppreimerCathegorie(CathegorieArticle cathegorieArticle){
        if(cathegorieArticle.isEnabled()){
            cathegorieArticle.setEnabled(false);
            cathegrieArticleRepo.save(cathegorieArticle);
            return true;
        }
        cathegorieArticle.setEnabled(true);
        cathegrieArticleRepo.save(cathegorieArticle);
        return false;
    }


}
