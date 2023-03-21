package org.akouma.stock.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String orderId;
    private String date;
    private Account account;
    private Payment payment;
    private List<Item> items;
}
