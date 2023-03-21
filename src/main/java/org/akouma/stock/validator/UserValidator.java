package org.akouma.stock.validator;

import org.akouma.stock.formulaire.UserForm;
import org.akouma.stock.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;

@Component
public class UserValidator implements Validator {


    private final EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        // Check the fields of AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.userForm.nom");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmpassword", "NotEmpty.userForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexe", "NotEmpty.userForm.gender");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "NotEmpty.userForm.telephone1");


        if (!httpSession.getAttribute("action").equals("modifier")) {

            boolean user = userService.CheckUserExisteByEmail(userForm.getEmail());
            if (!this.emailValidator.isValid(userForm.getEmail())) {
                errors.rejectValue("email", "Pattern.userForm.email");
            } else if (user) {
                errors.rejectValue("email", "Duplicate.userForm.email");
            }
        }
        if (!userForm.getConfirmpassword().equals(userForm.getPassword())) {
            errors.rejectValue("confirmpassword", "Match.userForm.confirmPassword");
        }

        if (userForm.getSexe().equals("Select sexe")) {
            errors.rejectValue("sexe", "Match.userForm.confirmPassword");

        }
    }

}
