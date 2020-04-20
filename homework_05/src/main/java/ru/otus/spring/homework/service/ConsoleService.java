package ru.otus.spring.homework.service;

import ru.otus.spring.homework.domain.Author;
import ru.otus.spring.homework.domain.Book;
import ru.otus.spring.homework.domain.Genre;
import java.util.List;

public interface ConsoleService {

    String ReadBookName();
    String ReadAuthor();
    String ReadAGenre();
    Long ReadBookID();
    void ShowBooks(List<Book> books);
    void ShowAuthors(List<Author> authors);
    void ShowGenres(List<Genre> genres);
    void ShowBook(Book book);

    void ShowBookOldAuthors(Book book);
    void ShowBookOldGenres(Book book);
    void ShowBookOld(Book book);

    void bookErrorInsert(String message);
    void bookError(String message);
    void bookNotExists();
}
