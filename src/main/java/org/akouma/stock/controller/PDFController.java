package org.akouma.stock.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import org.akouma.stock.entity.ArticleCommandeClient;
import org.akouma.stock.entity.CommandeClient;
import org.akouma.stock.pojo.Order;
import org.akouma.stock.service.ArticleCommandeClientService;
import org.akouma.stock.util.OrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class PDFController {


    @Autowired
    ServletContext servletContext;

    private final TemplateEngine templateEngine;
    @Autowired
    private ArticleCommandeClientService articleCommandeClientService;

    public PDFController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping( "/first")
    public String getOrderPage(Model model) {
        //Order order = OrderHelper.getOrder();
        //model.addAttribute("orderEntry", order);
        return "pdf/order";
    }

    @GetMapping("/pdf-generatot-commande-client-{id}")
    public ResponseEntity<?> getPDF(@PathVariable("id") CommandeClient commandeClient,HttpServletRequest request, HttpServletResponse response) throws IOException {

    Iterable<ArticleCommandeClient> articleCommand = articleCommandeClientService.getarticleCommande(commandeClient);

        Order order = OrderHelper.getOrder(articleCommand,commandeClient);

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("pdf/order", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:9000");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }
    @GetMapping("/pdf-generated")
    public ResponseEntity<?> getPDFg(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


        Order order = OrderHelper.getOrder();

        /* Create HTML using Thymeleaf template Engine */

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("orderEntry", order);
        String orderHtml = templateEngine.process("pdf/pdf-generated", context);

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:9000");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pdf-generated.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }


}
