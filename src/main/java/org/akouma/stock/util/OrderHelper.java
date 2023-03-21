package org.akouma.stock.util;


import org.akouma.stock.entity.ArticleCommandeClient;
import org.akouma.stock.entity.CommandeClient;
import org.akouma.stock.pojo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderHelper {

    public static Order getOrder(Iterable<ArticleCommandeClient> artc, CommandeClient commandeClient) {
        Order order = new Order();
        order.setOrderId(commandeClient.getCodeCommande());
        order.setDate("" + commandeClient.getDateCreation());
        Address address = new Address();
        address.setCity(commandeClient.getBoutique().getName());
        address.setStreet(commandeClient.getBoutique().getTelephone());
        address.setZipCode(commandeClient.getBoutique().getEmail());
        address.setState(commandeClient.getUser().getNameUser());
        address.setAvatar(commandeClient.getBoutique().getAvatar());

        Account account = new Account();
        account.setPhoneNumber(commandeClient.getBoutique().getTelephone());
//        account.setEmail("628jh4h624y@temporary-mail.net");
//        account.setName("Mr. Eugene A King");
        account.setAddress(address);
        order.setAccount(account);

        List<Item> items = new ArrayList<>();


        for (ArticleCommandeClient article : artc) {
            Item item1 = new Item();
            item1.setName(article.getArticle().getName());
            item1.setPrice(BigDecimal.valueOf(article.getPrixunitairs()));
            item1.setQuantity(article.getQuantite());
            float soustotal = article.getQuantite() * article.getPrixunitairs();
            item1.setSku("" + soustotal);
            items.add(item1);
        }
        order.setItems(items);
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(commandeClient.getPrixTotal()));
        payment.setCardNumber("Cash");
        payment.setCvv(commandeClient.getUser().getNameUser());
        payment.setClient(commandeClient.getClient());
//        payment.setMonth("04");
//        payment.setYear("2030");
        order.setPayment(payment);
        return order;
    }


    public static Order getOrder() {
        Order order = new Order();
        order.setOrderId(" Get Pdf generator ");
        order.setDate("2023/02/28");
        Address address = new Address();
        address.setCity("PARIS");
        address.setStreet(" 1 saint cloud 2");
        address.setZipCode("JKK");

        Account account = new Account();
        account.setPhoneNumber("0608789876");
        account.setAddress(address);
        order.setAccount(account);


        return order;
    }
}
