package org.akouma.stock.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@DynamicUpdate
@Entity
public class ArticleCommandeFournissseur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    private Timestamp dateCreation;
    private String codecommande;
    private int quantite;

    public boolean isAnnule() {
        return isAnnule;
    }

    public void setAnnule(boolean annule) {
        isAnnule = annule;
    }

    private boolean isAnnule=true;

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCodecommande() {
        return codecommande;
    }

    public void setCodecommande(String codecommande) {
        this.codecommande = codecommande;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commande_fournisseur_id")
    private CommandeFournisseur commandeFournisseur;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "boutique_id")
    private Boutique boutique;

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public CommandeFournisseur getCommandeFournisseur() {
        return commandeFournisseur;
    }

    public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseur = commandeFournisseur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
