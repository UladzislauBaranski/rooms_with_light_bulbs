package com.gmail.vladbaransky.webmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.gmail.vladbaransky.webmodule",
        "com.gmail.vladbaransky.service",
        "com.gmail.vladbaransky.repository"})
public class WebModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }
}
