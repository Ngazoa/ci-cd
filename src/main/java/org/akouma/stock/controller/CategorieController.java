package org.akouma.stock.controller;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CathegorieArticle;
import org.akouma.stock.formulaire.CathegorieForm;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.CatheorieServices;
import org.akouma.stock.util.LoadSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class CategorieController {
    @Autowired
    private CatheorieServices catheorieServices;
    @Autowired
    HttpSession httpSession;
    @Autowired
    private BoutiqueService boutiqueService;


    @GetMapping("/list-categorie")
    public String getCathList(Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Iterable<CathegorieArticle> cathegorieArticles = catheorieServices.getAllCategories(boutique);

        model.addAttribute("titre", "Liste de toutes les categories");
        model.addAttribute("categories", cathegorieArticles);
        return "categorie/categorie-list";

    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping("/get-categorie-form")
    public String getCathegorieForm(@ModelAttribute @Validated CathegorieForm cathegorieForm, Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        model.addAttribute("catheGorieUri", "/save-categorie-form");
        model.addAttribute("cathegorieForm", cathegorieForm);
        return "categorie/add-cathegorie";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @PostMapping({"/save-categorie-form", "/save-categorie-form-{id}"})
    public String saveCathegorieForm(@ModelAttribute @Validated CathegorieForm cathegorieForm
            , BindingResult bindingResult, Model model, @PathVariable(value = "id", required = false) CathegorieArticle idcath, RedirectAttributes redirectAttributes) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }


        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        if (bindingResult.hasErrors()) {
            return "categorie/add-cathegorie";
        }
        CathegorieArticle cathegorieArticle = new CathegorieArticle();

        if (idcath == null) {
            cathegorieArticle.setBoutique(boutique);
            cathegorieArticle.setNom(cathegorieForm.getNom());
            cathegorieArticle.setEnabled(true);
            catheorieServices.SaveCategorie(cathegorieArticle);
            redirectAttributes.addAttribute("flashMessage", "Categorie Cree avec succes");
            return "redirect:/list-categorie";
        }
        cathegorieArticle = catheorieServices.getArticleCategorie(idcath);
        cathegorieArticle.setNom(cathegorieForm.getNom());
        catheorieServices.SaveCategorie(cathegorieArticle);
        redirectAttributes.addAttribute("flashMessage", "Categorie Modifiee avec succes");
        return "redirect:/list-categorie";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping("/modifier-{id}/manage")
    public String ModifierCathegorieForm(@PathVariable("id") CathegorieArticle cathegorieArticle, Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        CathegorieForm cathegorieForm = new CathegorieForm(cathegorieArticle);
        model.addAttribute("titre", "Modifier categorie");

        model.addAttribute("cathegorieForm", cathegorieForm);
        model.addAttribute("catheGorieUri", "/save-categorie-form-" + cathegorieArticle.getId());
        return "categorie/add-cathegorie";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping("/categorie-{id}/manage")
    public String ActiverCategorie(@PathVariable("id") CathegorieArticle cathegorieArticle, Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }
        catheorieServices.SuppreimerCathegorie(cathegorieArticle);
        return "redirect:/list-categorie";
    }

}
