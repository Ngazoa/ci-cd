package org.akouma.stock.dao;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.StockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<StockEntity, Long> {
    StockEntity findByArticle(Article article);
    boolean existsByArticle(Article article);

    Page<StockEntity> findByBoutique(Boutique boutique, Pageable pageable);

}
