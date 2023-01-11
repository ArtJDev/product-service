package ru.itbooks.productservice.services;

import org.springframework.stereotype.Service;
import ru.itbooks.productservice.entities.Book;
import ru.itbooks.productservice.exceptions.BookAlreadyExistsException;
import ru.itbooks.productservice.exceptions.BookNotFoundException;
import ru.itbooks.productservice.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Book> viewBookList() {
        return productRepository.findAll();
    }

    public Book viewBookDetails(String article) {
        return productRepository.findByArticle(article)
                .orElseThrow(() -> new BookNotFoundException(article));
    }

    public Book addBookToCatalog(Book book) {
        if (productRepository.existsByArticle(book.article())) {
            throw new BookAlreadyExistsException(book.article());
        }
        return productRepository.save(book);
    }

    public void removeBookFromCatalog(String article) {
        productRepository.deleteByArticle(article);
    }

    public Book editBookDetails(String article, Book book) {
        return productRepository.findByArticle(article)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.article(),
                            book.title(),
                            book.author(),
                            book.price(),
                            book.publisher(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.version());
                    return productRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}