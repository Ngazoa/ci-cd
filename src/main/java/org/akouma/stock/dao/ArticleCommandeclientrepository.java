package org.akouma.stock.dao;

import org.akouma.stock.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ArticleCommandeclientrepository extends CrudRepository<ArticleCommandeClient, Long> {
    Iterable<ArticleCommandeClient> findByCommandeClientOrderByIdDesc(CommandeClient commandeClient);

    Iterable<ArticleCommandeClient> findByArticleAndDateCreationBetweenOrderByIdDesc(Article article, Date dateJourMin, Date dateJourMax);
    Iterable<ArticleCommandeClient> findAllByDateCreationBetweenOrderByIdDesc( Date dateJourMin, Date dateJourMax);

}
