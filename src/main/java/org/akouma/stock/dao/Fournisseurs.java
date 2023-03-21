package org.akouma.stock.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.akouma.stock.entity.Fournisseur;
import org.akouma.stock.entity.Boutique;

import java.util.Optional;

public interface Fournisseurs extends CrudRepository<Fournisseur, Long> {
    @Query("select count(f) from Fournisseur f where f.boutique = ?1 and f.isEnabled = ?2")
    long countByBoutiqueAndIsEnabled(Boutique boutique, boolean isEnabled);

    Iterable<Fournisseur> findAllByIdOrderByIdDesc(long longs);

    Iterable<Fournisseur> findByBoutique_IdOrderByIdDesc(Long id);

    Fournisseur findById(long aLong);


}
