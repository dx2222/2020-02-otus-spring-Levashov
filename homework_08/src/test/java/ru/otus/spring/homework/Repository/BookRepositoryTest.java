package ru.otus.spring.homework.Repository;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.homework.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами ")
@ComponentScan("ru.otus.spring.homework.events")
class BookRepositoryTest extends AbstractRepositoryTest{

    private static final int BOOK_COUNT = 3;

    private static final String DEFAULT_BOOK_NAME = "BOOK1";
    private static final String DEFAULT_BOOK_AUTHOR = "AUTHOR1";
    private static final String DEFAULT_BOOK_GENRE = "GENRE1";
    private static final String BOOK_NAME = "1";
    private static final String NEW_BOOK_NAME = "BOOK9";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("список всех книг")
    @Test
    void findALl() {
        List<Book> books = bookRepository.findAll();
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
        Book book2 = bookRepository.save(book);
        Book book3 = bookRepository.findById(book2.getId()).orElse(null);
        assertThat(book3).hasFieldOrPropertyWithValue("id", book2.getId());
        bookRepository.deleteById(book3.getId());
    }

    @Test
    @DisplayName("удаление книши из базы")
    void deleteByID() {
        Book book = bookRepository.findByName(DEFAULT_BOOK_NAME).orElse(null);
        assertThat(book).isNotEqualTo(null);
        if (book != null) {
            bookRepository.deleteById(book.getId());
            Book book3 = bookRepository.findById(book.getId()).orElse(null);
            assertThat(book3).isEqualTo(null);
        }
    }

    @Test
    @DisplayName("поиск книги по названию")
    void getBookByName() {
        List<Book> books = bookRepository.findByNameLike(DEFAULT_BOOK_NAME);
        Assert.assertTrue(books.size() > 0);
    }

    @Test
    @DisplayName("поиск книги по автору")
    void getBookByAuthor() {
        Author author = authorRepository.findByName(DEFAULT_BOOK_AUTHOR).orElse(null);
        assertThat(author).isNotEqualTo(null);
        if (author != null) {
            List<String> listid= new ArrayList<String>();
            listid.add(author.getId());
            List<Book> books = bookRepository.findByAuthorIdIn(listid);
            Assert.assertTrue(books.size() > 0);
        }
    }

    @Test
    @DisplayName("поиск книги по жанру")
    void getBookByGenre() {
        Genre genre = genreRepository.findByName(DEFAULT_BOOK_GENRE).orElse(null);
        assertThat(genre).isNotEqualTo(null);
        if (genre != null) {
            List<String> listid = new ArrayList<String>();
            listid.add(genre.getId());
            List<Book> books = bookRepository.findByGenreIdIn(listid);
            Assert.assertTrue(books.size() > 0);
        }
    }

}