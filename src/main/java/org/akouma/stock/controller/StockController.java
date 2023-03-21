package org.akouma.stock.controller;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.StockEntity;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.StockService;
import org.akouma.stock.util.LoadSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class StockController {
    @Autowired
    private StockService stockService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private BoutiqueService boutiqueService;


    private final int max = 10;
    private final int page = 1;


    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_STOCK"})
    @GetMapping({"/get-stok-boutique-{page}", "/get-stok-boutique-"})
    public String GetStockStatut(@PathVariable(value = "page", required = false) Integer page, Model model) {
        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Page<StockEntity> stockEntityPage = stockService.getStockBoutique(boutique, max, page);

        String uri = "/get-stok-boutique-{page}";
        pagination(stockEntityPage, model);
        model.addAttribute("uri", uri);
        model.addAttribute("titre", "liste des ajouts de stock");
        return "stock/list-stock";
    }

    private void pagination(Page<StockEntity> artticle, Model model) {
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
