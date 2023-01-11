package ru.itbooks.productservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itbooks.productservice.entities.Book;
import ru.itbooks.productservice.exceptions.BookAlreadyExistsException;
import ru.itbooks.productservice.exceptions.BookNotFoundException;
import ru.itbooks.productservice.repositories.ProductRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void bookAlreadyExistsExceptionTest() {
        var article = "12345";
        var bookToCreate = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        when(productRepository.existsByArticle(article)).thenReturn(true);
        assertThatThrownBy(() -> productService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("Книга с артикулем " + article + " уже существует");
    }

    @Test
    void bookNotFoundExceptionTest() {
        var article = "12345";
        when(productRepository.findByArticle(article)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.viewBookDetails(article))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Книга с артикулем " + article + " не найдена");
    }
}