package org.akouma.stock.validator;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.formulaire.ArticleForm;
import org.akouma.stock.service.ArticleServices;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleValidator implements Validator {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private ArticleServices articleServices;
    @Autowired
    private BoutiqueService boutiqueService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz==ArticleForm.class;
    }
    @Override
    public void validate(Object target, Errors errors) {
        ArticleForm articleForm = (ArticleForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantite", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prixAchat", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prixVente", "NotEmpty.empty");

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        boolean artticle=articleServices.checckArticleByBoutique(boutique,articleForm.getName());

        System.out.println("-------------- "+artticle);
        if (artticle) {
            errors.rejectValue("name", "Pattern.Article.name");
        }

        if(articleForm.getPrixAchat()>articleForm.getPrixVente()){
            errors.rejectValue("prixVente", "Pattern.Article.prix");
        }


        if(articleForm.getCathegorie()==null){
            errors.rejectValue("cathegorie", "Pattern.Article.quantite");

        }

    }
}
