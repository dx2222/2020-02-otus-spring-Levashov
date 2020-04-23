package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author insert(Author author) throws EntityExistException;
    void update(Author author);
    void deleteByID(Long authorID);
    int countByID(Long authorID);
    List<Author> findAll();
    Author getAuthorByName(String name);
    int countByName(String name);

    List<Author> findAuthorByBookID(Long bookID);
}
