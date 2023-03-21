package org.akouma.stock.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;


@DynamicUpdate
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int quantite;
    private String  code;
    private float prixAchat;
    private float prixDeVente;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public float getPrixDeVente() {
        return prixDeVente;
    }

    public void setPrixDeVente(float prixDeVente) {
        this.prixDeVente = prixDeVente;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean Enabled) {
        isEnabled = Enabled;
    }

    private boolean isEnabled;

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    private boolean isActivated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return quantite == article.quantite && isActive == article.isActive && Objects.equals(id, article.id) && Objects.equals(name, article.name) && Objects.equals(code, article.code) && Objects.equals(cathegorieArticle, article.cathegorieArticle) && Objects.equals(boutique, article.boutique);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantite, code, isActive, cathegorieArticle, boutique);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isActive=true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cathegorie_article_id")
    private CathegorieArticle cathegorieArticle;

    @ManyToOne()
    @JoinColumn(name = "boutique_id")
    private Boutique boutique;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public CathegorieArticle getCathegorieArticle() {
        return cathegorieArticle;
    }

    public void setCathegorieArticle(CathegorieArticle cathegorieArticle) {
        this.cathegorieArticle = cathegorieArticle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
