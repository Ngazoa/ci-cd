package org.akouma.stock.formulaire;

import org.akouma.stock.entity.Role;

public class roleForm {

    private String name;
    private String description;
    private Role role;

    public roleForm(Role role) {
        this.name = role.getName();
        this.description = role.getDescription();
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
