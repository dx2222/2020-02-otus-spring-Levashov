package ru.otus.spring.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.homework.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByNameLike(String name);
    Mono<Book> findByName(String name);
    Flux<Book> findByAuthorNameLike(String author);
    Flux<Book> findByGenreNameLike(String genre);
}
