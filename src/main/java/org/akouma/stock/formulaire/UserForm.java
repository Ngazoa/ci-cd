package org.akouma.stock.formulaire;

import lombok.Data;
import org.akouma.stock.entity.User;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserForm {
    private String nom;
    private String prenom;
    private String avatar;
    private String email;
    private String cni;
    private String sexe;
    private MultipartFile image;

    public UserForm(String nom, String prenom, String avatar, String email,
                    String cni, String sexe, String password,
                    String confirmpassword, User user) {
        this.nom = nom;
        this.prenom = prenom;
        this.avatar = avatar;
        this.email = email;
        this.cni = cni;
        this.sexe = sexe;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.user = user;
    }

    private String password;
    private String confirmpassword;
    private User user;

    public UserForm(User user) {
        this.user = user;
        this.nom = user.getNameUser();
        this.prenom = user.getSurnameUser();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.avatar = user.getAvatar();
        this.confirmpassword = null;
        this.sexe = user.getSexe();
        this.cni = user.getNumeroCni();

    }
    public UserForm() {


    }


}
