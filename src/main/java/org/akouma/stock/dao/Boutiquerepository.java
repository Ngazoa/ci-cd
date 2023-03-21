package org.akouma.stock.dao;

import org.springframework.data.repository.CrudRepository;
import org.akouma.stock.entity.Boutique;

import java.util.Optional;

public interface Boutiquerepository extends CrudRepository<Boutique, Long> {
    Iterable<Boutique> findAllByOrderByIdDesc();

    Boutique findById(long aLong);

    Optional<Boutique> findByUser_Email(String email);

    boolean existsByUser_Email(String email);

}
