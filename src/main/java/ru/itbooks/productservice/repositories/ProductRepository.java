package ru.itbooks.productservice.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itbooks.productservice.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByArticle(String article);

    boolean existsByArticle(String article);

    @Modifying
    @Transactional
    @Query("delete from Book where article = :article")
    void deleteByArticle(String article);
}