package org.akouma.stock.service;

import org.akouma.stock.dao.Boutiquerepository;
import org.akouma.stock.dao.RoleRepository;
import org.akouma.stock.dao.Users;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.Role;
import org.akouma.stock.entity.User;
import org.akouma.stock.formulaire.BoutiqueForm;
import org.akouma.stock.util.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class BoutiqueService {
    @Autowired
    private Boutiquerepository boutiquerepository;
    @Autowired
    private Users usersrepository;
    @Autowired
    RoleRepository roleRepository;


    public boolean AjouterBoutique(BoutiqueForm boutiqueForm) throws IOException {
        Upload upload=new Upload();
       String fileName= upload.uploadFile(boutiqueForm.getAvatar());
        Boutique boutique =new Boutique();
        boutique.setName(boutiqueForm.getNom());
        boutique.setEmail(boutiqueForm.getEmail());
        boutique.setTelephone(boutiqueForm.getTelephone());
        boutique.setAvatar(fileName);
        boutique.setEnabled(true);
        boutique.setIsActivated(true);
       PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        User user =new User();
        ArrayList<Role> roleBoutique= (ArrayList<Role>) roleRepository.findAll();
        if(boutiquerepository.save(boutique)!=null){
            if(!boutiquerepository.existsByUser_Email(boutiqueForm.getEmail())){
                user.setEmail(boutiqueForm.getEmail());
                user.setBoutique(boutique);
                user.setEnabled(true);
                user.setAvatar(fileName);
                user.setPassword(passwordEncoder.encode("1234"));
                user.setActivated(true);
                user.setRoles(roleBoutique);
               // user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));

                usersrepository.save(user);

            }

            return true;
        }
        return false;
    }
    public Iterable<Boutique> GetAllBoutiqueSysteme(){
        return boutiquerepository.findAllByOrderByIdDesc();
    }
    public boolean DesactiverETActiverBoutique(Boutique fournisseur) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+fournisseur.getId());
       // Boutique fournisseur1 = boutiquerepository.findById(""+fournisseur.getId());
        if (fournisseur.isEnabled()) {
            fournisseur.setEnabled(false);
            boutiquerepository.save(fournisseur);
            return true;
        }
        fournisseur.setEnabled(true);
        boutiquerepository.save(fournisseur);
        return false;
    }
    public Boutique getBoutiqueByid(long id){
     return   boutiquerepository.findById(id);
    }
}
