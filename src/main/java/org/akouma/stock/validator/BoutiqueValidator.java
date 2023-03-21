package org.akouma.stock.validator;

import org.akouma.stock.formulaire.BoutiqueForm;
import org.akouma.stock.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class BoutiqueValidator implements Validator {
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserService userService;

    public boolean supports(Class<?> clazz) {
        return clazz == BoutiqueForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoutiqueForm BoutiqueForm = (BoutiqueForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "NotEmpty.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.empty");

        boolean user = userService.CheckUserExisteByEmail(BoutiqueForm.getEmail());

        if (!this.emailValidator.isValid(BoutiqueForm.getEmail())) {

            errors.rejectValue("email", "Pattern.userForm.email");
        } else if (user) {
            errors.rejectValue("email", "Duplicate.userForm.email");

        }

    }
}
