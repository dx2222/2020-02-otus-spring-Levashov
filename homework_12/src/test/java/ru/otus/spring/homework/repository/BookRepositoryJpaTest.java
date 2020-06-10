package ru.otus.spring.homework.repository;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.homework.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами ")
@DataJpaTest
class BookRepositoryJpaTest {

    private static final int BOOK_COUNT_NEW_BOOK = 1;
    private static final int BOOK_COUNT_BY_ID = 1;
    private static final int BOOK_COUNT = 3;
    private static final Long DEFAULT_BOOK_ID = 100L;
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final int BOOK_COUNT_DELETE_BOOK = 0;
    private static final String DEFAULT_BOOK_NAME = "11";
    private static final String DEFAULT_BOOK_AUTHOR = "22";
    private static final String DEFAULT_BOOK_GENRE = "33";
    private static final String BOOK_NAME = "BOOK1";
    private static final String NEW_BOOK_NAME = "9999";

    @Autowired
    private BookRepositoryJpa bookJpa;

    @Autowired
    private AuthorRepositoryJpa authorJpa;

    @Autowired
    private GenreRepositoryJpa genreJpa;

    @DisplayName("список всех книг")
    @Test
    void findALl() {
        List<Book> books = bookJpa.findAll();
        assertThat(books).hasSize(BOOK_COUNT);
    }

    @Test
    @DisplayName("добавление книги в базу")
    void insert() {

        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author(null, DEFAULT_BOOK_AUTHOR));

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(null, DEFAULT_BOOK_GENRE));

        Book book  = new Book(null, NEW_BOOK_NAME, authors, genres);
        Book book2 = bookJpa.save(book);
        Book book3 = bookJpa.findById(book2.getId()).orElse(null);
        assertThat(book3).hasFieldOrPropertyWithValue("id", book2.getId());

    }

    @Test
    @DisplayName("удаление книши из базы")
    void deleteByID() {
        bookJpa.deleteById(DEFAULT_BOOK_ID);
        Book book3 = bookJpa.findById(DEFAULT_BOOK_ID).orElse(null);
        assertThat(book3).isEqualTo(null);
    }

    @Test
    @DisplayName("поиск книги по названию")
    void getBookByName() {
        List<Book> books = bookJpa.findByNameLike(DEFAULT_BOOK_NAME);
        Assert.assertTrue(bookJpa.count() > 0);
    }

    @Test
    @DisplayName("поиск книги по автору")
    void getBookByAuthor() {
        List<Book> books = bookJpa.findAllByAuthor(DEFAULT_BOOK_AUTHOR);
        Assert.assertTrue(bookJpa.count() > 0);

    }

    @Test
    @DisplayName("поиск книги по жанру")
    void getBookByGenre() {
        List<Book> books = bookJpa.findAllByGenre(DEFAULT_BOOK_GENRE);
        Assert.assertTrue(bookJpa.count() > 0);
    }

    @Test
    @DisplayName("поиск книги по ID книги")
    void checkByID() {
        Book book = bookJpa.findById(DEFAULT_BOOK_ID).orElse(null);
        assertThat(book).isNotEqualTo(null);
        if (book != null) {
            assertThat(book.getName()).isEqualTo(BOOK_NAME);
        }
    }
}