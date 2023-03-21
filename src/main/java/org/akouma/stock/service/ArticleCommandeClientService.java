package org.akouma.stock.service;

import org.akouma.stock.dao.ArticleCommandeclientrepository;
import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.ArticleCommandeClient;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleCommandeClientService {
    @Autowired
    private ArticleCommandeclientrepository articleCommandeclientrepository;

    public void savearticlecommande(ArticleCommandeClient articleCommandeClient){
       articleCommandeclientrepository.save(articleCommandeClient);
    }
    public Iterable<ArticleCommandeClient> getarticleCommande(CommandeClient commandeClient){
        return articleCommandeclientrepository.findByCommandeClientOrderByIdDesc(commandeClient);
    }

    public Iterable<ArticleCommandeClient> getarticleByVenteEntredeuxdates(Boutique boutique,Article article, Date dateJourMin, Date dateJourMax){

       if(article==null){
           return articleCommandeclientrepository.findAllByDateCreationBetweenOrderByIdDesc(dateJourMin,dateJourMax);
       }
        return articleCommandeclientrepository.findByArticleAndDateCreationBetweenOrderByIdDesc(article,dateJourMin,dateJourMax);
    }


}
