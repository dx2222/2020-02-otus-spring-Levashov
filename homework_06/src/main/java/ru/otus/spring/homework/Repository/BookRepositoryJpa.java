package ru.otus.spring.homework.Repository;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {

    Book insert(Book book) throws EntityExistException;
    void update(Book book) throws EntityExistException;
    void deleteByID(Long bookID);
    Book getBookByID(Long bookID);
    List <Book> findAll();

    List <Book> findAllByName(String name);
    List <Book> findAllByAuthor(String nameAuthor);
    List <Book> findAllByGenre(String nameGenre);

}
