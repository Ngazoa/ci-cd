package org.akouma.stock.controller;

import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeFournisseur;
import org.akouma.stock.entity.Fournisseur;
import org.akouma.stock.formulaire.FournisseurForm;
import org.akouma.stock.service.ArticleCommandeFournisseurService;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.FournisseurService;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.validator.FourArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FournisseurController {
    @Autowired
    private FournisseurService fournisseurService;
    @Autowired
    HttpSession session;
    @Autowired
    BoutiqueService boutiqueService;
    @Autowired
    private FourArticleValidator fourArticleValidator;
    @Autowired
    private ArticleCommandeFournisseurService commandeFournisseurService;
    private final int max = 10;
    private final int page = 1;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == FournisseurForm.class) {
            dataBinder.setValidator(fourArticleValidator);
        }
    }

    @GetMapping("/get-fournisseur-list")
    public String getListeDesFournisseure(HttpServletRequest request, Model model) {

        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) session.getAttribute("boutique");
        model.addAttribute("fournisseurs",
                fournisseurService.getListeFounisseurBOutique(boutique.getId()));
        model.addAttribute("titre", "Liste de tous les fournisseurs");

        return "fournisseur/fournisseur_list";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping({"/get-fournisseur-form", "/get-fournisseur-form/{id}"})
    public String getFounisseurForm(Model model, @PathVariable(value = "id", required = false) Fournisseur fournisseur) {

        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }
        FournisseurForm fournisseurForm = null;
        Fournisseur fournisseur1 = new Fournisseur();

        if (fournisseur != null) {
            fournisseurForm = new FournisseurForm(fournisseur);
            model.addAttribute("fournisseurUri", "/save-fournisseur-form/" + fournisseur.getId());
        } else {
            fournisseurForm = new FournisseurForm(fournisseur1);
            model.addAttribute("fournisseurUri", "/save-fournisseur-form");

        }

        model.addAttribute("fournisseurForm", fournisseurForm);
        return "fournisseur/ajouter_fournisseur";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @PostMapping({"/save-fournisseur-form", "/save-fournisseur-form/{id}"})
    public String SaveFournisseur(
            Model model, @ModelAttribute @Validated FournisseurForm fournisseurForm
            , BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @PathVariable(value = "id", required = false) Fournisseur fournisseur1) {
        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }
        Fournisseur fournisseur = new Fournisseur();

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Ajout du fournisseur Reuisssie avec succes");
            return "fournisseur/ajouter_fournisseur";
        }
        Boutique boutique = (Boutique) session.getAttribute("boutique");
        if (fournisseur1 != null) {
            fournisseur = fournisseur1;
        }


        fournisseur.setNomFournisseur(fournisseurForm.getNom());
        fournisseur.setEmail(fournisseurForm.getEmail());
        fournisseur.setTelephone(fournisseurForm.getTelephone());
        fournisseur.setEnabled(true);
        fournisseur.setBoutique(boutique);

        fournisseurService.AjouterFournissseurBoutique(fournisseur);

        redirectAttributes.addAttribute("flashMessage", "Ajout du fournisseur Reuisssie avec succes");
        return "redirect:/get-fournisseur-list";
    }

    @GetMapping({"/les-commandes-fournisseurs-{id}", "/les-commandes-fournisseurs-/{id}/page={page}"})
    public String getListe(Model model, @PathVariable(value = "id", required = false) Fournisseur fournisseur, @PathVariable(value = "page", required = false) Integer page) {

        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;

        // ON VERIFIE QUE LA BANQUE EST DANS LA SESSION AVANT DE CONTINUER
        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }
       double montantTotal=0;
        Page<ArticleCommandeFournissseur> cF=
                commandeFournisseurService.getCommmadesFouniiseurpar(fournisseur, max, page);
        // get motant total this fournisseur with their boutique
            if(cF!=null){
                for (ArticleCommandeFournissseur artCmmd:cF) {
                    montantTotal+= artCmmd.getQuantite()*artCmmd.getArticle().getPrixAchat();
                }
            }

        model.addAttribute("titre", "Liste commandes avec : " + fournisseur.getNomFournisseur());
        String uri = "/les-commandes-fournisseurs-/" + fournisseur.getId() + "/page={page}";
        model.addAttribute("uri", uri);
        model.addAttribute("Tmontant", "Montant total sorti :" +
                " <strong>"+montantTotal+"</strong>");
        pagination(cF, model);
        return "fournisseur/mes-commandes";
    }

    private void pagination(Page<ArticleCommandeFournissseur> artticle, Model model) {
        if (artticle != null) {
            int nbPages = artticle.getTotalPages();
            if (nbPages > 1) {
                int[] pages = new int[nbPages];
                for (int i = 0; i < nbPages; i++) {
                    pages[i] = i + 1;
                }
                model.addAttribute("pages", pages);
            }
            model.addAttribute("commandes", artticle.getContent());
            model.addAttribute("nbPages", artticle.getTotalPages());
            model.addAttribute("currentPage", artticle.getNumber() + 1);
            model.addAttribute("nbElements", artticle.getTotalElements());
            model.addAttribute("Tcommandes","Nombre total de commandes : <strong>"+artticle.getTotalElements()+"</strong>");

        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping("/founisseur-{id}/manage")
    public String manageArticle(@PathVariable("id") Fournisseur fournisseur) {
        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) session.getAttribute("boutique");

        if (boutique.getId() == fournisseur.getBoutique().getId()) {
            fournisseurService.deleteFounisseur(fournisseur);
            return "redirect:/get-fournisseur-list";
        }
        return "redirect:/";

    }
}
