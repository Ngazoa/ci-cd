package org.akouma.stock.service;

import org.akouma.stock.dao.Users;
import org.akouma.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private Users userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        System.out.println("User namrb "+username);

        if (user == null) {
            System.out.println("User not found");

            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

}
