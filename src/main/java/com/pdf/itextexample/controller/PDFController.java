package com.pdf.itextexample.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.pdf.itextexample.OrderHelper;
import com.pdf.itextexample.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.w3c.tidy.Tidy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/orders")
public class PDFController {


    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    public PDFController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RequestMapping(path = "/")
    public String getOrderPage(Model model) {
        Order order = OrderHelper.getOrder();
        model.addAttribute("orderEntry", order);
        return "order";
    }

    @RequestMapping(path = "/pdf")
    public void getPDF(HttpServletRequest request, HttpServletResponse response) {

        String pdfFileName = "invoice.pdf";

        // Business Logic

        Order order = OrderHelper.getOrder();

        //Create HTML using Thymeleaf template Engine

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("receipt", context);

        //Setup Source and target I/O streams

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8092");

        //Call convert method
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        //extract output as bytes
        byte[] bytes = target.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(pdfDirectory + pdfFileName)) {
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Send the response as downloadable PDF

  /*      return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receipt.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);*/


    }

    private String convertToXhtml(String html) {
        ByteArrayOutputStream outputStream = null;
        try {
            Tidy tidy = new Tidy();
            tidy.setInputEncoding(String.valueOf(StandardCharsets.UTF_8));
            tidy.setOutputEncoding(String.valueOf(StandardCharsets.UTF_8));
            tidy.setXHTML(true);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            outputStream = new ByteArrayOutputStream();
            tidy.parseDOM(inputStream, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }

}