package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Book;

import java.util.List;

public interface BookDao {

    Book insert(Book book) throws EntityExistException;
    void update(Book book) throws EntityExistException;
    void deleteByID(Long bookID);
    int countByID(Long bookID);
    Book getBookByID(Long bookID);
    List <Book> findAll();
    int countByName(String name);

    List <Book> findAllByName(String name);
    List <Book> findAllByAuthor(String nameAuthor);
    List <Book> findAllByGenre(String nameGenre);


    void insertBookAuthor(Long bookID, Long authorID);
    void deleteBookAuthorByID(Long bookID, Long authorID);
    void deleteBookAuthorByBookID(Long bookID);
    int countBookAuthorByID(Long bookID, Long authorID);
    List<Book> findBookByAuthorID(Long authorID);

    void insertBookGenre(Long bookID, Long genreID);
    void deleteBookGenreByID(Long bookID, Long genreID);
    void deleteBookGenreByBookID(Long bookID);
    int countBookGenreByID(Long bookID, Long genreID);

    List<Book> findBookByGenreID(Long genreID);
}
