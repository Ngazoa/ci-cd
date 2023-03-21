package org.akouma.stock.validator;

import org.akouma.stock.formulaire.CathegorieForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CategorieValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz== CathegorieForm.class;
    }
    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.empty");

    }
    }
