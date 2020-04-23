package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.dao.*;
import ru.otus.spring.homework.domain.*;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final ConsoleService consoleService;
    private final MessageService messageService;

    @Autowired
    public BookServiceImpl(BookDao bookDao,
                           AuthorDao authorDao,
                           GenreDao genreDao,
                           ConsoleService consoleService,
                           MessageService messageService) {
        this.bookDao   = bookDao;
        this.authorDao = authorDao;
        this.genreDao  = genreDao;
        this.consoleService = consoleService;
        this.messageService = messageService;
    }

    public void insertBook() {
        String bookName = consoleService.readBookName();

        List<Author> authors = Author.getAuthorList(consoleService.readAuthor().split(","));

        List<Genre>  genres = Genre.getGenreList(consoleService.readAGenre().split(","));

        try {
            Book book = bookDao.insert(new Book(0L,bookName,  authors ,  genres));

            consoleService.showBook(bookDao.getBookByID(book.getBookID()));

        } catch (EntityExistException e) {
             consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void updateBook() {
        Long bookID = consoleService.readBookID();
        Book book = bookDao.getBookByID(bookID);
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
                book.setAuthors(authorsList);
                updateed = true;
            }


            consoleService.showBookOldGenres(book);
            String genres = consoleService.readAGenre();
            if (!genres.trim().equals("")) {
                List<Genre> genresList = Genre.getGenreList(genres.split(","));
                book.setGenres(genresList);
                updateed = true;
            }


            if (updateed) {
                try {
                    bookDao.update(book);

                    consoleService.showBook(bookDao.getBookByID(bookID));

                } catch (EntityExistException e) {
                    consoleService.bookErrorInsert(e.getMessage());
                }
            }
        }
    }


    public void deleteBook() {
        Long bookID = consoleService.readBookID();
        Book book =  bookDao.getBookByID(bookID);

        if (book != null) {
            bookDao.deleteByID(book.getBookID());
        } else {
            consoleService.bookNotExists();
        }
    }

    public void selectBookByID(){
      Long bookID = consoleService.readBookID();
      Book book =  bookDao.getBookByID(bookID);
      if (book != null) {
          consoleService.showBook(book);
      } else {
          consoleService.bookNotExists();
      }
    }


    public void findAllBook() {
        consoleService.showBooks(bookDao.findAll());
    }

    public void findAllAuthor() {
        consoleService.showAuthors(authorDao.findAll());
    }

    public void findAllGenre() {
        consoleService.showGenres(genreDao.findAll());
    }


    public void findAllBookByName() {
        String bookName = consoleService.readBookName();
        consoleService.showBooks(bookDao.findAllByName(bookName));
    }

    public void findAllBookByAuthor() {
        String author = consoleService.readAuthor();
        consoleService.showBooks(bookDao.findAllByAuthor(author));
    }

    public void findAllBookByGenre() {
        String genre = consoleService.readAGenre();
        consoleService.showBooks(bookDao.findAllByGenre(genre));
    }


}
