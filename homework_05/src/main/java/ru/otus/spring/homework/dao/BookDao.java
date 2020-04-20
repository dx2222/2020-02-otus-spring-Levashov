package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.Exceptions.BookExistException;
import ru.otus.spring.homework.domain.Book;

import java.util.List;

public interface BookDao {

    Book insert(Book book) throws BookExistException;
    void update(Book book) throws BookExistException;
    void deleteByID(Long bookID);
    int countByID(Long bookID);
    Book getBookByID(Long bookID);
    List <Book> findAll();
    int countByName(String name);

    List <Book> findAllByName(String name);
    List <Book> findAllByAuthor(String nameAuthor);
    List <Book> findAllByGenre(String nameGenre);


}
