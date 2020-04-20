package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.Exceptions.GenreExistException;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre insert(Genre genre) throws GenreExistException;
    void update(Genre genre);
    void deleteByID(Long genreID);
    int countByID(Long genreID);
    List<Genre> findAll();
    Genre getGenreByName(String name);
    int countByName(String name);
}
