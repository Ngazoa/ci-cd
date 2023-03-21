package org.akouma.stock.service;

import org.akouma.stock.dao.ArticleCommandeclientrepository;
import org.akouma.stock.dao.CommandeClients;
import org.akouma.stock.entity.ArticleCommandeClient;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeClient;
import org.akouma.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommandeClientService {
    @Autowired
    private CommandeClients commandeClientsPespo;
    @Autowired
    ArticleCommandeclientrepository articleCommandeClient;
    @Autowired
    ArticleServices articleServices;

    public Page<CommandeClient> getAllListCommandeClient(Boutique boutique, int max, Integer page) {
        return commandeClientsPespo.findByBoutiqueOrderByIdDesc(boutique, PageRequest.of(page, max));
    }

    public Page<CommandeClient> getAllListCommandeClientstatutannule(Boutique boutique, int max, Integer page) {
        return commandeClientsPespo.findByBoutiqueAndIsAnnuleeOrderByIdDesc(boutique,true, PageRequest.of(page, max));
    }

    public Iterable getUserLIstCommane(User user) {
        return commandeClientsPespo.findAllByUserOrderByIdDesc(user);
    }

    public boolean AnnulerCommandeClient(CommandeClient commandeClient) {
        commandeClient.setAnnulee(true);
        commandeClientsPespo.save(commandeClient);
        if (RestorerArticleCommandeFournisseur(commandeClient)) {
            return true;
        }
        return false;
    }

    private boolean RestorerArticleCommandeFournisseur(CommandeClient commandeClient) {

        Iterable<ArticleCommandeClient> articles =
                articleCommandeClient.findByCommandeClientOrderByIdDesc(commandeClient);
        if (articles != null) {
            for (ArticleCommandeClient article : articles) {
                articleServices.DecrementerArticle(article.getArticle(), article.getQuantite());
            }
            return true;
        }
        return false;
    }

    public void savecommande(CommandeClient commandeClient) {
        commandeClientsPespo.save(commandeClient);
    }


    public long getNbretransactionscliens(Boutique boutique){
        return commandeClientsPespo.countByBoutiqueAndIsAnnulee(boutique,false);
    }
    public long getNbretransactionscliensByUer(User user){
        return commandeClientsPespo.countByIsAnnuleeAndUser(false,user);
    }

    public Float getSommeEntreeClient(Boutique boutique){
        return commandeClientsPespo.montantTotal(boutique);
    }
    public Float getSommeCommandeByUser(User user){
        return commandeClientsPespo.montantTotalByuserTransactions(user);
    }
}
