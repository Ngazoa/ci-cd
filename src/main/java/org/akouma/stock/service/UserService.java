package org.akouma.stock.service;

import org.akouma.stock.dao.CommandeClients;
import org.akouma.stock.dao.Users;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeClient;
import org.akouma.stock.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private Users userRespo;
    @Autowired
    private CommandeClients commandeClientsrepo;

    public void SaveUser(User user) {
        userRespo.save(user);
    }

    public User SearchUser(User user) {
        User userfound = userRespo.findByEmail(user.getEmail());
        if (userfound != null) {
            return userfound;
        }
        return null;
    }
    public User SearchUserByEmail(String mail) {
        User userfound = userRespo.findByEmail(mail);
        if (userfound != null) {
            return userfound;
        }
        return null;
    }
    public User getUserById(Long id){
        return userRespo.findById(id).get();
    }

    public Page<User> getAllUserByBoutique(Boutique boutique, int max, Integer page) {
        Page<User> ListeUsers = userRespo.findByBoutique(boutique, PageRequest.of(page, max));
        return ListeUsers;
    }
    public boolean UserExiste(User user){
        return userRespo.existsById(user.getId());
    }

    public boolean ActiverdesactiverUser(User user) {
        User userStatut = SearchUser(user);
        if (userStatut != null) {
            boolean etat = userStatut.isEnabled();
            if (etat) {
                user.setEnabled(false);
                userRespo.save(user);
                return true;
            }
            user.setEnabled(true);
            userRespo.save(user);
            return false;
        }
        return false;
    }
    public boolean CheckUserExisteByEmail(String email){
        return userRespo.existsByEmail(email);
    }

    public Page<CommandeClient> getAllcommandeUsersPerPage(User user,int max, Integer page){
       return commandeClientsrepo.findAllByUserOrderByIdDesc(user,PageRequest.of(page,max));
    }
}
