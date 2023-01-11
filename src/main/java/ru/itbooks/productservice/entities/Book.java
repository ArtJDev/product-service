package ru.itbooks.productservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Book(
        @Id
        Long id,
        @NotBlank(message = "Необходимо указать артикул книги")
        @Pattern(regexp = "^([0-9]{5})$", message = "Артикул книги должен состоять из 5 цифр")
        String article,
        @NotBlank(message = "Необходимо указать название книги")
        String title,
        @NotBlank(message = "Необходимо указать автора книги")
        String author,
        @NotNull(message = "Необходимо указать цену книги")
        @Positive(message = "Цена должна быть больше нуля")
        Double price,
        String publisher,
        @CreatedDate
        Instant createdDate,
        @LastModifiedDate
        Instant lastModifiedDate,
        @Version
        int version
) {
    public static Book of(String article, String title, String author, Double price, String publisher) {
        return new Book(null, article, title, author, price, publisher, null, null, 0);
    }
}