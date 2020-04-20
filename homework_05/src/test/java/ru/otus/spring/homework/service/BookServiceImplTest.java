package ru.otus.spring.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import ru.otus.spring.homework.Exceptions.*;
import ru.otus.spring.homework.dao.*;
import ru.otus.spring.homework.domain.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {


    private static final Long DEFAULT_BOOK_ID = 102L;
    private static final String DEAULT_BOOK_NAME = "1111";

    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "3333";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME    = "2222";


    @MockBean
    private IOService ioMock;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private  BookAuthorDao bookAuthorDao;

    @MockBean
    private  BookGenreDao bookGenreDao;

    @MockBean
    private ConsoleServiceImpl console;

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private MessageSource messageMock;

    @Test
    @DisplayName("добавление книги")
    void insert() throws BookExistException, AuthorExistException, GenreExistException {

        Mockito.when(console.ReadBookName()).thenReturn(DEAULT_BOOK_NAME);
        Mockito.when(console.ReadAuthor()).thenReturn(DEFAULT_AUTHOR_NAME);
        Mockito.when(console.ReadAGenre()).thenReturn(DEFAULT_GENRE_NAME);

        Author author = new Author(DEFAULT_AUTHOR_ID, DEFAULT_AUTHOR_NAME);
        Genre genre = new Genre(DEFAULT_GENRE_ID, DEFAULT_GENRE_NAME);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author);

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(genre);

        Book book  = new Book(DEFAULT_BOOK_ID, DEAULT_BOOK_NAME, authors, genres);

        Mockito.when(bookDao.insert(any())).thenReturn(book);
        Mockito.when(authorDao.getAuthorByName(anyString())).thenReturn(author);
        Mockito.when(genreDao.getGenreByName(any())).thenReturn(genre);
        Mockito.when(authorDao.insert(any())).thenReturn(author);
        Mockito.when(genreDao.insert(any())).thenReturn(genre);
        Mockito.when(bookDao.getBookByID(any())).thenReturn(book);
        Mockito.when(bookAuthorDao.findByBookID(book.getBookID())).thenReturn(authors);
        Mockito.when(bookGenreDao.findByBookID(book.getBookID())).thenReturn(genres);

        bookService.insertBook();

        Mockito.verify(bookAuthorDao).deleteByBookID(any());
        Mockito.verify(bookGenreDao).deleteByBookID(any());

        ArgumentCaptor<Book> argument = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(console).ShowBook(argument.capture());

        assertEquals(DEAULT_BOOK_NAME, argument.getValue().getName());
        assertEquals(DEFAULT_AUTHOR_ID, argument.getValue().getAuthors().get(0).getAuthorID());
        assertEquals(DEFAULT_GENRE_ID, argument.getValue().getGenres().get(0).getGenreID());
    }

}