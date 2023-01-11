package ru.itbooks.productservice.entities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        var book = new Book(1L, "12345", "Title", "Author", 100.0, "ITBooks", now, now, 3);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.article").isEqualTo(book.article());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher").isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate").isEqualTo(book.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate").isEqualTo(book.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2022-12-23T16:07:37.124138Z");
        var content = """
                {
                    "id": 1,
                    "isbn": "123451",
                    "title": "Title",
                    "author": "Author",
                    "price": 100.0,
                    "publisher": "ITBooks",
                    "createdDate": "2022-12-23T16:07:37.124138Z",
                    "lastModifiedDate": "2022-12-23T16:07:37.124138Z",
                    "version": 3
                }
                """;
        assertThat(json.parse(content)).usingRecursiveComparison()
                .isEqualTo(new Book(1L, "12345", "Title", "Author", 100.0, "ITBooks", instant, instant, 3));
    }
}