package org.akouma.stock.dao;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeFournisseur;
import org.akouma.stock.entity.Fournisseur;
import org.akouma.stock.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface commandeFournisseur extends CrudRepository<CommandeFournisseur, Long> {

    @Query("select count(c) from CommandeFournisseur c left join c.articleCommandeFournissseurs articleCommandeFournissseurs where articleCommandeFournissseurs.boutique = ?1 and c.isAnnulee = ?2")
    long countByArticleCommandeFournissseurs_BoutiqueAndIsAnnulee(Boutique boutique, boolean isAnnulee);

    Iterable<CommandeFournisseur> findByArticleCommandeFournissseurs_FournisseurOrderByIdDesc(Fournisseur fournisseur);
    Iterable<CommandeFournisseur> findByUserOrderByIdDesc(User user);

    Iterable<CommandeFournisseur> findByUser_BoutiqueOrderByIdDesc(Boutique boutique);

}
