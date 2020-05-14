package ru.otus.spring.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;

import ru.otus.spring.homework.WebBookBaseApplication;

import ru.otus.spring.homework.repository.AuthorRepositoryJpa;
import ru.otus.spring.homework.repository.BookRepositoryJpa;
import ru.otus.spring.homework.repository.GenreRepositoryJpa;
import ru.otus.spring.homework.domain.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest
@ContextConfiguration(classes= WebBookBaseApplication.class)
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {


    private static final Long DEFAULT_BOOK_ID = 102L;
    private static final String DEAULT_BOOK_NAME = "1111";

    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "3333";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME    = "2222";


    @MockBean
    private AuthorRepositoryJpa authorRepositoryJpa;

    @MockBean
    private GenreRepositoryJpa genreRepositoryJpa;

    @MockBean
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    @DisplayName("добавление книги")
    void insert()  {

        Author author = new Author(DEFAULT_AUTHOR_ID, DEFAULT_AUTHOR_NAME);
        Genre genre = new Genre(DEFAULT_GENRE_ID, DEFAULT_GENRE_NAME);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author);

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(genre);

        Book book  = new Book(DEFAULT_BOOK_ID, DEAULT_BOOK_NAME, authors, genres);

        Mockito.when(bookRepositoryJpa.save(any())).thenReturn(book);
        Mockito.when(bookRepositoryJpa.findById(any())).thenReturn(Optional.of(book));

        Book booknew  = bookService.insertBook(DEAULT_BOOK_NAME, DEFAULT_AUTHOR_NAME, DEFAULT_GENRE_NAME);

        assertEquals(DEAULT_BOOK_NAME, booknew.getName());
        assertEquals(DEFAULT_AUTHOR_ID,booknew.getAuthor().get(0).getId());
        assertEquals(DEFAULT_AUTHOR_NAME,booknew.getAuthor().get(0).getName());
        assertEquals(DEFAULT_GENRE_ID, booknew.getGenre().get(0).getId());
        assertEquals(DEFAULT_GENRE_NAME, booknew.getGenre().get(0).getName());
    }

}