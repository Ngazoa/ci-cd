package org.akouma.stock.formulaire;

import lombok.Data;
import org.akouma.stock.entity.Article;
import org.akouma.stock.entity.CathegorieArticle;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArticleForm {
    private Long id;
    private String name;
    private String code;
    private CathegorieArticle cathegorie;
    private float prixAchat;
    private float prixVente;
    private int quantite;
    private  Article article;
    private MultipartFile logo;
    private MultipartFile avatar;

    public ArticleForm(Article article) {
        this.article=article;
        this.id=article.getId();
        this.code=article.getCode();
        this.name= article.getName();
        this.quantite=getQuantite();
        this.cathegorie=article.getCathegorieArticle();
        this.prixVente=article.getPrixDeVente();
        this.prixAchat= article.getPrixAchat();
        this.avatar=null;


    }

    public ArticleForm() {

    }
}
