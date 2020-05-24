package ru.otus.spring.homework.repository;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.homework.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@DisplayName("Repository для работы с книгами ")
@ComponentScan("ru.otus.spring.homework.events")
class BookRepositoryTest extends AbstractRepositoryTest{

    private static final int BOOK_COUNT = 3;

    private static final String DEFAULT_BOOK_NAME = "BOOK1";
    private static final String DEFAULT_BOOK_AUTHOR = "AUTHOR1";
    private static final String DEFAULT_BOOK_GENRE = "GENRE1";
    private static final int BOOK_COUNT_FIND = 1;
    private static final String NEW_BOOK_NAME = "BOOK9";

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("список всех книг")
    @Test
    void findALl() {
        Flux<Book> books = bookRepository.findAll();
        StepVerifier
                .create(books)
                .expectNextCount(BOOK_COUNT)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("добавление книги в базу")
    void insert() {

        Book book  = Book.builder()
                        .name(NEW_BOOK_NAME)
                        .author(Collections.singletonList(Author.builder().name(DEFAULT_BOOK_AUTHOR).build()))
                        .genre(Collections.singletonList(Genre.builder().name(DEFAULT_BOOK_GENRE).build()))
                        .build();

        Mono<Book>  book2 = bookRepository.save(book);

        StepVerifier
                .create(bookRepository.findByName(DEFAULT_BOOK_NAME))
                .assertNext(booktest -> assertNotNull(booktest.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("поиск книги по названию")
    void getBookByName() {
        Flux<Book> books = bookRepository.findByNameLike(DEFAULT_BOOK_NAME);
        StepVerifier
                .create(books)
                .expectNextCount(BOOK_COUNT_FIND)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("поиск книги по автору")
    void getBookByAuthor() {

        Flux<Book> books = bookRepository.findByAuthorNameLike(DEFAULT_BOOK_AUTHOR);
        StepVerifier
                .create(books)
                .expectNextCount(BOOK_COUNT_FIND)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("поиск книги по жанру")
    void getBookByGenre() {

        Flux<Book> books = bookRepository.findByGenreNameLike(DEFAULT_BOOK_GENRE);
        StepVerifier
                .create(books)
                .expectNextCount(BOOK_COUNT_FIND)
                .expectComplete()
                .verify();
    }

}