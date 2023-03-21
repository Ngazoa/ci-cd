package org.akouma.stock.formulaire;

import lombok.Data;
import org.akouma.stock.entity.Boutique;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoutiqueForm {
    private String nom;
    private String telephone;
    private MultipartFile avatar;
    private Boutique boutique;
    private String email;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BoutiqueForm(Boutique boutique) {
        this.boutique = boutique;
        this.telephone = boutique.getTelephone();
        this.nom = boutique.getName();
        this.email= boutique.getEmail();
    }
    public BoutiqueForm() {

    }
}
