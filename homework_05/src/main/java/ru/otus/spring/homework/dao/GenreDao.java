package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.Exceptions.EntityExistException;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre insert(Genre genre) throws EntityExistException;
    void update(Genre genre);
    void deleteByID(Long genreID);
    int countByID(Long genreID);
    List<Genre> findAll();
    Genre getGenreByName(String name);
    int countByName(String name);

    List<Genre> findGenreByBookID(Long bookID);
}
