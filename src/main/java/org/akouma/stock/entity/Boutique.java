package org.akouma.stock.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@DynamicUpdate
@Entity
public class Boutique {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String telephone;
    private String avatar;
    private String email;

    public boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    private boolean isActivated;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean Enabled) {
        isEnabled = Enabled;
    }

    private boolean isEnabled;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> user = new ArrayList<>();

    @OneToMany(mappedBy = "boutique", orphanRemoval = true)
    private Collection<Fournisseur> fournisseurs = new ArrayList<>();

    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Article> articles = new ArrayList<>();

    @CreationTimestamp
    private Timestamp dateCreation;
    public Collection<Article> getArticles() {
        return articles;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
    }

    public Collection<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Collection<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
