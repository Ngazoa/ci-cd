package org.akouma.stock.controller;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.Fournisseur;
import org.akouma.stock.formulaire.BoutiqueForm;
import org.akouma.stock.formulaire.UserForm;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.validator.BoutiqueValidator;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;

@Controller
@Validated
@ControllerAdvice
public class BoutiqueController {
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private BoutiqueValidator boutiqueValidator
;
    @Autowired
    private HttpSession httpSession;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == BoutiqueForm.class) {
            dataBinder.setValidator(boutiqueValidator);
        }
    }

    @Secured({ "ROLE_SUPERUSER"})
    @GetMapping("/add-new-boutique")
    public String getBoutiqueForm( Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique=new Boutique();
        BoutiqueForm boutiqueForm=new BoutiqueForm(boutique);
        model.addAttribute("titre", "Ajout d'une nouvelle boutique");
        model.addAttribute("boutiqueForm", boutiqueForm);
        return "boutique/boutiqueForm";
    }
    @Secured({ "ROLE_SUPERUSER"})
    @PostMapping("/save-boutique")
    public String registerBoutique(@ModelAttribute @Validated BoutiqueForm boutiqueForm,
                                   BindingResult result, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        model.addAttribute("boutiqueForm", boutiqueForm);
        model.addAttribute("titre", "Reponse de l'ajout");
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Erreur survenue");
            System.out.println("ERROR :" + result.toString());
            return "boutique/boutiqueForm";
        }
        boutiqueService.AjouterBoutique(boutiqueForm);
        model.addAttribute("flashMessage", "Enregistrement Reussie");
        redirectAttributes.addAttribute("flashMessage","Enregistrement Reussie");
        return "redirect:/list-boutique";
    }

    @Secured({ "ROLE_SUPERUSER"})
    @GetMapping("/get-form-boutique")
    public String GetBoutiqueForm(
                                  Model model, HttpServletRequest request) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique=new Boutique();
        BoutiqueForm boutiqueForm=new BoutiqueForm(boutique);
        model.addAttribute("boutiqueForm", boutiqueForm);

        model.addAttribute("titre", "Ajout d'une nouvelle boutique");

        return "boutique/boutiqueForm";
    }
    @Secured({ "ROLE_SUPERUSER"})
    @GetMapping("/list-boutique")
    public String getListBoutique(Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        model.addAttribute("titre", "Liste des boutiques du Systeme");
        model.addAttribute("boutiques", boutiqueService.GetAllBoutiqueSysteme());
        return "boutique/boutique_list";
    }

    @Secured({"ROLE_SUPERUSER"})
    @GetMapping("boutique-{id}/manage")
    public String ManageBoutique(@PathVariable("id")  Boutique id,RedirectAttributes redirectAttributes){
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        boutiqueService.DesactiverETActiverBoutique(id);
           redirectAttributes.addAttribute("flashMessage","Boutique Desactivee avec succesm ! !!");
        return "redirect:/list-boutique";
    }

}
