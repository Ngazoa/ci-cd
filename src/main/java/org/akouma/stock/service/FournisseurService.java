package org.akouma.stock.service;

import org.akouma.stock.dao.Fournisseurs;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.Fournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Service
public class FournisseurService {
    @Autowired
    private Fournisseurs fournisseursResp;


    public Iterable<Fournisseur> getListeFounisseurBOutique(long boutique) {
        Iterable<Fournisseur> fournisseurs=fournisseursResp.findByBoutique_IdOrderByIdDesc(boutique);
        System.out.println("***************   "+fournisseurs);
        return fournisseurs;
    }

    public boolean DesactiverETActiverFournisseur(Fournisseur fournisseur) {
        if (fournisseur.isEnabled()) {
            fournisseur.setEnabled(false);
            fournisseursResp.save(fournisseur);
            return true;
        }
        fournisseur.setEnabled(true);
        fournisseursResp.save(fournisseur);
        return false;
    }

    public void AjouterFournissseurBoutique(Fournisseur fournisseur) {
        fournisseursResp.save(fournisseur);
    }

   public Fournisseur getfouisseur(long id){
    return  fournisseursResp.findById(id)    ;
   }

   public boolean deleteFounisseur(Fournisseur fournisseur){
        if(fournisseur.isEnabled()){
            fournisseur.setEnabled(false);
            fournisseursResp.save(fournisseur);
            return true;
        }
       fournisseur.setEnabled(true);
       fournisseursResp.save(fournisseur);
       return false;
   }
   public long getNbreFournisseur(Boutique boutique){
        return fournisseursResp.countByBoutiqueAndIsEnabled(boutique,true);
   }

}
