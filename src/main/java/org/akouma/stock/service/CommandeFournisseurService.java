package org.akouma.stock.service;

import org.akouma.stock.dao.commandeFournisseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandeFournisseurService {
    @Autowired
    ArticleServices articleServices;
    @Autowired
    private commandeFournisseur commandeFournisseurRespo;
    @Autowired
    private ArticleCommandeFournisseurService articleCommandeFournisseurService;

    public Iterable GetAllCommandeFounisseur(Boutique boutique) {
        return commandeFournisseurRespo.findByUser_BoutiqueOrderByIdDesc(boutique);
    }

    public Iterable getUserCommandeFournisseur(User user) {
        return commandeFournisseurRespo.findByUserOrderByIdDesc(user);
    }

//    public boolean AnnulerCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
//        commandeFournisseur.setAnnulee(true);
//        commandeFournisseurRespo.save(commandeFournisseur);
//        if (RestorerArticleCommandeFournisseur(commandeFournisseur)) {
//            return true;
//        }
//        return false;
//    }

//    private boolean RestorerArticleCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
//        Iterable<ArticleCommandeFournissseur> articles =
//                articleCommandeFournisseurService.GetAllArticlesFournisseur(commandeFournisseur);
//        if (articles != null) {
//            for (ArticleCommandeFournissseur article : articles) {
//                articleServices.DecrementerArticle(article.getArticle(), article.getQuantite());
//            }
//            return true;
//        }
//        return false;
//    }

    public long getNbretransactionsfournisseur(Boutique boutique) {
        return commandeFournisseurRespo.countByArticleCommandeFournissseurs_BoutiqueAndIsAnnulee(boutique, false);
    }


}
