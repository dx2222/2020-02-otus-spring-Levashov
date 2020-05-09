package ru.otus.spring.homework.restcontroller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("BookRestController")
public class BookRestControllerTest {

    private static final Long DEFAULT_BOOK_ID = 102L;
    private static final String DEAULT_BOOK_NAME = "1111";

    private static final Long DEFAULT_GENRE_ID = 1L;
    private static final String DEFAULT_GENRE_NAME = "3333";

    private static final Long DEFAULT_AUTHOR_ID = 1L;
    private static final String DEFAULT_AUTHOR_NAME    = "2222";

    @Autowired
    private BookRestController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @Test
    public void booksPageTest() throws Exception {
        List<Book> books = new ArrayList<Book>();

        Author author = new Author(DEFAULT_AUTHOR_ID, DEFAULT_AUTHOR_NAME);
        Genre genre = new Genre(DEFAULT_GENRE_ID, DEFAULT_GENRE_NAME);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author);

        List<Genre> genres = new ArrayList<Genre>();
        genres.add(genre);

        books.add(Book.builder().id(DEFAULT_BOOK_ID).name(DEAULT_BOOK_NAME).author(authors).genre(genres).build());

        Mockito.when(bookService.findAllBook()).thenReturn(books);

        mvc.perform(get("/books"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$[0].name",Matchers.is(DEAULT_BOOK_NAME)))
                 .andExpect(jsonPath("$[0].authors",Matchers.is(DEFAULT_AUTHOR_NAME)))
                 .andExpect(jsonPath("$[0].genres",Matchers.is(DEFAULT_GENRE_NAME)))
        ;
    }

}
