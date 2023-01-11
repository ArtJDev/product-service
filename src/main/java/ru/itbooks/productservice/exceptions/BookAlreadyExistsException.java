package ru.itbooks.productservice.exceptions;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(String article) {
        super("Книга с артикулем " + article + " уже существует");
    }
}