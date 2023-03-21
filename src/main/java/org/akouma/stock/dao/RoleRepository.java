package org.akouma.stock.dao;

import org.akouma.stock.entity.Role;
import org.akouma.stock.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    long deleteByUsers(User users);
    Optional<Role> findByName(String name);

}
