package org.akouma.stock.controller;

import org.akouma.stock.entity.*;
import org.akouma.stock.service.*;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CommandeClientController {
    @Autowired
    private ArticleServices articleServices;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private CommandeClientService commandeClientService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleCommandeClientService articleCommandeClientService;
    @Autowired
    private StockService stockService;

    private final int max = 5;
    private final int page = 1;

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping("/get-form-new-commad")
    public String getFormNewCommandeClent(Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Iterable<Article> articleIterable = articleServices.GetAllArticleBoutique(boutique);

        model.addAttribute("articles", articleIterable);

        return "client/new-commande";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @PostMapping("/save-confirmation-commande")
    public String confirmationCommande(@RequestParam(value = "article[]") Article[] article,
                                       @RequestParam(value = "quantite[]") Integer[] quantite,
                                       @RequestParam(value = "client",required = false) String nomClient) {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }


        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        User users = userService.getUserById((long) httpSession.getAttribute("user"));
        System.out.println("on est dedant");
        if (article != null) {
            int i = 0, j = 1;
            float PrixTotal = 0;
            Util util = new Util();
            CommandeClient commandeClient = new CommandeClient();
            commandeClient.setAnnulee(false);
            commandeClient.setCodeCommande(util.getReferenece());
            commandeClient.setUser(users);
            commandeClient.setBoutique(boutique);
            commandeClient.setClient(nomClient);
            commandeClientService.savecommande(commandeClient);

            for (Article art : article) {
                ArticleCommandeClient articleCommandeClient = new ArticleCommandeClient();
                float prixvente = art.getPrixDeVente() * quantite[i];
                PrixTotal += prixvente;
                articleCommandeClient.setArticle(art);
                articleCommandeClient.setCommandeClient(commandeClient);
                articleCommandeClient.setQuantite(quantite[i]);
                articleCommandeClient.setPrixunitairs(art.getPrixDeVente());
                articleCommandeClient.setPrixvente(prixvente);
                articleCommandeClientService.savearticlecommande(articleCommandeClient);
                stockService.DecrementerArticleDansleStock(art, quantite[i]);
                i++;
            }
            commandeClient.setPrixTotal(PrixTotal);
            commandeClientService.savecommande(commandeClient);

        }
        return "redirect:/list-commandes-client-user";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping({"/list-commandes-client-user", "/list-commandes-client-user-{page}",
            "list-commandes-client-user-{status}"})
    public String ListeCommade(Model model, @PathVariable(value = "page", required = false) Integer page,
                               @PathVariable(value = "statut", required = false) boolean statut) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }


        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");

        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;
        String uri = "/list-commandes-client-user-{page}";
        model.addAttribute("uri", uri);
        pagination(commandeClientService.getAllListCommandeClient(boutique, max, page), model);
        return "client/list-commandes";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping({"list-commandes-client-user-", "/list-commandes-client-user-{page}"})
    public String ListeCommadestatutannule(Model model, @PathVariable(value = "page", required = false)
            Integer page) {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");

        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;
        String uri = "/list-commandes-client-user-{page}";
        model.addAttribute("uri", uri);
        pagination(commandeClientService.getAllListCommandeClientstatutannule(boutique, max, page), model);
        return "client/list-commandes";
    }

    private void pagination(Page<CommandeClient> artticle, Model model) {
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

        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping("/get-all-article-command-client-{id}")
    public String getAllArticleCommande(@PathVariable("id") CommandeClient commandeClient, Model model) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }


        model.addAttribute("titre", "Details commande " + commandeClient.getCodeCommande());
        model.addAttribute("produits", articleCommandeClientService.getarticleCommande(commandeClient));
        return "client/list-article-commande";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_STOCK"})
    @GetMapping("/annuler-client-commade-{id}")
    public String anullercommande(@PathVariable("id") CommandeClient commandeClient) {
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Iterable<ArticleCommandeClient> art = articleCommandeClientService.getarticleCommande(commandeClient);
        User user = userService.getUserById((long) httpSession.getAttribute("user"));

        for (ArticleCommandeClient acc : art) {
            stockService.incrementerArticleDansleStock(acc.getArticle(), acc.getQuantite());
        }
        commandeClient.setNomAnulleur(user.getNameUser() + "  " + user.getEmail());
        commandeClient.setAnnuleur(user.getId());
        commandeClient.setAnnulee(true);
        commandeClientService.savecommande(commandeClient);
        return "redirect:/list-commandes-client-user";
    }
}
