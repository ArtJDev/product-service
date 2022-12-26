package ru.itbooks.productservice.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itbooks.productservice.entities.Book;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void allFieldsCorrectValidationTest() {
        var book = Book.of("12345", "Title", "Author", 100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void articleNotDefinedAndIncorrectValidationTest() {
        var book = Book.of("", "Title", "Author", 100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages)
                .contains("Необходимо указать артикул книги")
                .contains("Артикул книги должен состоять из 5 цифр");
    }

    @Test
    void articleDefinedButIncorrectValidationTest() {
        var book = Book.of("a2345", "Title", "Author", 100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Артикул книги должен состоять из 5 цифр");
    }

    @Test
    void titleNotDefinedValidationTest() {
        var book = Book.of("12345", "", "Author", 100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Необходимо указать название книги");
    }

    @Test
    void authorNotDefinedValidationTest() {
        var book = Book.of("12345", "Title", "", 100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Необходимо указать автора книги");
    }

    @Test
    void priceNotDefinedValidationTest() {
        var book = Book.of("12345", "Title", "Author", null, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Необходимо указать цену книги");
    }

    @Test
    void priceDefinedButZeroValidationTest() {
        var book = Book.of("12345", "Title", "Author", 0.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Цена должна быть больше нуля");
    }

    @Test
    void whenPriceDefinedButNegativeThenValidationFails() {
        var book = Book.of("12345", "Title", "Author", -100.0, "ITBooks");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Цена должна быть больше нуля");
    }

    @Test
    void publisherNotDefinedValidationTest() {
        Book book = Book.of("12345", "Title", "Author", 100.0,null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }
}
