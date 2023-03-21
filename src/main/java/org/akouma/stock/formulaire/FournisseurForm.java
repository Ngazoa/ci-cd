package org.akouma.stock.formulaire;

import lombok.Data;
import org.akouma.stock.entity.Fournisseur;

@Data
public class FournisseurForm {
    private String nom;
    private String telephone;
    private String email;
    private Fournisseur fournisseur;

    public FournisseurForm(Fournisseur fournisseur) {
        this.nom = fournisseur.getNomFournisseur();
        this.telephone = fournisseur.getTelephone();
        this.email= fournisseur.getEmail();
        this.fournisseur = fournisseur;
    }

    public FournisseurForm() {

    }
}
