package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;

import java.util.List;

public interface BookGenreDao {

    void insertBookGenre(Long bookID, Long genreID);
    void deleteByID(Long bookID, Long genreID);
    int countByID(Long bookID, Long genreID);
    void deleteByBookID(Long bookID);
    List<Genre> findByBookID(Long bookID);
    List<Book> findByGenreID(Long genreID);
}
