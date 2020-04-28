package ru.otus.spring.homework.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.homework.BookBaseApplication;
import ru.otus.spring.homework.domain.*;
import ru.otus.spring.homework.Exceptions.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами ")
@DataJpaTest
@ContextConfiguration(classes= BookBaseApplication.class)
@Import({BookRepositoryJpaImpl.class, AuthorRepositoryJpaImpl.class, GenreRepositoryJpaImpl.class})
class BookRepositoryJpaTest {

    private static final int BOOK_COUNT_NEW_BOOK = 1;
    private static final int BOOK_COUNT_BY_ID = 1;
    private static final int BOOK_COUNT = 2;
    private static final Long DEFAULT_BOOK_ID = 100L;
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final int BOOK_COUNT_DELETE_BOOK = 0;
    private static final String DEFAULT_BOOK_NAME = "11";
    private static final String DEFAULT_BOOK_AUTHOR = "22";
    private static final String DEFAULT_BOOK_GENRE = "33";
    private static final String BOOK_NAME = "1111";
    private static final String NEW_BOOK_NAME = "9999";

    @Autowired
    private BookRepositoryJpaImpl bookJpa;

    @Autowired
    private AuthorRepositoryJpaImpl authorJpa;

    @Autowired
    private GenreRepositoryJpaImpl genreDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("список всех книг")
    @Test
    void findALl() {
        List<Book> books = bookJpa.findAll();
        assertThat(books).hasSize(BOOK_COUNT);
    }

    @Test
    @DisplayName("добавление книги в базу")
    void insert() throws EntityExistException {

        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author(null, DEFAULT_BOOK_AUTHOR));

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(null, DEFAULT_BOOK_GENRE));

        Book book  = new Book(null, NEW_BOOK_NAME, authors, genres);
        Book book2 = bookJpa.insert(book);
        Book book3 = bookJpa.getBookByID(book2.getId());
        assertThat(book3).hasFieldOrPropertyWithValue("id", book2.getId());

    }

    @Test
    @DisplayName("удаление книши из базы")
    void deleteByID() {
        bookJpa.deleteByID(DEFAULT_BOOK_ID);
        Book book3 = bookJpa.getBookByID(DEFAULT_BOOK_ID);
        assertThat(book3).isEqualTo(null);
    }

    @Test
    @DisplayName("поиск книги по названию")
    void getBookByName() {
        List<Book> books = bookJpa.findAllByName(DEFAULT_BOOK_NAME);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("id", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по автору")
    void getBookByAuthor() {
        List<Book> books = bookJpa.findAllByAuthor(DEFAULT_BOOK_AUTHOR);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("id", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по жанру")
    void getBookByGenre() {
        List<Book> books = bookJpa.findAllByGenre(DEFAULT_BOOK_GENRE);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("id", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по ID книги")
    void checkByID() {
        Book book = bookJpa.getBookByID(DEFAULT_BOOK_ID);
        assertThat(book.getName()).isEqualTo(BOOK_NAME);
    }
}