package org.akouma.stock.validator;


import org.akouma.stock.formulaire.EntreeDeStockForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StockValidator implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == EntreeDeStockForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EntreeDeStockForm entreeDeStockForm = (EntreeDeStockForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantite", "NotEmpty.empty");


        if (entreeDeStockForm.getArticle()==null) {
            errors.rejectValue("article", "Match.userForm.confirmPassword");
        }

        if (entreeDeStockForm.getQuantite()<1) {
            errors.rejectValue("quantite", "Match.userForm.confirmPassword");

        }
        if (entreeDeStockForm.getFournisseur()==null) {
            errors.rejectValue("fournisseur", "Match.userForm.confirmPassword");

        }
    }

}

