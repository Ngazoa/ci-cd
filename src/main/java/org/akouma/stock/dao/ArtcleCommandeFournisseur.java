package org.akouma.stock.dao;

import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArtcleCommandeFournisseur extends CrudRepository<ArticleCommandeFournissseur, Long> {

    Page<ArticleCommandeFournissseur> findByFournisseurOrderByIdDesc(Fournisseur fournisseur,Pageable pageable);
    Iterable<ArticleCommandeFournissseur> findByCommandeFournisseurOrderByIdDesc(ArticleCommandeFournissseur commandeFournisseur);

    Iterable<ArticleCommandeFournissseur> findByCommandeFournisseur_User_Boutique(Boutique boutique);

    Page<ArticleCommandeFournissseur> findByBoutiqueOrderByIdDesc(Boutique boutique, Pageable pageable);

}
