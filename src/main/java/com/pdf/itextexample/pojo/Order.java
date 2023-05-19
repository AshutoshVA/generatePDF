package com.pdf.itextexample.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String merchantName;
    private String txnId;
    private String date;
    private Integer amount;
    private List<Item> items;
}
