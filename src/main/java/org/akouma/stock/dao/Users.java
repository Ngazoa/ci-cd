package org.akouma.stock.dao;

import org.akouma.stock.entity.Boutique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.akouma.stock.entity.User;

import java.util.Optional;

public interface Users extends CrudRepository<User, Long> {
    @Override
    Iterable<User> findAllById(Iterable<Long> longs);

    Optional<User> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    Iterable<User> findAll();



    User findByEmail(String aLong);

    boolean existsByEmail(String email);

    Page<User> findByBoutique(Boutique boutique, Pageable pageable);



}
