package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.Repository.*;
import ru.otus.spring.homework.domain.*;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryJpa bookRepository;
    private final AuthorRepositoryJpa authorRepository;
    private final GenreRepositoryJpa genreRepository;

    private final ConsoleService consoleService;
    private final MessageService messageService;

    @Autowired
    public BookServiceImpl(BookRepositoryJpa bookRepository,
                           AuthorRepositoryJpa authorRepository,
                           GenreRepositoryJpa genreRepository,
                           CommentRepositoryJpa commentRepository,
                           ConsoleService consoleService,
                           MessageService messageService) {
        this.bookRepository   = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository  = genreRepository;
        this.consoleService = consoleService;
        this.messageService = messageService;
    }

    public void insertBook() {
        String bookName = consoleService.readBookName();

        List<Author> authors = Author.getAuthorList(consoleService.readAuthor().split(","));

        List<Genre>  genres = Genre.getGenreList(consoleService.readAGenre().split(","));

        try {
            Book book = bookRepository.insert(Book.builder().name(bookName).author(authors).genre(genres).build());

            consoleService.showBook(bookRepository.getBookByID(book.getId()));

        } catch (EntityExistException e) {
             consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void updateBook() {
        Long bookID = consoleService.readBookID();
        Book book = bookRepository.getBookByID(bookID);
        boolean updateed = false;

        if (book != null) {
            consoleService.showBookOld(book);
            String bookName = consoleService.readBookName();
            if (!bookName.trim().equals("")) {
                book.setName(bookName);
                updateed = true;
            }

            consoleService.showBookOldAuthors(book);
            String authors = consoleService.readAuthor();
            if (!authors.trim().equals("")) {
                List<Author> authorsList = Author.getAuthorList(authors.split(","));
                book.setAuthor(authorsList);
                updateed = true;
            }


            consoleService.showBookOldGenres(book);
            String genres = consoleService.readAGenre();
            if (!genres.trim().equals("")) {
                List<Genre> genresList = Genre.getGenreList(genres.split(","));
                book.setGenre(genresList);
                updateed = true;
            }


            if (updateed) {
                try {
                    bookRepository.update(book);

                    consoleService.showBook(bookRepository.getBookByID(bookID));

                } catch (EntityExistException e) {
                    consoleService.bookErrorInsert(e.getMessage());
                }
            }
        }
    }


    public void deleteBook() {
        Long bookID = consoleService.readBookID();
        Book book =  bookRepository.getBookByID(bookID);

        if (book != null) {
            bookRepository.deleteByID(book.getId());
        } else {
            consoleService.bookNotExists();
        }
    }

    public void selectBookByID(){
      Long bookID = consoleService.readBookID();
      Book book =  bookRepository.getBookByID(bookID);
      if (book != null) {
          consoleService.showBook(book);
      } else {
          consoleService.bookNotExists();
      }
    }


    public void findAllBook() {
        consoleService.showBooks(bookRepository.findAll());
    }

    public void findAllAuthor() {
        consoleService.showAuthors(authorRepository.findAll());
    }

    public void findAllGenre() {
        consoleService.showGenres(genreRepository.findAll());
    }

    public void findAllBookByName() {
        String bookName = consoleService.readBookName();
        consoleService.showBooks(bookRepository.findAllByName(bookName));
    }

    public void findAllBookByAuthor() {
        String author = consoleService.readAuthor();
        consoleService.showBooks(bookRepository.findAllByAuthor(author));
    }

    public void findAllBookByGenre() {
        String genre = consoleService.readAGenre();
        consoleService.showBooks(bookRepository.findAllByGenre(genre));
    }


}
