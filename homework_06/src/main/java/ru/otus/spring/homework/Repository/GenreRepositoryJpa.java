package ru.otus.spring.homework.Repository;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

public interface GenreRepositoryJpa {

    void insert(Genre genre) throws EntityExistException;
    void update(Genre genre) throws EntityExistException;
    void deleteByID(Long genreID)throws EntityExistException;
    List<Genre> findAll();

}
