package org.akouma.stock.service;

import org.akouma.stock.dao.ArtcleCommandeFournisseur;
import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.Fournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleCommandeFournisseurService {
    @Autowired
    private ArtcleCommandeFournisseur artcleCommandeFournisseurRepo;
    @Autowired
    private StockService stockService;

    //    public Iterable GetAllArticlesFournisseur(CommandeFournisseur commandeFournisseur){
//        return artcleCommandeFournisseurRepo.findByCommandeFournisseur(commandeFournisseur);
//    }
    public void saveArticle(ArticleCommandeFournissseur artcleCommandeFournisseur,boolean firtTime) {
        artcleCommandeFournisseurRepo.save(artcleCommandeFournisseur);
        stockService.IncrementerArticle(artcleCommandeFournisseur,artcleCommandeFournisseur.getQuantite(),firtTime);
    }

    public Page<ArticleCommandeFournissseur> getAllCommandeFournisseurBoutique(Boutique boutique, int max, Integer page) {
        return artcleCommandeFournisseurRepo.findByBoutiqueOrderByIdDesc(boutique, PageRequest.of(page, max));
    }

    public boolean annulerCommandeFournisseur(ArticleCommandeFournissseur articleCommandeFournissseur) {

        if (RestorerArticleCommandeFournisseur(articleCommandeFournissseur)) {
            articleCommandeFournissseur.setAnnule(true);
            artcleCommandeFournisseurRepo.save(articleCommandeFournissseur);
            return true;
        }
        return false;
    }

    private boolean RestorerArticleCommandeFournisseur(ArticleCommandeFournissseur commandeFournisseur) {
        if (stockService.DecrementerArticle(commandeFournisseur, commandeFournisseur.getQuantite())) {
            return true;
        }
        return false;
    }
    public Page<ArticleCommandeFournissseur> getCommmadesFouniiseurpar(Fournisseur fournisseur,int max, Integer page){
        return artcleCommandeFournisseurRepo.findByFournisseurOrderByIdDesc(fournisseur,PageRequest.of(page, max));
    }
}
