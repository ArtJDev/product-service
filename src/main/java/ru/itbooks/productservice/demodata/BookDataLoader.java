package ru.itbooks.productservice.demodata;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.itbooks.productservice.entities.Book;
import ru.itbooks.productservice.repositories.ProductRepository;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {

    private final ProductRepository productRepository;

    public BookDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        productRepository.deleteAll();
        var book1 = Book.of("00100", "Spring in Action", "Craig Walls", 1790.0, "Manning Publications");
        var book2 = Book.of("00101", "Spring Security in Action", "Laurentiu Spilca", 1590.0, "Manning Publications");
        var book3 = Book.of("00102", "Spring Boot: Up and Running", "Mark Heckler", 1290.0, "O'Reilly");
        productRepository.saveAll(List.of(book1, book2, book3));
    }
}