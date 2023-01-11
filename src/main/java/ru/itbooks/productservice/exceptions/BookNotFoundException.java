package ru.itbooks.productservice.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String article) {
        super("Книга с артикулем " + article + " не найдена");
    }
}