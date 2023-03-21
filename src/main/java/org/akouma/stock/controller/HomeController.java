package org.akouma.stock.controller;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Boutique;
import org.akouma.stock.entity.User;
import org.akouma.stock.service.*;
import org.akouma.stock.util.LoadSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private HttpSession session;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private FournisseurService fournisseurService;
    @Autowired
    private ArticleCommandeFournisseurService articleCommandeFournisseurService;
    @Autowired
    private ArticleCommandeClientService articleCommandeClientService;
    @Autowired
    private CommandeClientService commandeClientService;
    @Autowired
    private CommandeFournisseurService commandeFournisseurService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleServices articleServices;


    @GetMapping("/load-dashboard")
    public String loadDashboard(Model model, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("+++++++++++++++++++++++ 3 " + authentication.getAuthorities());


        User userloggeed = userService.SearchUserByEmail(principal.getName());
        session.setAttribute("boutique", userloggeed.getBoutique());
        session.setAttribute("user", userloggeed.getId());
        session.setAttribute("user2", userloggeed);

        if (!LoadSession.sessionExiste(httpSession)) {
            return "redirect:/";
        }

        Boutique boutique = userloggeed.getBoutique();
        User user = userloggeed;

        Long nbreCmdClt = commandeClientService.getNbretransactionscliens(boutique);
        Long nbreCmdByUserservi = commandeClientService.getNbretransactionscliensByUer(user);
        Float montantTotalenregistre = commandeClientService.getSommeEntreeClient(boutique);
        Long nbreFournisseur = fournisseurService.getNbreFournisseur(boutique);
        Iterable<Article> article = articleServices.GetAllArticleBoutique(boutique);

        Page<ArticleCommandeFournissseur> commandeFornisseur = articleCommandeFournisseurService
                .getAllCommandeFournisseurBoutique(boutique, 8, 0);

        model.addAttribute("nbreTotalCmnd", nbreCmdClt);
        model.addAttribute("nbreTotalCmndUer", nbreCmdByUserservi);
        model.addAttribute("montantTotalCmnd", montantTotalenregistre);
        model.addAttribute("nbreFournisseur", nbreFournisseur);
        model.addAttribute("nbrecomandeFournisseur", commandeFornisseur.getTotalElements());
        model.addAttribute("produits", commandeFornisseur.getContent());
        model.addAttribute("article", article);
        model.addAttribute("lineChartUri", "/build-line-chart/");

        return "dashboard";
    }

    @GetMapping("/po")
    public String logghome(Model model) {
        long id = 26;
        long iduser = 2;

        Boutique boutique = boutiqueService.getBoutiqueByid(id);
        session.setAttribute("boutique", boutique);
        session.setAttribute("user", iduser);
        model.addAttribute("pagehome", "home");
        model.addAttribute("title", "Dashboard");
        return "layout/layout";
    }

    @PostMapping("/login-save")
    private String checkLogin(@RequestParam("email") String login, @RequestParam("password") String Pasword) {
        User user = userService.SearchUserByEmail(login);
        System.out.println("LOGIN " + login + "  *********" + Pasword);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encrytedPassword = passwordEncoder.encode(Pasword);

//        if (securityService.isAuthenticated()) {
//            return "redirect:/";
//        }

        if (user != null) {
            boolean isSamepassword = passwordEncoder.matches(Pasword, user.getPassword());

            if (isSamepassword) {
                session.setAttribute("boutique", user.getBoutique());
                session.setAttribute("user", user.getId());
                return "true";
            }
        }
        return "false";
    }

    @GetMapping({"/", "/login"})
    public String loginform() {

        return "login";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();

        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //  Ssi nous retrouvons le cookies enregidtree dans le systeme
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "redirect:/";
    }

    @PostMapping("/edit-password")
    public String editPasssword(Model model, @RequestParam("password") String Pasword
            , @RequestParam("password2") String Pasword2, @RequestParam("password3") String Pasword3) {
        if (!Pasword2.equals(Pasword3)) {
            model.addAttribute("errorMessage", "Attention, le nouveau mot de passe n'est pas identique");
            return "editer-password";
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = (User) session.getAttribute("user2");
        if (passwordEncoder.matches(Pasword, user.getPassword())) {
            model.addAttribute("flashMessage", "Mot de passe modifie avec succes");
            user.setPassword(passwordEncoder.encode(Pasword2));
            userService.SaveUser(user);
            return "editer-password";
        }
        model.addAttribute("errorMessage", "Mot de passe actuel errone");
        return "editer-password";
    }

    @GetMapping("/edit-password-form")
    public String getEditpasswordForm() {
        return "editer-password";
    }

    @RequestMapping(value = "image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

        File serverFile = new File("upload/" + imageName);

        return Files.readAllBytes(serverFile.toPath());
    }
}
