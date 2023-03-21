package org.akouma.stock.controller;

import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.CommandeClient;
import org.akouma.stock.entity.User;
import org.akouma.stock.formulaire.UserForm;
import org.akouma.stock.service.BoutiqueService;
import org.akouma.stock.service.CommandeClientService;
import org.akouma.stock.service.RoleService;
import org.akouma.stock.service.UserService;
import org.akouma.stock.util.LoadSession;
import org.akouma.stock.util.Upload;
import org.akouma.stock.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private CommandeClientService commandeClientService;

    private final int max = 10;
    private final int page = 1;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(userValidator);
        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping({"/user-get-save-form", "/get-user-modif-{id}"})
    public String Userformsaveuser(
            Model model, @PathVariable(value = "id",
            required = false) User usermodif) {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }


        UserForm userForm = null;

        if (usermodif != null) {
            httpSession.setAttribute("action", "modifier");
            model.addAttribute("readOnly", "readonly");
            userForm = new UserForm(usermodif);
            model.addAttribute("userForm", userForm);
            model.addAttribute("url", "/user-save-form-" + usermodif.getId());
            return "user/user-form";
        }
        httpSession.setAttribute("action", "ajouter");
        User user = new User();
        userForm = new UserForm(user);

        model.addAttribute("url", "/user-save-form");
        model.addAttribute("userForm", userForm);
        return "user/user-form";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @PostMapping({"/user-save-form", "/user-save-form-{id}"})
    public String Usersaveuser(Model model, @ModelAttribute @Validated UserForm userForm, BindingResult result,
                               @PathVariable(value = "id", required = false) User user1) throws IOException {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");
        Upload upload = new Upload();

        if (result.hasErrors()) {

            System.out.println("error -> " + result);
            model.addAttribute("erroMessage", "Oops donnees entrees incorrectes");
            return "user/user-form";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();

        if (user1 != null) {
            user = user1;
        }
        user.setNameUser(userForm.getNom());
        user.setAvatar(upload.uploadFile(userForm.getImage()));
        user.setPassword(encoder.encode(userForm.getPassword()));
        user.setEnabled(true);
        user.setEmail(userForm.getEmail());
        user.setBoutique(boutique);
        user.setSexe(userForm.getSexe());
        user.setNumeroCni(userForm.getCni());
        user.setSurnameUser(userForm.getPrenom());

        userService.SaveUser(user);


        return "redirect:/user-list";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping({"/user-list", "/user-list-{page}"})
    public String UseerLis(Model model, @PathVariable(value = "page", required = false) Integer page) {
        //User id = userService.getUserById((long) httpSession.getAttribute("user"));
        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        if (page == null || page <= 0) {
            page = this.page;
        }
        page--;
        Boutique boutique = (Boutique) httpSession.getAttribute("boutique");

        String uri = "/user-list-{page}";
        model.addAttribute("uri", uri);
        model.addAttribute("titre", "Liste des administrateur de " + boutique.getName());
        System.out.println("     RRRRRRRRRROOOOOOOOOLLLLLLLLLLEEEEEE " + roleService.getAllRole());
        model.addAttribute("uroles", roleService.getAllRole());


        Page<User> listuser = userService.getAllUserByBoutique(boutique, max, page);
        pagination(listuser, model);

        return "user/user-list";
    }

    private void pagination(Page<User> artticle, Model model) {
        if (artticle != null) {
            int nbPages = artticle.getTotalPages();
            if (nbPages > 1) {
                int[] pages = new int[nbPages];
                for (int i = 0; i < nbPages; i++) {
                    pages[i] = i + 1;
                }
                model.addAttribute("pages", pages);
            }
            model.addAttribute("users", artticle.getContent());
            model.addAttribute("nbPages", artticle.getTotalPages());
            model.addAttribute("currentPage", artticle.getNumber() + 1);
            model.addAttribute("nbElements", artticle.getTotalElements());

        }
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER"})
    @GetMapping("activer-{id}-user")
    public String activerUser(@PathVariable("id") User user) {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        if (user.isEnabled()) {
            user.setEnabled(false);
            userService.SaveUser(user);
            return "redirect:/user-list";
        }
        user.setEnabled(true);
        userService.SaveUser(user);
        return "redirect:/user-list";
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_SUPERUSER", "ROLE_ADMIN"})
    @GetMapping("get-user-historique-/{id}")
    public String getUserCommandePerPage(@PathVariable("id") User user, Integer page, Model model) {

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }
        if (page == null || page <= 0) {
            page = this.page;
        }
        Float commandecount=commandeClientService.getSommeCommandeByUser(user);


        page--;
        Page<CommandeClient> listcommandeuser = userService.getAllcommandeUsersPerPage(user, max, page);
        pagination2(listcommandeuser, model);
        model.addAttribute("titre", "Historique de l'administrateur - " + user.getNameUser());
        model.addAttribute("montantCommandes", "Montant Tt de commandes: "+commandecount);
        return "user/commamde-user";
    }

    private void pagination2(Page<CommandeClient> artticle, Model model) {
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
            model.addAttribute("NbreCommandes", "Nbre total de commandes : "+artticle.getTotalElements());

        }
    }
}
