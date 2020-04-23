package ru.otus.spring.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.homework.config.ApplicationSettings;
import ru.otus.spring.homework.domain.*;
import ru.otus.spring.homework.Exceptions.*;
import ru.otus.spring.homework.service.MessageServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами ")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoJdbcTest {

    private static final int BOOK_COUNT_NEW_BOOK = 1;
    private static final int BOOK_COUNT_BY_ID = 1;
    private static final Long DEFAULT_BOOK_ID = 100L;
    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final int BOOK_COUNT_DELETE_BOOK = 0;
    private static final String DEFAULT_BOOK_NAME = "11";
    private static final String DEFAULT_BOOK_AUTHOR = "22";
    private static final String DEFAULT_BOOK_GENRE = "33";

    private static final String NEW_BOOK_NAME = "9999";

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("добавление книги в базу")
    void insert() throws EntityExistException {

        List<Author> authors = new ArrayList<Author>();
        authors.add(new Author(DEFAULT_AUTHOR_ID, DEFAULT_BOOK_AUTHOR));

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(new Genre(DEFAULT_GENRE_ID, DEFAULT_BOOK_GENRE));

        Book book  = new Book(0L, NEW_BOOK_NAME, authors, genres);
        Book book2 = bookDaoJdbc.insert(book);
        assertThat(bookDaoJdbc.countByID(book2.getBookID())).isEqualTo(BOOK_COUNT_NEW_BOOK);

        Book book3 = bookDaoJdbc.getBookByID(book2.getBookID());
        assertThat(book3).hasFieldOrPropertyWithValue("bookID", book2.getBookID());

    }

    @Test
    @DisplayName("удаление книши из базы")
    void deleteByID() {
        bookDaoJdbc.deleteByID(DEFAULT_BOOK_ID);
        assertThat(bookDaoJdbc.countByID(DEFAULT_BOOK_ID)).isEqualTo(BOOK_COUNT_DELETE_BOOK);
    }

    @Test
    @DisplayName("поиск книги по названию")
    void getBookByName() {
        List<Book> books = bookDaoJdbc.findAllByName(DEFAULT_BOOK_NAME);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("bookID", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по автору")
    void getBookByAuthor() {
        List<Book> books = bookDaoJdbc.findAllByAuthor(DEFAULT_BOOK_AUTHOR);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("bookID", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по жанру")
    void getBookByGenre() {
        List<Book> books = bookDaoJdbc.findAllByGenre(DEFAULT_BOOK_GENRE);
        Book book = books.get(0);
        assertThat(book).hasFieldOrPropertyWithValue("bookID", DEFAULT_BOOK_ID);
    }

    @Test
    @DisplayName("поиск книги по ID книги")
    void checkByID() {
        assertThat(bookDaoJdbc.countByID(DEFAULT_BOOK_ID)).isEqualTo(BOOK_COUNT_BY_ID);
    }
}