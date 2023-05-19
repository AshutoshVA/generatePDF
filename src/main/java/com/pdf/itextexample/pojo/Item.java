package com.pdf.itextexample.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private Integer amount;
    private Integer convenienceFee;
    private double gst;
    private double totalAmount;
    private String customerName;
    private String invoiceNo;
    private String mobileNo;
    private String email;
}
