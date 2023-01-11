package ru.itbooks.productservice.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.itbooks.productservice.config.DataConfig;
import ru.itbooks.productservice.entities.Book;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findAllBooksTest() {
        var book1 = Book.of("12345", "Title", "Author", 90.0, "ITBooks");
        var book2 = Book.of("12346", "Another Title", "Author", 110.0, "ITBooks");
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

        Iterable<Book> actualBooks = productRepository.findAll();

        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
                .filter(book -> book.article().equals(book1.article()) || book.article().equals(book2.article()))
                .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findBookByArticleIfExistingTest() {
        var article = "12345";
        var book = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        jdbcAggregateTemplate.insert(book);

        Optional<Book> actualBook = productRepository.findByArticle(article);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().article()).isEqualTo(book.article());
    }

    @Test
    void findBookByArticleIfNotExistingTest() {
        Optional<Book> actualBook = productRepository.findByArticle("12345");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByArticleExistingTest() {
        var article = "12345";
        var newBook = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        jdbcAggregateTemplate.insert(newBook);

        boolean existing = productRepository.existsByArticle(article);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByArticleNotExistingTest() {
        boolean existing = productRepository.existsByArticle("12345");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByArticleTest() {
        var article = "12345";
        var newBook = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        var persistedBook = jdbcAggregateTemplate.insert(newBook);

        productRepository.deleteByArticle(article);

        assertThat(jdbcAggregateTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }
}