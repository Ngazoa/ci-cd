package org.akouma.stock.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@DynamicUpdate
@Entity
public class CathegorieArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    private boolean isActivated;

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean Enabled) {
        isEnabled = Enabled;
    }

    private boolean isEnabled;

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @OneToMany(mappedBy = "cathegorieArticle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    public List<Article> getArticles() {
        return articles;
    }
    @CreationTimestamp
    private Timestamp dateCreation;

    @OneToOne( orphanRemoval = true)
    @JoinColumn(name = "boutique_id")
    private Boutique boutique;

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
