package org.akouma.stock.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.akouma.stock.entity.Role;
import org.akouma.stock.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomUserDetails implements UserDetails {

    private User user;
    private Collection<Role> roles;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        roles = user.getRoles();
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                role.getPrivileges().stream()
                        .map(p -> new SimpleGrantedAuthority(p.getName()))
                        .forEach(authorities::add);
            }
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return user.getNameUser() + " " + user.getSurnameUser();
    }

}