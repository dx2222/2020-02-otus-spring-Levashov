package ru.otus.spring.homework.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByName(String name);
    List <Book> findByNameLike(String name);

    List <Book> findByAuthorIdIn(List<String> authorId);
    List <Book> findByGenreIdIn(List<String> genreId);

}
