package ru.itbooks.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.itbooks.productservice.entities.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class ProductServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void addBookAndGetBookByIdTest() {
        var article = "12345";
        var newBook = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();

        webTestClient
                .get()
                .uri("/books/" + article)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(createdBook -> {
                    assertThat(createdBook).isNotNull();
                    assertThat(createdBook.article()).isEqualTo(expectedBook.article());
                });
    }

    @Test
    void createBookTest() {
        var expectedBook = Book.of("12346", "Title", "Author", 100.0, "ITBooks");

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(createdBook -> {
                    assertThat(createdBook).isNotNull();
                    assertThat(createdBook.article()).isEqualTo(expectedBook.article());
                });
    }

    @Test
    void createBookAndUpdateBookTest() {
        var article = "12347";
        var newBook = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        Book createdBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> assertThat(book).isNotNull())
                .returnResult().getResponseBody();
        var bookToUpdate = new Book(createdBook.id(), createdBook.article(), createdBook.title(), createdBook.author(), 90.0,
                createdBook.publisher(), createdBook.createdDate(), createdBook.lastModifiedDate(), createdBook.version());

        webTestClient
                .put()
                .uri("/books/" + article)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }

    @Test
    void createBookAndDeleteBookTest() {
        var article = "12348";
        var newBook = Book.of(article, "Title", "Author", 100.0, "ITBooks");
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated();

        webTestClient
                .delete()
                .uri("/books/" + article)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + article)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errorMessage ->
                        assertThat(errorMessage).isEqualTo("Книга с артикулем " + article + " не найдена")
                );
    }
}