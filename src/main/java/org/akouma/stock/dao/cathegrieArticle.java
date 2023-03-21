package org.akouma.stock.dao;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CathegorieArticle;
import org.springframework.data.repository.CrudRepository;


public interface cathegrieArticle extends CrudRepository<CathegorieArticle, Long> {
    Iterable<CathegorieArticle> findByBoutique_Id(Long id);

}
