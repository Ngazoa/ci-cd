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
public class CommandeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getAnnuleur() {
        return annuleur;
    }

    public void setAnnuleur(Long annuleur) {
        this.annuleur = annuleur;
    }

    private Long annuleur;

    private String codeCommande;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    private String client;

    public String getNomAnulleur() {
        return nomAnulleur;
    }

    public void setNomAnulleur(String nomAnulleur) {
        this.nomAnulleur = nomAnulleur;
    }

    private String nomAnulleur;
    @CreationTimestamp
    private Timestamp dateCreation;
    private float prixTotal;

    @Column(nullable = false)
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

    public String getCodeCommande() {
        return codeCommande;
    }

    public void setCodeCommande(String codeCommande) {
        this.codeCommande = codeCommande;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id_user")
    private User user;

    @OneToMany(mappedBy = "commandeClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ArticleCommandeClient> articleCommandeClients = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "boutique_id")
    private Boutique boutique;

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Collection<ArticleCommandeClient> getArticleCommandeClients() {
        return articleCommandeClients;
    }

    public void setArticleCommandeClients(Collection<ArticleCommandeClient> articleCommandeClients) {
        this.articleCommandeClients = articleCommandeClients;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
