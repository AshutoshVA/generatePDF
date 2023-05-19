package com.pdf.itextexample;

import com.pdf.itextexample.pojo.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderHelper {

    public static final Integer AMOUNT = 3000;
    public static final Integer CONVENIENCE_FEE = 64;
    public static final double GST = 18;

    public static Order getOrder() {
        Order order = new Order();
        order.setMerchantName("ABC");
        Date date = new Date();
        order.setDate(currentDate(date));
        order.setTxnId("TXN665");
        order.setAmount(AMOUNT);

        List<Item> items = new ArrayList<>();
        order.setItems(items);
        Item item1 = new Item();
        item1.setAmount(AMOUNT);
        item1.setConvenienceFee(CONVENIENCE_FEE);
        item1.setGst(GST);
        double gst = (AMOUNT + CONVENIENCE_FEE) * GST / 100;
        double totalAmount = AMOUNT + gst;
        item1.setTotalAmount(totalAmount);
        item1.setCustomerName("Ashutosh Abhang");
        item1.setInvoiceNo("INV2321");
        item1.setMobileNo("9734793243");
        item1.setEmail("abc@gmail.com");
        items.add(item1);


        return order;
    }

    public static String currentDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }
}
