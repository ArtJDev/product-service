package ru.itbooks.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itbooks.productservice.config.ItBooksProperties;

@RestController
public class HomeController {
    private final ItBooksProperties itBooksProperties;

    public HomeController(ItBooksProperties itBooksProperties) {
        this.itBooksProperties = itBooksProperties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return itBooksProperties.getGreeting();
    }
}