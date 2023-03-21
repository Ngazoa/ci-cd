package org.akouma.stock.controller;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.formulaire.ArticleForm;
import org.akouma.stock.service.ArticleServices;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.CatheorieServices;
import org.akouma.stock.service.FournisseurService;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.util.Upload;
import org.akouma.stock.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ArticleController {
    @Autowired
    private ArticleServices articleServices;
    @Autowired
    HttpSession session;
    @Autowired
    BoutiqueService boutiqueService;
    @Autowired
    private FournisseurService fournisseurService;
    @Autowired
    private CatheorieServices catheorieServices;
    @Autowired
    private ArticleValidator articleValidator;
    @Autowired
    private HttpSession httpSession;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ArticleForm.class) {
            dataBinder.setValidator(articleValidator);
        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER","ROLE_STOCK"})
    @GetMapping({"/get-article-form", "/get-articles-form-{id}"})
    public String getAricleForm(
            Model model, @PathVariable(value = "id", required = false) Article article) {
        ArticleForm articleForm = null;
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        if (article != null) {
            articleForm = new ArticleForm(article);
            model.addAttribute("articleuri", "/save-article-" + article.getId());

        } else {
            Article article1 = new Article();
            articleForm = new ArticleForm(article1);
            model.addAttribute("articleuri", "/save-article");

        }
        model.addAttribute("articleForm", articleForm);
        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        model.addAttribute("categories", catheorieServices.getAllCategories(boutique));
        return "article/add-article";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER","ROLE_STOCK"})
    @PostMapping({"/save-article", "save-article-{id}"})
    public String SaveNewArticle(RedirectAttributes redirectAttributes, Model model,
                                 @ModelAttribute @Validated ArticleForm articleForm,
                                 BindingResult result, @PathVariable(value = "id", required = false) Article articlem) throws IOException {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }
        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");

        if (result.hasErrors()) {
            System.out.println("eERRRRRRRRRRRRRRRRRRRRoooooooooooooo " + result.toString());
            model.addAttribute("categories", catheorieServices.getAllCategories(boutique));
            model.addAttribute("errorMessage", "Une erreur de saisie des donnees est survenue");
            return "article/add-article";
        }
        Upload upload = new Upload();

        Article article = new Article();

        if (articlem != null) {
            article = articleServices.GetArticleById(articlem);
        }
        //article.setQuantite(articleForm.getQuantite());
        article.setEnabled(true);
        article.setBoutique(boutique);
        article.setName(articleForm.getName());
        article.setCode(articleForm.getCode());
        article.setCathegorieArticle(articleForm.getCathegorie());
        article.setPrixAchat(articleForm.getPrixAchat());
        article.setPrixDeVente(articleForm.getPrixVente());
        String avatar = upload.uploadFile(articleForm.getAvatar());
        article.setAvatar(avatar);
        articleServices.AjouterArticleDansBoutique(article);

        return "redirect:/article-list";

    }

    @GetMapping("/article-list")
    public String ListArticle(Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Iterable<Article> article = articleServices.GetAllArticleBoutique(boutique);
        // System.out.println("    ARRRRTIIIICCCCLEE "+article.g);
        model.addAttribute("titre", "Liste des articles " + boutique.getName());
        model.addAttribute("articles", article);
        return "article/article-list";
    }
    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER","ROLE_STOCK"})
    @GetMapping("/articles-{id}/manage")
    public String manageArticle(@PathVariable("id") Article article) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        System.out.println("--- " + boutique.getId() + " **** " + article.getBoutique().toString());
        if (boutique.getId() == article.getBoutique().getId()) {
            articleServices.deleteArticle(article);
            return "redirect:/article-list";
        }
        return "redirect:/";

    }
}
