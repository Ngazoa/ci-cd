package org.akouma.stock.formulaire;

import org.akouma.stock.entity.CathegorieArticle;

public class CathegorieForm {
    private String nom;
    private boolean isEnabled;
    private CathegorieArticle cathegorieArticle;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean Enabled) {
        isEnabled = Enabled;
    }

    public CathegorieForm() {
        this.nom=null;
    }

    public CathegorieArticle getCathegorieArticle() {
        return cathegorieArticle;
    }

    public void setCathegorieArticle(CathegorieArticle cathegorieArticle) {
        this.cathegorieArticle = cathegorieArticle;
    }

    public CathegorieForm(CathegorieArticle cathegorieArticle) {
        this.cathegorieArticle=cathegorieArticle;
        this.nom=cathegorieArticle.getNom();
    }
}
