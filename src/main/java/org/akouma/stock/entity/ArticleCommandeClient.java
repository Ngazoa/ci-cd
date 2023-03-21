package org.akouma.stock.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@DynamicUpdate
@Entity
public class ArticleCommandeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    private Timestamp dateCreation;
    private String code;
    private int quantite;
    private float prixunitairs;

    private float prixvente;

    public float getPrixvente() {
        return prixvente;
    }

    public void setPrixvente(float prixvente) {
        this.prixvente = prixvente;
    }


    public float getPrixunitairs() {
        return prixunitairs;
    }

    public void setPrixunitairs(float prixunitairs) {
        this.prixunitairs = prixunitairs;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commande_client_id")
    private CommandeClient commandeClient;

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "article_id")
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public CommandeClient getCommandeClient() {
        return commandeClient;
    }

    public void setCommandeClient(CommandeClient commandeClient) {
        this.commandeClient = commandeClient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
