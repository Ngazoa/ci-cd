package org.akouma.stock;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

@SpringBootApplication
public class StockMainClass implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(StockMainClass.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("1234"))
        ;

    }
}