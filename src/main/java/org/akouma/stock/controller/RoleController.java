package org.akouma.stock.controller;

import org.akouma.stock.entity.Role;
import org.akouma.stock.entity.User;
import org.akouma.stock.service.RoleService;
import org.akouma.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/get-user-role-{id}")
    public String getuserRoles(@PathVariable("id") User user) {
        return null;
    }

    @PostMapping("/role-{id}-user-add")
    private String saveUserRole(@PathVariable("id") User user,
                                @RequestParam("role[]") Role[] role, Model model, RedirectAttributes redirectAttributes) {
        // roleService.
System.out.println("RESULTAT ++++++++++++++++++++++++++++++");
        List<Role> roleList = new ArrayList<>();

        for (Role r : role) {
            if (r != null) {
                roleList.add(r);
            }
        }
        System.out.println("RESULTAT ++++++++++++++++++++++++++++++   "+roleList.toString());

        user.setRoles(roleList);
        userService.SaveUser(user);
        redirectAttributes.addAttribute("flashMessage","Role de l'administrateur "+user.getNameUser()+" sauvegarde avec succes");

        return "redirect:/user-list";
    }
}
