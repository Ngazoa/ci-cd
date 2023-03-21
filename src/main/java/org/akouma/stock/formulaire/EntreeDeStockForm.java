package org.akouma.stock.formulaire;

import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.ArticleCommandeFournissseur;
import org.akouma.stock.entity.Fournisseur;

public class EntreeDeStockForm {
    private Fournisseur fournisseur;
    private Article article;
    private int quantite=0;

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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public EntreeDeStockForm() {
    }

    public EntreeDeStockForm(ArticleCommandeFournissseur articleCommandeFournissseur) {
        this.fournisseur = articleCommandeFournissseur.getFournisseur();
        this.article = articleCommandeFournissseur.getArticle();
        this.quantite = articleCommandeFournissseur.getQuantite();
    }

}
