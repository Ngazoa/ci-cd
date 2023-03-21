package org.akouma.stock.service;


import org.akouma.stock.dao.RoleRepository;
import org.akouma.stock.entity.Role;
import org.akouma.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public void deleteRole(User user){
        roleRepository.deleteByUsers(user);
    }
}
