package ru.otus.spring.homework.dao;

import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;

import java.util.List;

public interface BookAuthorDao {

    void insertBookAuthor(Long bookID, Long authorID);
    void deleteByID(Long bookID, Long authorID);
    int countByID(Long bookID, Long authorID);
    void deleteByBookID(Long bookID);
    List<Author> findByBookID(Long bookID);
    List<Book> findByAuthorID(Long authorID);
}
