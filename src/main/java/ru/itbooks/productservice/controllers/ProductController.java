package ru.itbooks.productservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itbooks.productservice.entities.Book;
import ru.itbooks.productservice.services.ProductService;

@RestController
@RequestMapping("books")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Iterable<Book> get() {
        return productService.viewBookList();
    }

    @GetMapping("{article}")
    public Book getByArticle(@PathVariable String article) {
        return productService.viewBookDetails(article);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
        return productService.addBookToCatalog(book);
    }

    @DeleteMapping("{article}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String article) {
        productService.removeBookFromCatalog(article);
    }

    @PutMapping("{article}")
    public Book put(@PathVariable String article, @Valid @RequestBody Book book) {
        return productService.editBookDetails(article, book);
    }
}