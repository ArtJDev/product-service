package ru.itbooks.productservice.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(
        @NotBlank(message = "Необходимо указать артикул книги")
        @Pattern(regexp = "^([0-9]{5})$", message = "Артикул книги должен состоять из 5 цифр")
        String article,
        @NotBlank(message = "Необходимо указать название книги")
        String title,
        @NotBlank(message = "Необходимо указать автора книги")
        String author,
        @NotNull(message = "Необходимо указать цену книги")
        @Positive(message = "Цена должна быть больше нуля")
        Double price
) {
}