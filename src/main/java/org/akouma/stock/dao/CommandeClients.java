package org.akouma.stock.dao;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.akouma.stock.entity.CommandeClient;

import java.util.Collection;
import java.util.List;

public interface CommandeClients extends CrudRepository<CommandeClient, Long> {

    Long countByIsAnnuleeAndUser(boolean isAnnulee, User user);


    Long countByBoutiqueAndIsAnnulee(Boutique boutique, boolean isAnnulee);

    @Query("select sum(c.prixTotal) from CommandeClient c where c.boutique = ?1")
    Float montantTotal(Boutique boutique);

    @Query("select sum(c.prixTotal) from CommandeClient c where c.user = ?1")
    Float montantTotalByuserTransactions(User user);


    Page<CommandeClient> findByBoutiqueOrderByIdDesc(Boutique boutique, Pageable pageable);

    Page<CommandeClient> findByIsAnnuleeAndBoutiqueOrderByIdDesc(boolean isAnnulee, Boutique boutique,Pageable pageable);

    Page<CommandeClient> findByBoutiqueAndIsAnnuleeOrderByIdDesc(Boutique boutique, boolean isAnnulee,Pageable pageable);
    Collection<CommandeClient> findByUser_Boutique(Boutique boutique);

    Iterable<CommandeClient> findAllByUserOrderByIdDesc(User longs);
    Page<CommandeClient> findAllByUserOrderByIdDesc(User longs,Pageable pageable);
}
