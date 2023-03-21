package org.akouma.stock.dao;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CathegorieArticle;
import org.akouma.stock.entity.Fournisseur;
import org.springframework.data.repository.CrudRepository;
import org.akouma.stock.entity.Article;

public interface Articles extends CrudRepository<Article, Long> {
    boolean existsByBoutiqueAndName(Boutique boutique, String name);
    long countByIsActiveAndBoutique(boolean isActive, Boutique boutique);
    long countDistinctByCathegorieArticle(CathegorieArticle cathegorieArticle);

    Iterable<Article> findAllByFournisseur(Fournisseur longs);

    Iterable<Article> findAllByBoutiqueAndIsEnabled(Boutique longs,boolean a);

}
