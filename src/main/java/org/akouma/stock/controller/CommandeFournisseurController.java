package org.akouma.stock.controller;

import org.akouma.stock.entity.*;
import org.akouma.stock.formulaire.EntreeDeStockForm;
import org.akouma.stock.service.*;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.util.Util;
import org.akouma.stock.validator.StockValidator;
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

import javax.servlet.http.HttpSession;

@Controller
public class CommandeFournisseurController {
    @Autowired
    HttpSession session;
    @Autowired
    ArticleCommandeFournisseurService articleCommandeFournisseurService;
    @Autowired
    private CommandeFournisseurService commandeFournisseurService;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private UserService userService;
    @Autowired
    private FournisseurService fournisseurService;
    @Autowired
    private ArticleServices articleServices;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockValidator stockValidator;

    private final int max = 10;
    private final int page = 1;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == EntreeDeStockForm.class) {
            dataBinder.setValidator(stockValidator);
        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping({"/add-article-stock", "/Edit-article-stock-form-{id}"})
    public String getAjoutStock(EntreeDeStockForm entreeDeStockForm,
                                @PathVariable(value = "id", required = false) ArticleCommandeFournissseur
                                        articleCommandeFournissseur, Model model) {
        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) session.getAttribute("boutique");
        Iterable<Fournisseur> fournisseur = fournisseurService.getListeFounisseurBOutique(boutique.getId());
        Iterable<Article> articles = articleServices.GetAllArticleBoutique(boutique);
        if (articleCommandeFournissseur != null) {
            entreeDeStockForm = new EntreeDeStockForm(articleCommandeFournissseur);
        }
        model.addAttribute("stockForm", entreeDeStockForm);
        model.addAttribute("fournisseurs", fournisseur);
        model.addAttribute("articles", articles);
        model.addAttribute("title", "Ajout de produits dans le stock");

        return "stock/entree-article-stock";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_STOCK"})
    @PostMapping({"/save-article-stock", "/save-Edit-article-stock-form-{id}"})
    public String savegetAjoutStock(Model modele, @ModelAttribute @Validated EntreeDeStockForm entreeDeStockForm,
                                    BindingResult result, RedirectAttributes model,
                                    @PathVariable(value = "id", required = false) ArticleCommandeFournissseur
                                            articleCommandeFournissseur) {

        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }

        ArticleCommandeFournissseur newArticlestock = new ArticleCommandeFournissseur();
        long id = (long) session.getAttribute("user");

        Boutique boutique = (Boutique) session.getAttribute("boutique");

        Util util = new Util();
        User userRegister = userService.getUserById(id);
        if (result.hasErrors()) {
            System.out.println("****************+++++++++++ " + result);
            modele.addAttribute("errorMessage", "Donnes entrees sont invalides ");

            Iterable<Fournisseur> fournisseur = fournisseurService.getListeFounisseurBOutique(boutique.getId());
            Iterable<Article> articles = articleServices.GetAllArticleBoutique(boutique);


            modele.addAttribute("stockForm", entreeDeStockForm);
            modele.addAttribute("fournisseurs", fournisseur);
            modele.addAttribute("articles", articles);
            return "stock/entree-article-stock";
        }
        StockEntity stockEntity = new StockEntity();
        if (articleCommandeFournissseur != null) {
            newArticlestock = articleCommandeFournissseur;
        } else {
            newArticlestock.setCodecommande(util.getReferenece());
        }
        boolean isPremierFois=false;
        boolean stock = stockService.getArticleStoc(entreeDeStockForm.getArticle());
        if (!stock) {
            stockEntity.setArticle(entreeDeStockForm.getArticle());
            stockEntity.setBoutique(boutique);
            stockEntity.setQuantite(entreeDeStockForm.getQuantite());
            stockService.SaveStock(stockEntity);
            isPremierFois=true;
        }

        newArticlestock.setBoutique(boutique);
        newArticlestock.setAnnule(false);
        newArticlestock.setUser(userRegister);
        newArticlestock.setArticle(entreeDeStockForm.getArticle());
        newArticlestock.setFournisseur(entreeDeStockForm.getFournisseur());
        newArticlestock.setQuantite(entreeDeStockForm.getQuantite());
        model.addFlashAttribute("flashMessage", "Opereation reussie avec succes");
        articleCommandeFournisseurService.saveArticle(newArticlestock,isPremierFois);

        return "redirect:/get-stok-boutique-";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_STOCK"})
    @GetMapping({"/get-list-commande-fournisseur-{page}", "/get-list-commande-fournisseur"})
    public String getlistCommande(Model model, @PathVariable(value = "page", required = false) Integer page) {

        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }
        Boutique boutique = (Boutique) session.getAttribute("boutique");

        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;

        Page<ArticleCommandeFournissseur> srf =
                articleCommandeFournisseurService.getAllCommandeFournisseurBoutique(boutique, max, page);

        System.out.println(" RESULT " + srf.spliterator().estimateSize());
        pagination(srf, model);
        model.addAttribute("titre", "Liste des commande avec les fournisseurs");

        String uri = "/get-list-commande-fournisseur-{page}";
        model.addAttribute("uri", uri);
        model.addAttribute("title", "liste des ajouts");
        return "fournisseur/list-commande-fournisseur";
    }


    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_STOCK"})
    @GetMapping("/annuler-commande-{id}-fournisseur")
    public String annulerCommade(@PathVariable(value = "id", required = false) ArticleCommandeFournissseur
                                         articleCommandeFournissseur, RedirectAttributes model) {
        if (!LoadSession.sessionExiste(session)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) session.getAttribute("boutique");

        if (articleCommandeFournissseur.getBoutique() != boutique) {
            return "redirect:/login";
        }
        if (articleCommandeFournissseur.isAnnule()) {
            model.addFlashAttribute("errorMessage", "Desole mais il pourrait que cet action" +
                    "soit deja executee");
            return "redirect:/get-list-commande-fournisseur";
        }
        model.addFlashAttribute("flashMessage", "Stock retourne avec succes ");
        articleCommandeFournisseurService.annulerCommandeFournisseur(articleCommandeFournissseur);

        return "redirect:/get-list-commande-fournisseur";
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
            model.addAttribute("produits", artticle.getContent());
            model.addAttribute("nbPages", artticle.getTotalPages());
            model.addAttribute("currentPage", artticle.getNumber() + 1);
            model.addAttribute("nbElements", artticle.getTotalElements());

        }
    }
}
