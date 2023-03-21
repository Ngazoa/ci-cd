package org.akouma.stock.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Data
@DynamicUpdate
@Entity
public class CommandeFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String codeFournisseurCommande;
    private float prixTotal;
    private boolean isAnnulee;

    public boolean isAnnulee() {
        return isAnnulee;
    }

    public void setAnnulee(boolean annulee) {
        isAnnulee = annulee;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getCodeFournisseurCommande() {
        return codeFournisseurCommande;
    }

    public void setCodeFournisseurCommande(String codeFournisseurCommande) {
        this.codeFournisseurCommande = codeFournisseurCommande;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @CreationTimestamp
    private Timestamp dateCreation;

    @OneToMany(mappedBy = "commandeFournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ArticleCommandeFournissseur> articleCommandeFournissseurs = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id_user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<ArticleCommandeFournissseur> getArticleCommandeFournissseurs() {
        return articleCommandeFournissseurs;
    }

    public void setArticleCommandeFournissseurs(Collection<ArticleCommandeFournissseur> articleCommandeFournissseurs) {
        this.articleCommandeFournissseurs = articleCommandeFournissseurs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
