package org.akouma.stock.validator;

import org.akouma.stock.formulaire.FournisseurForm;
import org.akouma.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FourArticleValidator implements Validator {


    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == FournisseurForm.class;
    }
    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.empty");



    }

}
