package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.Exceptions.AuthorExistException;
import ru.otus.spring.homework.Exceptions.BookExistException;
import ru.otus.spring.homework.Exceptions.GenreExistException;
import ru.otus.spring.homework.dao.*;
import ru.otus.spring.homework.domain.*;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookAuthorDao bookAuthorDao;
    private final BookGenreDao bookGenreDao;
    private final ConsoleService consoleService;
    private final MessageService messageService;

    @Autowired
    public BookServiceImpl(BookDao bookDao,
                           AuthorDao authorDao,
                           GenreDao genreDao,
                           BookAuthorDao bookAuthorDao,
                           BookGenreDao bookGenreDao,
                           ConsoleService consoleService,
                           MessageService messageService) {
        this.bookDao   = bookDao;
        this.authorDao = authorDao;
        this.genreDao  = genreDao;
        this.bookAuthorDao = bookAuthorDao;
        this.bookGenreDao  = bookGenreDao;
        this.consoleService = consoleService;
        this.messageService = messageService;
    }

    public void insertBook() {
        String bookName = consoleService.ReadBookName();

        List<Author> authors = Author.getAuthorList(consoleService.ReadAuthor().split(","));

        List<Genre>  genres = Genre.getGenreList(consoleService.ReadAGenre().split(","));

        try {
            Book book = bookDao.insert(new Book(0L,bookName,  authors ,  genres));
            bookAuthorDao.deleteByBookID(book.getBookID());
            for (Author curAuthor:book.getAuthors()) {
                if (authorDao.getAuthorByName(curAuthor.getName()) == null) {
                    try {
                        Author author = authorDao.insert(curAuthor);
                        bookAuthorDao.insertBookAuthor(book.getBookID(),author.getAuthorID());
                    } catch (AuthorExistException e) {
                        throw new BookExistException(messageService.getMessage(TxtConst.AUTHOR_INSERT_ERROR,new String [] {curAuthor.getName()}));
                    }
                }
            }

            bookGenreDao.deleteByBookID(book.getBookID());

            for (Genre curGenre:book.getGenres()) {
                if (genreDao.getGenreByName(curGenre.getName()) == null) {
                    try {
                        Genre genre = genreDao.insert(curGenre);
                        bookGenreDao.insertBookGenre(book.getBookID(),genre.getGenreID());
                    } catch (GenreExistException e) {
                        throw new BookExistException(messageService.getMessage(TxtConst.GENRE_INSERT_ERROR,new String [] {curGenre.getName()}));
                    }
                }
            }

            Book booknew = bookDao.getBookByID(book.getBookID());
            getBookList(booknew);
            consoleService.ShowBook(booknew);

        } catch (BookExistException e) {
             consoleService.bookErrorInsert(e.getMessage());
        }
    }

    public void updateBook() {
        Long bookID = consoleService.ReadBookID();
        Book book = bookDao.getBookByID(bookID);
        boolean updateed = false;

        if (book != null) {
            getBookList(book);
            consoleService.ShowBookOld(book);
            String bookName = consoleService.ReadBookName();
            if (!bookName.trim().equals("")) {
                book.setName(bookName);
                updateed = true;
            }

            consoleService.ShowBookOldAuthors(book);
            String authors = consoleService.ReadAuthor();
            if (!authors.trim().equals("")) {
                List<Author> authorsList = Author.getAuthorList(authors.split(","));
                book.setAuthors(authorsList);
                updateed = true;
            }


            consoleService.ShowBookOldGenres(book);
            String genres = consoleService.ReadAGenre();
            if (!genres.trim().equals("")) {
                List<Genre> genresList = Genre.getGenreList(genres.split(","));
                book.setGenres(genresList);
                updateed = true;
            }


            if (updateed) {
                try {
                    bookDao.update(book);

                    bookAuthorDao.deleteByBookID(book.getBookID());

                    for (Author curAuthor:book.getAuthors()) {
                        if (authorDao.getAuthorByName(curAuthor.getName()) == null) {
                            try {
                                Author author = authorDao.insert(curAuthor);
                                bookAuthorDao.insertBookAuthor(book.getBookID(),author.getAuthorID());
                            } catch (AuthorExistException e) {
                                throw new BookExistException(messageService.getMessage(TxtConst.AUTHOR_INSERT_ERROR,new String [] {curAuthor.getName()}));
                            }
                        }
                    }

                    bookGenreDao.deleteByBookID(book.getBookID());

                    for (Genre curGenre:book.getGenres()) {
                        if (genreDao.getGenreByName(curGenre.getName()) == null) {
                            try {
                                Genre genre = genreDao.insert(curGenre);
                                bookGenreDao.insertBookGenre(book.getBookID(),genre.getGenreID());
                            } catch (GenreExistException e) {
                                throw new BookExistException(messageService.getMessage(TxtConst.GENRE_INSERT_ERROR,new String [] {curGenre.getName()}));
                            }
                        }
                    }

                    Book bookupd = bookDao.getBookByID(bookID);
                    getBookList(bookupd);
                    consoleService.ShowBook(bookupd);

                } catch (BookExistException e) {
                    consoleService.bookErrorInsert(e.getMessage());
                }
            }
        }
    }


    public void deleteBook() {
        Long bookID = consoleService.ReadBookID();
        Book book =  bookDao.getBookByID(bookID);

        if (book != null) {
            bookDao.deleteByID(book.getBookID());
        } else {
            consoleService.bookNotExists();
        }
    }

    public void selectBookByID(){
      Long bookID = consoleService.ReadBookID();
      Book book =  bookDao.getBookByID(bookID);
      if (book != null) {
          consoleService.ShowBook(book);
      } else {
          consoleService.bookNotExists();
      }
    }

    private void getBookList(Book book) {
        book.setAuthors(bookAuthorDao.findByBookID(book.getBookID()));
        book.setGenres(bookGenreDao.findByBookID(book.getBookID()));
    }


    private List<Book> getLists(List<Book> books) {
        for (Book curBook:books) {
            getBookList(curBook);
        }
        return books;
    }

    public void findAllBook() {
        consoleService.ShowBooks(getLists(bookDao.findAll()));
    }

    public void findAllAuthor() {
        consoleService.ShowAuthors(authorDao.findAll());
    }

    public void findAllGenre() {
        consoleService.ShowGenres(genreDao.findAll());
    }


    public void findAllBookByName() {
        String bookName = consoleService.ReadBookName();
        consoleService.ShowBooks(getLists(bookDao.findAllByName(bookName)));
    }

    public void findAllBookByAuthor() {
        String author = consoleService.ReadAuthor();
        consoleService.ShowBooks(getLists(bookDao.findAllByAuthor(author)));
    }

    public void findAllBookByGenre() {
        String genre = consoleService.ReadAGenre();
        consoleService.ShowBooks(getLists(bookDao.findAllByGenre(genre)));
    }


}
