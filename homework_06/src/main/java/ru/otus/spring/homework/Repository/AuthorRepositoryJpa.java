package ru.otus.spring.homework.Repository;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Author;

import java.util.List;

public interface AuthorRepositoryJpa {

    void insert(Author author) throws EntityExistException;
    void update(Author author) throws EntityExistException;
    void deleteByID(Long authorID) throws EntityExistException;
    List<Author> findAll();

}
