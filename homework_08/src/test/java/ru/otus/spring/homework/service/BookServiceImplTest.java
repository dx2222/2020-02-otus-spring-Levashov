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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.*;

import ru.otus.spring.homework.BookBaseApplication;

import ru.otus.spring.homework.Repository.*;
import ru.otus.spring.homework.domain.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с книгами ")
@SpringBootTest
@ContextConfiguration(classes= BookBaseApplication.class)
@ExtendWith(SpringExtension.class)
class BookServiceImplTest {


    private static final String DEFAULT_BOOK_ID = "102L";
    private static final String DEAULT_BOOK_NAME = "1111";

    private static final String DEFAULT_GENRE_ID = "1L";
    private static final String DEFAULT_GENRE_NAME = "3333";

    private static final String DEFAULT_AUTHOR_ID = "1L";
    private static final String DEFAULT_AUTHOR_NAME    = "2222";


    @MockBean
    private IOService ioMock;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ConsoleServiceImpl console;

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private MessageSource messageMock;

    @Test
    @DisplayName("добавление книги")
    void insert()  {

        Mockito.when(console.readBookName()).thenReturn(DEAULT_BOOK_NAME);
        Mockito.when(console.readAuthor()).thenReturn(DEFAULT_AUTHOR_NAME);
        Mockito.when(console.readAGenre()).thenReturn(DEFAULT_GENRE_NAME);

        Author author = new Author(DEFAULT_AUTHOR_ID, DEFAULT_AUTHOR_NAME);
        Genre genre = new Genre(DEFAULT_GENRE_ID, DEFAULT_GENRE_NAME);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author);

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(genre);

        Book book  = new Book(DEFAULT_BOOK_ID, DEAULT_BOOK_NAME, authors, genres);

        Mockito.when(bookRepository.save(any())).thenReturn(book);
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.findByName(any())).thenReturn(Optional.of(book));

        Mockito.when(authorRepository.save(any())).thenReturn(author);
        Mockito.when(authorRepository.findByName(any())).thenReturn(Optional.of(author));

        Mockito.when(genreRepository.save(any())).thenReturn(genre);
        Mockito.when(genreRepository.findByName(any())).thenReturn(Optional.of(genre));

        bookService.insertBook();

        ArgumentCaptor<Book> argument = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(console).showBook(argument.capture());

        assertEquals(DEAULT_BOOK_NAME, argument.getValue().getName());
        assertEquals(DEFAULT_AUTHOR_ID, argument.getValue().getAuthor().get(0).getId());
        assertEquals(DEFAULT_AUTHOR_NAME, argument.getValue().getAuthor().get(0).getName());
        assertEquals(DEFAULT_GENRE_ID, argument.getValue().getGenre().get(0).getId());
        assertEquals(DEFAULT_GENRE_NAME, argument.getValue().getGenre().get(0).getName());
    }


}