package org.akouma.stock.RestController;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.ArticleCommandeClient;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.StockEntity;
import org.akouma.stock.service.ArticleCommandeClientService;
import org.akouma.stock.service.ArticleServices;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@RestController
public class HomeRestController {
    @Autowired
    private ArticleServices articleServices;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private StockService stockService;
    @Autowired
    private ArticleCommandeClientService articleCommandeClientService;

    @GetMapping("/get-form-new-commad-rest")
    public ResponseEntity<?> approuverTransactionLikeMacker() {

        HashMap<String, Object> response = new HashMap<>();
        String message = "Il n'existe pas de produits dans votre stock";

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Iterable<Article> articleIterable = articleServices.GetAllArticleBoutique(boutique);
        String result = "";
        String nouvelleArticleCommande = "  <table id=\"datatables-reponsive\" class=\"table table-striped\" style=\"width:100%\">\n<td><div class='col-md-5'>" +
                "  <label class='form-label'>Article</label>" +
                "     <select class='form-control form-select' riquired name='article[]'>" +
                "<option value=''></option>";
        for (Article art :
                articleIterable) {
            nouvelleArticleCommande +=
                    "    <option  value='" + art.getId() + "'>" + art.getName() + "</option>"
            ;
        }
        result = nouvelleArticleCommande + "  </select>" +
                "   </div></td><td><div class='col-md-3'>" +
                "     <label for='validationCustom05'  riquired class='form-label'>Quantite</label>" +
                "    <input type='number' name='quantite[]'  class='form-control' id='validationCustom05'" +
                "     required=''>" +
                "    <div class='invalid-feedback'>" +
                "     </div>" +
                "    </div></td><td><button class=\"delete btn-danger\"><i class=\"fas fa-times\"></i></button></td></table> ";

        response.put("produits", result);
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/confirmation-commande")
    public ResponseEntity<?> confirmationCommande(@RequestParam(value = "article[]") Article[] article,
                                                  @RequestParam(value = "quantite[]") Integer[] quantite) {
        HashMap<String, Object> response = new HashMap<>();
        int i = 0, j = 1;
        int PrixTotal = 0;
        String table = "";
        String entete = "<table id=\"datatables-reponsive\" class=\"table table-striped\" style=\"width:100%\">\n" +
                "    <thead>\n" +
                "    <tr>\n" +
                "        <th>#Num</th>\n" +
                "        <th>Article</th>\n" +
                "        <th>Quantite </th>\n" +
                "        <th>Prix Unitaire</th>\n" +
                "        <th>Sous total</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>";

        String corps = "";
        StockEntity stock = null;
        if (article != null) {
            for (Article art : article) {
                  System.out.println("********************1");
                if (art== null) {
                    response.put("prefacture", "404");
                    response.put("message", "Donnees erronees");
                    System.out.println("EEEEEEEEEEEEEEEE******************** 11");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                System.out.println("******************** 11");

                if ( quantite.length==0) {
                    response.put("prefacture", "404");
                    response.put("message", "Donnees erronees");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                System.out.println("********************212");

                if ( quantite[i] < 0) {
                    response.put("prefacture", "404");
                    response.put("message", "Donnees erronees");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                System.out.println("******************** 13");

                Float PrixTotalArticle = art.getPrixDeVente() * quantite[i];

                stock = stockService.getArticleStock(art);
                if (stock == null) {
                    response.put("prefacture", "400");
                    response.put("message", "Desole, mais cet article n'existe pas dans votre stock");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                if (stock.getQuantite() < quantite[i]) {
                    response.put("prefacture", "100");
                    response.put("message", "Desole mais la quantite de: <strong>" + art.getName() + "</strong> est superieur au stock restant");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                corps += " <tr>\n" +
                        "            <td >" + j + "</td>\n" +
                        "            <td >" + art.getName() + "</td>\n" +
                        "            <td >" + quantite[i] + "</td>\n" +
                        "            <td >" + art.getPrixDeVente() + "</td>\n" +
                        "            <td class='montant_format'>" + PrixTotalArticle + "</td>\n" +
                        "        </tr>";
                PrixTotal += PrixTotalArticle;
                j++;
                i++;
            }

        }
        String pieds = "  </tbody>\n" +
                "    <tr>\n" +
                "        <th>TOTAL </th>\n" +
                "        <th></th>\n" +
                "        <th> </th>\n" +
                "        <th></th>\n" +
                "        <th class='montant_format'> " + PrixTotal + "</th>\n" +
                "    </tr>\n" +
                "</table>";
        table += entete;
        table += corps;
        table += pieds;
        response.put("prefacture", table);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping({"/build-line-chart/", "/build-line-chart/{id}"})
    public ResponseEntity<?> lineChart(@RequestParam(required = false) String id) throws ParseException {
        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Article article = null;
        if (id != null) {
            article = articleServices.GetArticleById2(Long.valueOf(id));
            System.out.println("Article = " + id);
        }
        HashMap<String, Object> response = getChartData(boutique, article);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private HashMap<String, Object> getChartData(Boutique boutique, Article article) throws ParseException {
        HashMap<String, Object> data = new HashMap<>();
        // On recupere la date du jour
        int nbJours = 12;
        String[] labels = new String[nbJours];
        Long[] values = new Long[nbJours];

        for (int i = 0; i < nbJours; i++) {
            Calendar dateJour = Calendar.getInstance();
            dateJour.add(Calendar.DATE, -1 * (nbJours - 1 - i));
            String dateMaxStr = new SimpleDateFormat("yyyy-MM-dd").format(dateJour.getTime());
            Date dateJourMax = new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(dateMaxStr + " 23:59:59");
            Date dateJourMin = new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(dateMaxStr + " 00:00:00");

            Iterable<ArticleCommandeClient> transactions =
                    articleCommandeClientService.getarticleByVenteEntredeuxdates(boutique, article, dateJourMin, dateJourMax);
            String jour = new SimpleDateFormat("dd/MM/yyyy").format(dateJour.getTime());
            labels[i] = "JJ"+((i + 1)-12);
            values[i] = transactions.spliterator().estimateSize();
        }
        data.put("data", values);
        data.put("labels", labels);

        return data;
    }

}
